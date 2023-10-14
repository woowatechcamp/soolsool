package com.woowacamp.soolsool.core.liquor.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PageLiquorResponse {

    private final boolean hasNext;
    private final Long nextCursorId;
    private final List<LiquorElementResponse> liquors;

    @JsonCreator
    public PageLiquorResponse(
        final boolean hasNext,
        final Long nextCursorId,
        final List<LiquorElementResponse> liquors
    ) {
        this.hasNext = hasNext;
        this.nextCursorId = nextCursorId;
        this.liquors = liquors;
    }
    public static PageLiquorResponse of(
        final Pageable pageable,
        final List<LiquorElementResponse> liquors
    ) {
        if (liquors.size() < pageable.getPageSize()) {
            return new PageLiquorResponse(false, liquors);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();

        return new PageLiquorResponse(true, lastReadLiquorId, liquors);
    }

    private PageLiquorResponse(final boolean hasNext, final List<LiquorElementResponse> liquors) {
        this(hasNext, null, liquors);
    }
}
