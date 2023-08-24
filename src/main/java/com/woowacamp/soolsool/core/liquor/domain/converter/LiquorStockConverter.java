package com.woowacamp.soolsool.core.liquor.domain.converter;

import com.woowacamp.soolsool.core.liquor.domain.vo.Stock;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LiquorStockConverter implements AttributeConverter<Stock, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Stock stock) {
        return stock.getStock();
    }

    @Override
    public Stock convertToEntityAttribute(final Integer dbData) {
        return new Stock(dbData);
    }
}
