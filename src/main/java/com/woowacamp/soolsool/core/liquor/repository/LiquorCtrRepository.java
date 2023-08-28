package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorCtr;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorCtrRepository extends JpaRepository<LiquorCtr, Long> {

    Optional<LiquorCtr> findByLiquorId(Long liquorId);

    List<LiquorCtr> findAllByLiquorIdIn(List<Long> liquorIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select lc from LiquorCtr lc where lc.liquorId = :liquorId")
    Optional<LiquorCtr> findByLiquorIdWithPessimisticWriteLock(final Long liquorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select lc from LiquorCtr lc where lc.liquorId in :liquorIds")
    List<LiquorCtr> findAllByLiquorIdInWithPessimisticWriteLock(List<Long> liquorIds);
}
