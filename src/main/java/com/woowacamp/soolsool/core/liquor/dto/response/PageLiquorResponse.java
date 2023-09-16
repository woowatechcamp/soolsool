package com.woowacamp.soolsool.core.liquor.dto.response;

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
        final Pageable pageable,
        final List<Liquor> liquors
    ) {
        List<LiquorElementResponse> liquorElements = liquors.stream()
            .map(LiquorElementResponse::of)
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
