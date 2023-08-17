package com.woowacamp.soolsool.core.liquor.domain;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorStatusType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorStatusRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorStatusTypeConfigurer {

    private final LiquorStatusRepository liquorStatusRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void setUpLiquorStatusType() {
        Arrays.stream(LiquorStatusType.values())
            .forEach(type -> liquorStatusRepository.save(new LiquorStatus(null, type)));
    }
}
