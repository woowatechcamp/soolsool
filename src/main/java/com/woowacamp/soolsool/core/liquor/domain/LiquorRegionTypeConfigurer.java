package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorRegionType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorRegionRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// TODO : data.sql 추가시 삭제 예정
@Component
@RequiredArgsConstructor
public class LiquorRegionTypeConfigurer {

    private final LiquorRegionRepository liquorRegionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void setUpLiquorRegionType() {
        Arrays.stream(LiquorRegionType.values())
            .forEach(type -> liquorRegionRepository.save(new LiquorRegion(null, type)));
    }
}
