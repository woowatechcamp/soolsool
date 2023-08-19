package com.woowacamp.soolsool.core.liquor.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("술 단위 테스트")
class LiquorTest {

    @Test
    @DisplayName("술을 정상적으로 생성한다.")
    void create() {
        /* given */
        LiquorBrew brew = new LiquorBrew(LiquorBrewType.SOJU);
        LiquorRegion region = new LiquorRegion(LiquorRegionType.GYEONGGI_DO);
        LiquorStatus status = new LiquorStatus(LiquorStatusType.ON_SALE);
        String name = "마싯는 소주";
        String price = "10000";
        String brand = "우아한";
        String imageUrl = "soju.png";
        int stock = 77;
        double alcohol = 17.2;
        int volume = 500;

        /* when */
        Liquor liquor = Liquor.builder()
            .brew(brew)
            .region(region)
            .status(status)
            .name(name)
            .price(price)
            .brand(brand)
            .imageUrl(imageUrl)
            .stock(stock)
            .alcohol(alcohol)
            .volume(volume)
            .build();

        /* then */
        assertAll(
            () -> assertThat(liquor.getBrew()).isEqualTo(brew),
            () -> assertThat(liquor.getRegion()).isEqualTo(region),
            () -> assertThat(liquor.getStatus()).isEqualTo(status),
            () -> assertThat(liquor.getName()).isEqualTo(name),
            () -> assertThat(liquor.getPrice()).isEqualTo(price),
            () -> assertThat(liquor.getBrand()).isEqualTo(brand),
            () -> assertThat(liquor.getImageUrl()).isEqualTo(imageUrl),
            () -> assertThat(liquor.getStock()).isEqualTo(stock),
            () -> assertThat(liquor.getAlcohol()).isEqualTo(alcohol),
            () -> assertThat(liquor.getVolume()).isEqualTo(volume)
        );
    }
}
