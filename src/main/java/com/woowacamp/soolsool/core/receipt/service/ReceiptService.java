package com.woowacamp.soolsool.core.receipt.service;

import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.ACCESS_DENIED_RECEIPT;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_EQUALS_MEMBER;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_FOUND_RECEIPT;
import static com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode.NOT_RECEIPT_FOUND;

import com.woowacamp.soolsool.core.cart.domain.Cart;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.receipt.code.ReceiptErrorCode;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptStatus;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.dto.response.ReceiptDetailResponse;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptRepository;
import com.woowacamp.soolsool.core.receipt.repository.ReceiptStatusCache;
import com.woowacamp.soolsool.core.receipt.repository.redisson.ReceiptRedisRepository;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private static final int RECEIPT_EXPIRED_MINUTES = 5;
    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;
    private final ReceiptStatusCache receiptStatusCache;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    private final ReceiptRedisRepository receiptRedisRepository;

    private final RedissonClient redissonClient;

    @Transactional
    public Long addReceipt(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new SoolSoolException(ReceiptErrorCode.MEMBER_NO_INFORMATION));

        final Cart cart = new Cart(memberId, cartItemRepository.findAllByMemberId(memberId));

        final Long receiptId = receiptRepository.save(
            receiptMapper.mapFrom(cart, member.getMileage())).getId();

        receiptRedisRepository.addExpiredEvent(receiptId, memberId, RECEIPT_EXPIRED_MINUTES);

        return receiptId;
    }

    @Transactional(readOnly = true)
    public ReceiptDetailResponse findReceipt(final Long memberId, final Long receiptId) {
        final Receipt receipt = getReceipt(receiptId);

        if (!Objects.equals(receipt.getMemberId(), memberId)) {
            throw new SoolSoolException(NOT_EQUALS_MEMBER);
        }

        return ReceiptDetailResponse.from(receipt);
    }

    @Transactional
    public void modifyReceiptStatus(
        final Long memberId,
        final Long receiptId,
        final ReceiptStatusType receiptStatusType
    ) {
        final RLock receiptLock = getReceiptLock(receiptId);

        try {
            receiptLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            final Receipt receipt = getReceipt(receiptId);

            if (!Objects.equals(receipt.getMemberId(), memberId)) {
                throw new SoolSoolException(NOT_EQUALS_MEMBER);
            }

            if (receipt.isNotInProgress()) {
                throw new SoolSoolException(ReceiptErrorCode.UNMODIFIABLE_STATUS);
            }

            receipt.updateStatus(
                getReceiptStatus(receiptStatusType)
            );
        } catch (final InterruptedException e) {
            throw new SoolSoolException(ReceiptErrorCode.INTERRUPTED_THREAD);
        } finally {
            unlock(receiptLock);
        }
    }

    private Receipt getReceipt(final Long receiptId) {
        return receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_RECEIPT_FOUND));
    }

    private ReceiptStatus getReceiptStatus(final ReceiptStatusType receiptStatusType) {
        return receiptStatusCache.findByType(receiptStatusType)
            .orElseThrow(() -> new SoolSoolException(ReceiptErrorCode.NOT_RECEIPT_TYPE_FOUND));
    }

    private RLock getReceiptLock(final Long receiptId) {
        return redissonClient.getLock(LockType.RECEIPT.getPrefix() + receiptId);
    }

    private void unlock(final RLock lock) {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Transactional(readOnly = true)
    public Receipt getMemberReceipt(final Long memberId, final Long receiptId) {
        final Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new SoolSoolException(NOT_FOUND_RECEIPT));
        validateAccessibleReceipt(memberId, receipt);

        return receipt;
    }

    private void validateAccessibleReceipt(final Long memberId, final Receipt receipt) {
        if (!Objects.equals(memberId, receipt.getMemberId())) {
            throw new SoolSoolException(ACCESS_DENIED_RECEIPT);
        }
    }
}
