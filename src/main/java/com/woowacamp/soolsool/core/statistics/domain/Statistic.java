package com.woowacamp.soolsool.core.statistics.domain;

import com.woowacamp.soolsool.core.statistics.domain.converter.*;
import com.woowacamp.soolsool.core.statistics.domain.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Statistic {

    @EmbeddedId
    private StatisticId statisticId;

    @Column(name = "region")
    @Convert(converter = RegionConverter.class)
    private Region region;

    @Column(name = "brew_type")
    @Convert(converter = BrewTypeConverter.class)
    private BrewType brewType;

    @Column(name = "impression", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = ImpressionConverter.class)
    private Impression impression;

    @Column(name = "click", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = ClickConverter.class)
    private Click click;

    @Column(name = "sale_quantity", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SaleQuantityConverter.class)
    private SaleQuantity saleQuantity;

    @Column(name = "sale_price", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SalePriceConveter.class)
    private SalePrice salePrice;

    @Builder
    public Statistic(
            final StatisticId statisticId,
            final String region,
            final String brewType,
            final BigInteger impression,
            final BigInteger click,
            final BigInteger saleQuantity,
            final BigInteger salePrice
    ) {
        this.statisticId = statisticId;
        this.region = new Region(region);
        this.brewType = new BrewType(brewType);
        this.impression = new Impression(impression);
        this.click = new Click(click);
        this.saleQuantity = new SaleQuantity(saleQuantity);
        this.salePrice = new SalePrice(salePrice);
    }

    public Statistic addSaleQuantityAndPrice(final BigInteger saleQuantity, final BigInteger salePrice) {
        return Statistic.builder()
                .statisticId(this.statisticId)
                .region(this.region.getName())
                .brewType(this.brewType.getType())
                .impression(this.impression.getCount())
                .click(this.click.getCount())
                .saleQuantity(this.saleQuantity.add(saleQuantity).getQuantity())
                .salePrice(this.salePrice.add(salePrice).getPrice())
                .build();
    }
}
