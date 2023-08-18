package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorStatusRepository extends JpaRepository<LiquorStatus, Long> {

    LiquorStatus findByType(final LiquorStatusType type);
}
