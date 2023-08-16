package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "liquor_regions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorRegion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RegionType type;

    @Getter
    @RequiredArgsConstructor
    enum RegionType {

        GYEONGGI_DO("경기도"),
        GANGWON_DO("강원도"),
        CHUNGCHEONGBUK_DO("충청북도"),
        CHUNGCHEONGNAM_DO("충청남도"),
        GYEONGSANGBUK_DO("경상북도"),
        GYEONGSANGNAM_DO("경상남도"),
        JEOLLABUK_DO("전라북도"),
        JEOLLANAM_DO("전라남도"),
        JEJU_DO("제주도"),
        ;

        private final String name;
    }
}
