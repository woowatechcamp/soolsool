package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorRegionRepository extends JpaRepository<LiquorRegion, Long> {

    @Cacheable(value = "liquorRegion", key = "#type")
    Optional<LiquorRegion> findByType(final LiquorRegionType type);
}
