package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorRepository extends JpaRepository<Liquor, Long> {

    @Query("select liq"
        + " from Order o"
        + "      inner join ReceiptItem ri on o.receipt = ri.receipt"
        + "      inner join Liquor liq on ri.liquorId = liq.id"
        + " where ri.receipt.id in (select sub_ri.receipt.id"
        + "                         from ReceiptItem sub_ri"
        + "                         where sub_ri.liquorId = :liquorId)"
        + "       and o.status.id = (select os.id"
        + "                          from OrderStatus os"
        + "                          where os.type = 'COMPLETED')"
        + "       and ri.liquorId != :liquorId"
        + " group by ri.liquorId"
        + " order by count(ri.liquorId) desc")
    List<Liquor> findLiquorsPurchasedTogether(
        final Long liquorId,
        final Pageable pageableForLimit
    );
}
