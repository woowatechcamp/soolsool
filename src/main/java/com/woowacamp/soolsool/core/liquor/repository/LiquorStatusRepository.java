package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorStatusRepository extends JpaRepository<LiquorStatus, Long> {

    @Cacheable(value = "liquorStatus", key = "#type")
    Optional<LiquorStatus> findByType(final LiquorStatusType type);
}
