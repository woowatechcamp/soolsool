package com.woowacamp.soolsool.core.statistics.infra;

import com.woowacamp.soolsool.core.statistics.domain.Statistic;
import com.woowacamp.soolsool.core.statistics.domain.StatisticId;
import com.woowacamp.soolsool.core.statistics.domain.StatisticLiquor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticJpaRepository extends JpaRepository<Statistic, StatisticId> {

    @Modifying
    @Query(value =
        "INSERT INTO statistics (liquor_id, year, month, week, day, sale_quantity, sale_price, brew_type, region) "
            +
            "SELECT ri.liquor_id as liquor_id, " +
            "       YEAR(o.created_at) as year, " +
            "       MONTH(o.created_at) as month, " +
            "       WEEK(o.created_at) as week, " +
            "       DAY(o.created_at) as day, " +
            "       SUM(ri.quantity) as sale_quantity, " +
            "       SUM(ri.quantity * ri.liquor_original_price) as sale_price, " +
            "       ri.liquor_brew as brew_type, " +
            "       ri.liquor_region as region " +
            "       FROM receipt_items ri " +
            "              INNER JOIN orders o ON ri.receipt_id = o.receipt_id " +
            "WHERE o.order_status_id = 1 " +
            "AND o.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY year, month, week, day, liquor_id, brew_type, region " +
            "ON DUPLICATE KEY UPDATE sale_quantity = (SELECT sale_quantity), sale_price = (SELECT sale_price) ", nativeQuery = true)
    void updateStatisticsSales(
        final LocalDate startDate,
        final LocalDate endDate
    );

    @Modifying
    @Query(value = "INSERT INTO statistics (year, month, week, day, liquor_id, impression, click) "
        +
        "SELECT YEAR(:date) as year, " +
        "       MONTH(:date) as month, " +
        "       WEEK(:date) as week, " +
        "       DAY(:date) as day, " +
        "       s.liquor_id as liquor_id, " +
        "       (lc.impression - s.sum_impression) as day_impression, " +
        "       (lc.click - s.sum_click) as day_click " +
        "FROM liquor_ctrs lc " +
        "INNER JOIN ( " +
        "    SELECT liquor_id, SUM(impression) AS sum_impression, SUM(click) AS sum_click " +
        "    FROM statistics " +
        "    WHERE year < YEAR(:date) " +
        "    OR (year = YEAR(:date) AND month < MONTH(:date))" +
        "    OR (year = YEAR(:date) AND month = MONTH(:date) AND day < DAY(:date))" +
        "    GROUP BY liquor_id " +
        ") s ON lc.liquor_id = s.liquor_id " +
        "ON DUPLICATE KEY UPDATE impression = (lc.impression - s.sum_impression), " +
        "                        click = (lc.click - s.sum_click)", nativeQuery = true)
    void updateStatisticsCtr(final LocalDate date);

    @Query(value = "SELECT l.id as liquorId, "
        + "                l.name as liquorName, "
        + "                l.brand as liquorBrand, "
        + "                l.image_url as liquorImageUrl, "
        + "                l.price as liquorPrice, "
        + "                s.liquorValue as liquorValue "
        + "FROM ( "
        + "    SELECT liquor_id, SUM(sale_price) AS liquorValue "
        + "    FROM statistics "
        + "    GROUP BY liquor_id "
        + "    ORDER BY SUM(sale_price) DESC "
        + "    LIMIT 5 "
        + ") s "
        + "JOIN liquors l ON s.liquor_id = l.id ", nativeQuery = true)
    List<StatisticLiquor> findTop5LiquorsAndSalePrice();

    @Query(value = "SELECT l.id as liquorId, "
        + "                l.name as liquorName, "
        + "                l.brand as liquorBrand, "
        + "                l.image_url as liquorImageUrl, "
        + "                l.price as liquorPrice, "
        + "                s.liquorValue as liquorValue "
        + "FROM ( "
        + "    SELECT liquor_id, SUM(sale_quantity) AS liquorValue "
        + "    FROM statistics "
        + "    GROUP BY liquor_id "
        + "    ORDER BY SUM(sale_quantity) DESC "
        + "    LIMIT 5 "
        + ") s "
        + "JOIN liquors l ON s.liquor_id = l.id ", nativeQuery = true)
    List<StatisticLiquor> findTop5LiquorsAndSaleQuantity();

    @Query(value = "select liquor_id as liquorId, sum(sale_price) as liquorValue "
        + "from statistics "
        + "group by liquor_id "
        + "order by sum(sale_price) desc "
        + "limit 5", nativeQuery = true)
    List<StatisticLiquor> findTop5LiquorIdAndSalePrice();

    @Query(value = "select liquor_id as liquorId, sum(sale_quantity) as liquorValue "
        + "from statistics "
        + "group by liquor_id "
        + "order by sum(sale_quantity) desc "
        + "limit 5", nativeQuery = true)
    List<StatisticLiquor> findTop5LiquorIdAndSaleQuantity();
}
