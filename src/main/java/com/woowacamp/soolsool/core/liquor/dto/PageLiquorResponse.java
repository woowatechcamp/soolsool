package com.woowacamp.soolsool.core.liquor.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageLiquorResponse {

    private final boolean hasNext;
    private final Long nextCursorId;
    private final List<LiquorElementResponse> liquors;

    public static PageLiquorResponse of(final boolean hasNext, final Long nextCursorId,
        final List<LiquorElementResponse> liquors) {
        return new PageLiquorResponse(hasNext, nextCursorId, liquors);
    }
}
