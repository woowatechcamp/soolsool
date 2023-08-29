package com.woowacamp.soolsool.core.statistics.domain;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "statistics")
@Getter
@RequiredArgsConstructor
public class Statistics {

    @EmbeddedId
    private StatisticsId statisticsId;

    @Column(name = "region")
    private String region;

    @Column(name = "brew_type")
    private String brewType;

    @Column(name = "impression")
    private BigInteger impression;

    @Column(name = "click")
    private BigInteger click;

    @Column(name = "sale_quantity")
    private BigInteger saleQuantity;

    @Column(name = "sale_price")
    private BigInteger salePrice;

    @Builder
    public Statistics(final String region, final String brewType, final BigInteger impression,
        final BigInteger click,
        final BigInteger saleQuantity, final BigInteger salePrice) {
        this.region = region;
        this.brewType = brewType;
        this.impression = impression;
        this.click = click;
        this.saleQuantity = saleQuantity;
        this.salePrice = salePrice;
    }
}
