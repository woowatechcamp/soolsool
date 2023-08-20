package com.woowacamp.soolsool.core.cart.repository;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByMemberIdOrderByCreatedAtDesc(final Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from CartItem c where c.memberId = :memberId")
    void deleteAllByMemberId(final Long memberId);
}
