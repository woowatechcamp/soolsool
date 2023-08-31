package com.woowacamp.soolsool.core.liquor.event.listener;

import com.woowacamp.soolsool.core.liquor.event.LiquorCtrExpiredEvent;
import com.woowacamp.soolsool.core.liquor.service.LiquorCtrService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorCtrExpiredEventListener {

    private final LiquorCtrService liquorCtrService;

    @EventListener
    public void expiredListener(final LiquorCtrExpiredEvent event) {
        liquorCtrService.writeBackLiquorCtr(
            event.getLiquorId(),
            event.getImpression(),
            event.getClick()
        );
    }
}
