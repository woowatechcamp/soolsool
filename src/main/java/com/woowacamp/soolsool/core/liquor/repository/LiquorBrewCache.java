package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorBrewCache {

    private final LiquorBrewRepository liquorBrewRepository;

    @Cacheable(value = "liquorBrew", key = "#type")
    public Optional<LiquorBrew> findByType(final LiquorBrewType type) {
        return liquorBrewRepository.findByType(type);
    }
}
