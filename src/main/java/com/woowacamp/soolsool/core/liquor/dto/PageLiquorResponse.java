package com.woowacamp.soolsool.core.liquor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
        final List<Liquor> liquors
    ) {
        List<LiquorElementResponse> liquorElements = liquors.stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());

        if (liquors.size() < pageable.getPageSize()) {
            return new PageLiquorResponse(false, liquorElements);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();

        return new PageLiquorResponse(true, lastReadLiquorId, liquorElements);
    }

    private PageLiquorResponse(final boolean hasNext, final List<LiquorElementResponse> liquors) {
        this(hasNext, null, liquors);
    }
}
