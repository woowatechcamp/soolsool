package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorStatusCache {

    private final LiquorStatusRepository liquorStatusRepository;

    @Cacheable(value = "liquorStatus", key = "#type")
    public Optional<LiquorStatus> findByType(final LiquorStatusType type) {
        return liquorStatusRepository.findByType(type);
    }
}
