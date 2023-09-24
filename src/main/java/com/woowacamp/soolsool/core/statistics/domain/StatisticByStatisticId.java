package com.woowacamp.soolsool.core.statistics.domain;

import com.woowacamp.soolsool.core.order.domain.Order;
import com.woowacamp.soolsool.core.receipt.domain.ReceiptItem;
import com.woowacamp.soolsool.core.statistics.domain.mapper.StatisticMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticByStatisticId {

    private final Map<StatisticId, Statistic> statistics;

    public static StatisticByStatisticId from(final List<Order> orders) {
        return new StatisticByStatisticId(add(orders));
    }

    private static Map<StatisticId, Statistic> add(final List<Order> orders) {
        final Map<StatisticId, Statistic> statisticByStatisticId = new HashMap<>();

        orders.forEach(order -> addMap(order, statisticByStatisticId));

        return statisticByStatisticId;
    }

    private static void addMap(final Order order, final Map<StatisticId, Statistic> map) {
        order.getReceipt().getReceiptItems()
                .forEach(receiptItem -> addReceiptItem(receiptItem, order.getCreatedAt(), map));
    }

    private static void addReceiptItem(final ReceiptItem receiptItem, final LocalDateTime localDateTime, final Map<StatisticId, Statistic> map) {
        final StatisticId statisticId = StatisticId.from(localDateTime, receiptItem.getLiquorId()); // key

        addExistedKey(receiptItem, map, statisticId);

        addNewKey(receiptItem, map, statisticId);
    }

    private static void addNewKey(final ReceiptItem receiptItem, final Map<StatisticId, Statistic> map, final StatisticId statisticId) {
        if (!map.containsKey(statisticId)) {
            final Statistic statistic = StatisticMapper.from(receiptItem);
            map.put(statisticId, statistic);
        }
    }

    private static void addExistedKey(final ReceiptItem receiptItem, final Map<StatisticId, Statistic> map, final StatisticId statisticId) {
        if (map.containsKey(statisticId)) {
            final BigInteger saleQuantity = BigInteger.valueOf(receiptItem.getQuantity());
            final BigInteger salePrice = receiptItem.getReceiptItemOriginalPrice();
            map.put(statisticId, map.get(statisticId).addSaleQuantityAndPrice(saleQuantity, salePrice));
        }
    }
}
