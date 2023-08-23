package com.woowacamp.soolsool.core.liquor.repository;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorRepository extends JpaRepository<Liquor, Long> {

    @Lock(value = PESSIMISTIC_WRITE)
    @Query("select l from Liquor l where l.id = :liquorId")
    Optional<Liquor> findLiquorByIdWithLock(final Long liquorId);

    Page<Liquor> findAll(final Specification<Liquor> conditions, final Pageable pageable);
}
