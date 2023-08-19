package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorBrewRepository extends JpaRepository<LiquorBrew, Long> {

    Optional<LiquorBrew> findByType(final LiquorBrewType type);
}
