package com.woowacamp.soolsool.core.liquor.event;

import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LiquorCtrExpiredEvent {

    private final Long liquorId;
    private final LiquorCtrImpression impression;
    private final LiquorCtrClick click;
}
