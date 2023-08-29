package com.woowacamp.soolsool.core.statistics.repository;

import com.woowacamp.soolsool.core.statistics.domain.Statistics;
import com.woowacamp.soolsool.core.statistics.domain.StatisticsId;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, StatisticsId> {

    @Modifying
    @Query(value =
        "INSERT INTO statistics (liquor_id, year, month, week, day, sale_quantity, sale_price, brew_type, region) "
            + "SELECT ri.liquor_id as liquor_id, "
            + "YEAR(DATE(DATE_FORMAT(o.created_at, '%Y-%m-%d'))) as year, "
            + "MONTH(DATE(DATE_FORMAT(o.created_at, '%Y-%m-%d'))) as month, "
            + "WEEK(DATE(DATE_FORMAT(o.created_at, '%Y-%m-%d'))) as week, "
            + "DAY(DATE(DATE_FORMAT(o.created_at, '%Y-%m-%d'))) as day, "
            + "SUM(ri.quantity) as sale_quantity, "
            + "SUM(ri.quantity * ri.liquor_original_price) as sale_price, "
            + "ri.liquor_brew as brew_type, "
            + "ri.liquor_region as region "
            + "FROM receipt_items ri "
            + "INNER JOIN orders o ON ri.receipt_id = o.receipt_id "
            + "WHERE o.order_status_id = 1 "
            + "AND o.created_at BETWEEN :startDate AND :endDate "
            + "GROUP BY year, month, week, day, liquor_id, brew_type, region "
            + "ON DUPLICATE KEY UPDATE sale_quantity = (SELECT sale_quantity), sale_price = (SELECT sale_price) ", nativeQuery = true)
    void updateStatisticsSales(
        final LocalDate startDate,
        final LocalDate endDate
    );
    
}
