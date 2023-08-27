package com.woowacamp.soolsool.core.liquor.repository;

import static com.woowacamp.soolsool.core.liquor.domain.QLiquor.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacamp.soolsool.core.liquor.domain.LiquorBrew;
import com.woowacamp.soolsool.core.liquor.domain.LiquorRegion;
import com.woowacamp.soolsool.core.liquor.domain.LiquorStatus;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorBrand;
import com.woowacamp.soolsool.core.liquor.dto.LiquorElementResponse;
import com.woowacamp.soolsool.core.liquor.dto.LiquorSearchCondition;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<LiquorElementResponse> getList(
        final LiquorSearchCondition condition,
        final Pageable pageable,
        final Long cursorId
    ) {
        return queryFactory.select(
                Projections.constructor(
                    LiquorElementResponse.class,
                    liquor
                )
            )
            .from(liquor)
            .where(
                eqRegion(condition.getLiquorRegion()),
                eqBrew(condition.getLiquorBrew()),
                eqStatus(condition.getLiquorStatus()),
                eqBrand(condition.getBrand()),
                cursorId(cursorId)
            )
            .orderBy(liquor.id.desc())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression eqRegion(final Optional<LiquorRegion> liquorRegion) {
        if (liquorRegion.isEmpty()) {
            return null;
        }
        return liquor.region.eq(liquorRegion.get());
    }

    private BooleanExpression eqBrew(final Optional<LiquorBrew> liquorBrew) {
        if (liquorBrew.isEmpty()) {
            return null;
        }
        return liquor.brew.eq(liquorBrew.get());
    }

    private BooleanExpression eqStatus(final Optional<LiquorStatus> liquorStatus) {
        if (liquorStatus.isEmpty()) {
            return null;
        }
        return liquor.status.eq(liquorStatus.get());
    }

    private BooleanExpression eqBrand(final String brand) {
        if (brand == null) {
            return null;
        }
        return liquor.brand.eq(new LiquorBrand(brand));
    }

    private BooleanExpression cursorId(final Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return liquor.id.lt(cursorId);
    }
}
