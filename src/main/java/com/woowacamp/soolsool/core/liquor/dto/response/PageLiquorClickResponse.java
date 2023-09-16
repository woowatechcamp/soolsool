package com.woowacamp.soolsool.core.liquor.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@RequiredArgsConstructor
public class PageLiquorClickResponse {

    private final boolean hasNext;
    private final Long nextCursorId;
    private final Long nextClickCount;
    private final List<LiquorClickElementResponse> liquors;

    public static PageLiquorClickResponse of(
        final Pageable pageable,
        final List<LiquorClickElementResponse> liquors
    ) {
        if (liquors.size() < pageable.getPageSize()) {
            return new PageLiquorClickResponse(false, liquors);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();
        final Long lastReadClickCount = liquors.get(liquors.size() - 1).getClickCount();

        return new PageLiquorClickResponse(true, lastReadLiquorId, lastReadClickCount, liquors);
    }

    private PageLiquorClickResponse(
        final boolean hasNext,
        final List<LiquorClickElementResponse> liquors
    ) {
        this(hasNext, null, null, liquors);
    }
}
