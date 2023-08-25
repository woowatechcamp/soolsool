package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorRepository extends JpaRepository<Liquor, Long> {

    @Query("select ri.liquorId"
        + " from ReceiptItem ri"
        + " where ri.receipt.id in (select sub_ri.receipt.id"
        + "                         from ReceiptItem sub_ri"
        + "                         where sub_ri.liquorId = :liquorId)"
        + "       and ri.liquorId != :liquorId"
        + " group by ri.liquorId"
        + " order by count(ri.liquorId) desc")
    List<Long> findLiquorsPurchasedTogether(
        @Param("liquorId") final Long liquorId,
        final Pageable pageableForLimit
    );

    List<Liquor> findAllByIdIn(final List<Long> liquorIds);

    Page<Liquor> findAll(final Specification<Liquor> conditions, final Pageable pageable);
}
