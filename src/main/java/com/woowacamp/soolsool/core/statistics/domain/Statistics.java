package com.woowacamp.soolsool.core.statistics.domain;

import com.woowacamp.soolsool.core.statistics.domain.converter.BrewTypeConverter;
import com.woowacamp.soolsool.core.statistics.domain.converter.ImpressionConverter;
import com.woowacamp.soolsool.core.statistics.domain.converter.RegionConverter;
import com.woowacamp.soolsool.core.statistics.domain.converter.SalePriceConveter;
import com.woowacamp.soolsool.core.statistics.domain.converter.SaleQuantityConverter;
import com.woowacamp.soolsool.core.statistics.domain.vo.BrewType;
import com.woowacamp.soolsool.core.statistics.domain.vo.Click;
import com.woowacamp.soolsool.core.statistics.domain.vo.Impression;
import com.woowacamp.soolsool.core.statistics.domain.vo.Region;
import com.woowacamp.soolsool.core.statistics.domain.vo.SalePrice;
import com.woowacamp.soolsool.core.statistics.domain.vo.SaleQuantity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Statistics {

    @EmbeddedId
    private StatisticsId statisticsId;

    @Column(name = "region")
    @Convert(converter = RegionConverter.class)
    private Region region;

    @Column(name = "brew_type")
    @Convert(converter = BrewTypeConverter.class)
    private BrewType brewType;

    @Column(name = "impression", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = ImpressionConverter.class)
    private Impression impression;

    @Embedded
    private Click click;

    @Column(name = "sale_quantity", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SaleQuantityConverter.class)
    private SaleQuantity saleQuantity;

    @Column(name = "sale_price", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SalePriceConveter.class)
    private SalePrice salePrice;

    @Builder
    public Statistics(
        final Region region,
        final BrewType brewType,
        final Impression impression,
        final Click click,
        final SaleQuantity saleQuantity,
        final SalePrice salePrice
    ) {
        this.region = region;
        this.brewType = brewType;
        this.impression = impression;
        this.click = click;
        this.saleQuantity = saleQuantity;
        this.salePrice = salePrice;
    }
}
