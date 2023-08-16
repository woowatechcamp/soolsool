package com.woowacamp.soolsool.core.liquor.domain;


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
public class LiquorStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private StatusType type;

    @Getter
    @RequiredArgsConstructor
    enum StatusType {

        ON_SALE("판매중"),
        STOPPED("판매중지"),
        ;

        private final String status;
    }
}
