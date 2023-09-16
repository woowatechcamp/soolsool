package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorBrewCache {

    private final LiquorBrewRepository liquorBrewRepository;

    @Cacheable(value = "liquorBrew", key = "#type", condition = "#type!=null", unless = "#result==null")
    public Optional<LiquorBrew> findByType(final LiquorBrewType type) {
        log.info("LiquorBrewCache {}", type);
        return liquorBrewRepository.findByType(type);
    }
}
