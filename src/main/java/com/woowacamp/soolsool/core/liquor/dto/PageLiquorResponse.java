package com.woowacamp.soolsool.core.liquor.dto;

import com.woowacamp.soolsool.core.liquor.domain.Liquor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@RequiredArgsConstructor
public class PageLiquorResponse {

    private final boolean hasNext;
    private final Long nextCursorId;
    private final List<LiquorElementResponse> liquors;

    public static PageLiquorResponse of(
        final boolean hasNext,
        final Long nextCursorId,
        final List<LiquorElementResponse> liquors
    ) {
        return new PageLiquorResponse(hasNext, nextCursorId, liquors);
    }

    public static PageLiquorResponse of(
        final boolean hasNext,
        final List<LiquorElementResponse> liquors
    ) {
        return PageLiquorResponse.of(hasNext, null, liquors);
    }

    public static PageLiquorResponse of(final Pageable pageable, final List<Liquor> liquors) {
        List<LiquorElementResponse> liquorElements = liquors.stream()
            .map(LiquorElementResponse::from)
            .collect(Collectors.toList());

        if (liquors.size() < pageable.getPageSize()) {
            return PageLiquorResponse.of(false, liquorElements);
        }

        final Long lastReadLiquorId = liquors.get(liquors.size() - 1).getId();

        return PageLiquorResponse.of(true, lastReadLiquorId, liquorElements);
    }
}
