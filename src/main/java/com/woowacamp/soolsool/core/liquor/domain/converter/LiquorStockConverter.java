package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStock;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LiquorStockConverter implements AttributeConverter<LiquorStock, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final LiquorStock stock) {
        return stock.getStock();
    }

    @Override
    public LiquorStock convertToEntityAttribute(final Integer dbData) {
        return new LiquorStock(dbData);
    }
}
