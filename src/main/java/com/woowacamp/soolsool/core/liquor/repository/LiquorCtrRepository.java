package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorCtrRepository extends JpaRepository<LiquorCtr, Long> {

    Optional<LiquorCtr> findByLiquorId(final Long liquorId);

    List<LiquorCtr> findAllByLiquorIdIn(final List<Long> liquorIds);

    @Modifying
    @Query(value = "update liquor_ctrs lc"
        + " set lc.impression = :impression, lc.click = :click"
        + " where lc.liquor_id = :liquorId",
        nativeQuery = true)
    void updateLiquorCtr(
        @Param("impression") final Long impression,
        @Param("click") final Long click,
        @Param("liquorId") final Long liquorId
    );
}
