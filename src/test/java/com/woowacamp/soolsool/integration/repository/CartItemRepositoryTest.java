package com.woowacamp.soolsool.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacamp.soolsool.core.cart.domain.CartItem;
import com.woowacamp.soolsool.core.cart.domain.vo.CartItemQuantity;
import com.woowacamp.soolsool.core.cart.repository.CartItemRepository;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorAlcohol;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorImageUrl;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorName;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorPrice;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStock;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorVolume;
import com.woowacamp.soolsool.core.liquor.repository.LiquorBrewRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRepository;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import com.woowacamp.soolsool.core.member.domain.Member;
import com.woowacamp.soolsool.core.member.domain.MemberRole;
import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.domain.vo.MemberName;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPhoneNumber;
import com.woowacamp.soolsool.core.member.repository.MemberRepository;
import com.woowacamp.soolsool.core.member.repository.MemberRoleRepository;
import java.math.BigInteger;
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
        MemberRole memberRole = memberRoleRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("MemberRole이 존재하지 않습니다."));

        Member member = Member.builder()
            .role(memberRole)
            .email(new MemberEmail("test@email.com"))
            .password(new MemberPassword("test_password"))
            .name(new MemberName("최배달"))
            .phoneNumber(new MemberPhoneNumber("010-1234-5678"))
            .mileage(MemberMileage.from("0"))
            .address(new MemberAddress("서울시 잠실역"))
            .build();

        commonMemberId = memberRepository.save(member).getId();
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
            .name(new LiquorName("안동 소주"))
            .price(new LiquorPrice(new BigInteger("12000")))
            .brand(new LiquorBrand("안동"))
            .imageUrl(new LiquorImageUrl("/soju.jpeg"))
            .stock(new LiquorStock(120))
            .alcohol(new LiquorAlcohol(21.8))
            .volume(new LiquorVolume(400))
            .build();
        liquor = liquorRepository.save(liquor);

        CartItem cartItem = CartItem.builder()
            .memberId(commonMemberId)
            .liquor(liquor)
            .quantity(new CartItemQuantity(1))
            .build();

        // when
        CartItem saved = cartItemRepository.save(cartItem);

        // then
        assertThat(saved.getId()).isNotNull();
    }
}
