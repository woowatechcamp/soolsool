package com.woowacamp.soolsool.core.liquor.domain;


import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrewType;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorType;
import com.woowacamp.soolsool.core.liquor.repository.LiquorTypeRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorTypeConfigurer {

    private final LiquorTypeRepository liquorTypeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void setUpLiquorType() {
        Arrays.stream(LiquorBrewType.values())
            .forEach(type -> liquorTypeRepository.save(new LiquorType(null, type)));
    }
}
