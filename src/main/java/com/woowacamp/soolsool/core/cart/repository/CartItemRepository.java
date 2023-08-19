package com.woowacamp.soolsool.core.cart.repository;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByMemberIdOrderByCreatedAtDesc(final Long memberId);
}
