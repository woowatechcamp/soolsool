package com.woowacamp.soolsool.core.liquor.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@RequiredArgsConstructor
public class PageLiquorResponse {

    private final boolean hasNext;
    private final Long nextCursorId;
    private final Long nextClickCount;
    private final List<LiquorClickElementResponse> liquors;

    public static PageLiquorResponse of(
        final Pageable pageable,
        final List<LiquorClickElementResponse> liquors
    ) {
        if (liquors.size() < pageable.getPageSize()) {
            return new PageLiquorResponse(false, liquors);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();
        final Long lastReadClickCount = liquors.get(liquors.size() - 1).getClickCount();

        return new PageLiquorResponse(true, lastReadLiquorId, lastReadClickCount, liquors);
    }

    private PageLiquorResponse(
        final boolean hasNext,
        final List<LiquorClickElementResponse> liquors
    ) {
        this(hasNext, null, null, liquors);
    }
}
