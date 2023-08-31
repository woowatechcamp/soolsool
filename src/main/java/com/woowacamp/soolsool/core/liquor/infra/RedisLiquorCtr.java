package com.woowacamp.soolsool.core.liquor.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RedisLiquorCtr {

    private final Long impression;
    private final Long click;

    public RedisLiquorCtr increaseImpression() {
        return new RedisLiquorCtr(impression + 1, click);
    }

    public RedisLiquorCtr increaseClick() {
        return new RedisLiquorCtr(impression, click + 1);
    }

    public RedisLiquorCtr synchronizedWithDatabase(final Long impression, final Long click) {
        return new RedisLiquorCtr(this.impression + impression, this.click + click);
    }
}
