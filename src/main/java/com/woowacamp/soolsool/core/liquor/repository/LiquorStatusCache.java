package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquorStatusCache {

    private final LiquorStatusRepository liquorStatusRepository;

    @Cacheable(value = "liquorStatus", key = "#type")
    public Optional<LiquorStatus> findByType(final LiquorStatusType type) {
        log.info("LiquorStatusCache {}", type);
        return liquorStatusRepository.findByType(type);
    }
}
