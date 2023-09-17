package com.woowacamp.soolsool.core.payment.service;

import com.woowacamp.soolsool.core.cart.service.CartService;
import com.woowacamp.soolsool.core.liquor.service.LiquorService;
import com.woowacamp.soolsool.core.liquor.service.LiquorStockService;
import com.woowacamp.soolsool.core.member.service.MemberService;
import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.order.service.OrderService;
import com.woowacamp.soolsool.core.payment.code.PayErrorCode;
import com.woowacamp.soolsool.core.payment.dto.request.PayOrderRequest;
import com.woowacamp.soolsool.core.payment.dto.response.PayReadyResponse;
import com.woowacamp.soolsool.core.payment.infra.PayClient;
import com.woowacamp.soolsool.core.receipt.domain.Receipt;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.receipt.domain.vo.ReceiptStatusType;
import com.woowacamp.soolsool.core.receipt.service.ReceiptService;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import com.woowacamp.soolsool.global.infra.LockType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final ReceiptService receiptService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final CartService cartService;
    private final LiquorStockService liquorStockService;
    private final LiquorService liquorService;

    private final PayClient payClient;

    private final RedissonClient redissonClient;

    @Transactional
    public PayReadyResponse ready(final Long memberId, final PayOrderRequest payOrderRequest) {
        final Receipt receipt = receiptService
            .getMemberReceipt(memberId, payOrderRequest.getReceiptId());

        return payClient.ready(receipt);
    }

    @Transactional
    public Order approve(final Long memberId, final Long receiptId, final String pgToken) {
        final Receipt receipt = receiptService.getMemberReceipt(memberId, receiptId);

        final List<RLock> locks = new ArrayList<>();
        locks.add(getMemberLock(memberId));
        locks.add(getReceiptLock(receiptId));
        locks.addAll(getLiquorLocks(receipt.getReceiptItems()));

        final RLock multiLock = redissonClient.getMultiLock(
            locks.toArray(locks.toArray(new RLock[0]))
        );

        try {
            multiLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);

            decreaseStocks(receipt);

            final Order order = orderService.addOrder(memberId, receipt);

            memberService.subtractMemberMileage(memberId, order, receipt.getMileageUsage());

            cartService.removeCartItems(memberId);

            receiptService.modifyReceiptStatus(memberId, receiptId, ReceiptStatusType.COMPLETED);

            orderService.addPaymentInfo(
                payClient.payApprove(receipt, pgToken).toEntity(order.getId()));

            return order;
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new SoolSoolException(PayErrorCode.INTERRUPTED_THREAD);
        } finally {
            multiLock.unlock();
        }
    }

    private RLock getMemberLock(final Long memberId) {
        return redissonClient.getLock(LockType.MEMBER.getPrefix() + memberId);
    }

    private RLock getReceiptLock(final Long receiptId) {
        return redissonClient.getLock(LockType.RECEIPT.getPrefix() + receiptId);
    }

    private List<RLock> getLiquorLocks(final List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
            .map(ReceiptItem::getLiquorId)
            .sorted()
            .map(liquorId -> redissonClient.getLock(
                LockType.LIQUOR_STOCK.getPrefix() + liquorId))
            .collect(Collectors.toList());
    }

    private void decreaseStocks(final Receipt receipt) {
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            liquorStockService.decreaseLiquorStock(receiptItem.getLiquorId(),
                receiptItem.getQuantity());
            liquorService.decreaseTotalStock(receiptItem.getLiquorId(),
                receiptItem.getQuantity());
        }
    }

    @Transactional
    public void cancelReceipt(final Long memberId, final Long receiptId) {
        receiptService.modifyReceiptStatus(memberId, receiptId, ReceiptStatusType.CANCELED);
    }
}
