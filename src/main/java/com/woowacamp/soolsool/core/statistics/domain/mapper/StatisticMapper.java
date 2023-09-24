package com.woowacamp.soolsool.core.statistics.domain.mapper;

import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.statistics.domain.Statistic;
import com.woowacamp.soolsool.core.statistics.domain.StatisticId;

import java.math.BigInteger;

public class StatisticMapper {

    private StatisticMapper() {
    }

    public static Statistic from(final ReceiptItem receiptItem) {
        return Statistic.builder()
                .statisticId(StatisticId.from(receiptItem.getCreatedAt(), receiptItem.getLiquorId()))
                .region(receiptItem.getReceiptItemRegion())
                .brewType(receiptItem.getReceiptItemBrew())
                .saleQuantity(BigInteger.valueOf(receiptItem.getQuantity()))
                .salePrice(receiptItem.getReceiptItemOriginalPrice().multiply(BigInteger.valueOf(receiptItem.getQuantity())))
                .build();
    }
}
