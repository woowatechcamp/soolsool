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
@Table(name = "liquor_types")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private BrewType type;

    @Getter
    @RequiredArgsConstructor
    enum BrewType {

        SOJU("소주"),
        DISTILLED("증류주"),
        MAKGEOLLI("막걸리"),
        PURE("약주"),
        RICE("청주"),
        BERRY("과실주"),
        ETC("기타주류"),
        ;

        private final String type;
    }
}
