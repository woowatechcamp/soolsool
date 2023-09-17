package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorRegionCache {

    private final LiquorRegionRepository liquorRegionRepository;

    @Cacheable(value = "liquorRegion", key = "#type", condition = "#type!=null",
        unless = "#result==null", cacheManager = "caffeineCacheManager")
    public Optional<LiquorRegion> findByType(final LiquorRegionType type) {
        log.info("LiquorRegionCache {}", type);
        return liquorRegionRepository.findByType(type);
    }
}
