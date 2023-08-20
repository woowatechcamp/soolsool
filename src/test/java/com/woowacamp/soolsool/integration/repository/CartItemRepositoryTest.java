package com.woowacamp.soolsool.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("CartItemRepository 테스트")
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private LiquorBrewRepository liquorBrewRepository;

    @Autowired
    private LiquorRegionRepository liquorRegionRepository;

    @Autowired
    private LiquorStatusRepository liquorStatusRepository;

    @Autowired
    private LiquorRepository liquorRepository;

    private Long commonMemberId;

    @BeforeEach
    void setUpMember() {
        Member member = memberRepository.findById(4L)
            .orElseThrow(() -> new RuntimeException("Member가 존재하지 않습니다."));
        commonMemberId = member.getId();
    }

    @Test
    @DisplayName("save 테스트")
    void createCartItem() {
        // given
        LiquorBrew liquorBrew = liquorBrewRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("LiquorBrew가 존재하지 않습니다."));
        LiquorRegion liquorRegion = liquorRegionRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("LiquorRegion이 존재하지 않습니다."));
        LiquorStatus liquorStatus = liquorStatusRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("LiquorStatus가 존재하지 않습니다."));
        Liquor liquor = Liquor.builder()
            .brew(liquorBrew)
            .region(liquorRegion)
            .status(liquorStatus)
            .name("안동 소주")
            .price("12000")
            .brand("안동")
            .imageUrl("/soju.jpeg")
            .stock(120)
            .alcohol(21.8)
            .volume(400)
            .build();
        liquor = liquorRepository.save(liquor);

        CartItem cartItem = CartItem.builder()
            .memberId(commonMemberId)
            .liquor(liquor)
            .quantity(1)
            .build();

        // when
        CartItem saved = cartItemRepository.save(cartItem);

        // then
        assertThat(saved.getId()).isNotNull();
    }
}
