package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorCtrRepository extends JpaRepository<LiquorCtr, Long> {

    Optional<LiquorCtr> findByLiquorId(final Long liquorId);

    List<LiquorCtr> findAllByLiquorIdIn(final List<Long> liquorIds);
}
