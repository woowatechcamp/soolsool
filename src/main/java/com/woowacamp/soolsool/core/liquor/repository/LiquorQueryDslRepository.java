package com.woowacamp.soolsool.core.liquor.repository;

import static com.woowacamp.soolsool.core.liquor.domain.QLiquor.liquor;
import static com.woowacamp.soolsool.core.liquor.domain.QLiquorCtr.liquorCtr;

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
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LiquorQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<LiquorElementResponse> getList(
        final LiquorSearchCondition condition,
        final Pageable pageable,
        final Long liquorId,
        final Long clickCount
    ) {
        return queryFactory.select(
                Projections.constructor(
                    LiquorElementResponse.class,
                    liquor,
                    liquorCtr.click
                )
            )
            .from(liquor)
            .join(liquorCtr).on(liquor.id.eq(liquorCtr.liquorId))
            .where(
                eqRegion(condition.getLiquorRegion()),
                eqBrew(condition.getLiquorBrew()),
                eqStatus(condition.getLiquorStatus()),
                eqBrand(condition.getBrand()),
                cursorId(liquorId, clickCount)
            )
            .orderBy(liquorCtr.click.click.desc(),liquor.id.desc())
            .limit(pageable.getPageSize())
            .fetch();
    }

    @Cacheable(value = "liquorsFirstPage")
    public List<LiquorElementResponse> getCachedList(
        final Pageable pageable
    ) {
        log.info("LiquorQueryDslRepository getCachedList");
        return getList(
            LiquorSearchCondition.nullObject(),
            pageable,
            null, null
        );
    }

    private BooleanExpression eqRegion(final LiquorRegion liquorRegion) {
        if (Objects.isNull(liquorRegion)) {
            return null;
        }
        return liquor.region.eq(liquorRegion);
    }

    private BooleanExpression eqBrew(final LiquorBrew liquorBrew) {
        if (Objects.isNull(liquorBrew)) {
            return null;
        }
        return liquor.brew.eq(liquorBrew);
    }

    private BooleanExpression eqStatus(final LiquorStatus liquorStatus) {
        if (Objects.isNull(liquorStatus)) {
            return null;
        }
        return liquor.status.type.eq(liquorStatus.getType());
    }

    private BooleanExpression eqBrand(final String brand) {
        if (brand == null) {
            return null;
        }
        return liquor.brand.eq(new LiquorBrand(brand));
    }

    private BooleanExpression cursorId(final Long liquorId, final Long click) {
        if (liquorId == null) {
            return null;
        }
        if (click == null) {
            return liquor.id.lt(liquorId);
        }

        return liquorCtr.click.click.lt(click).or(
            liquorCtr.click.click.eq(click).and(liquor.id.lt(liquorId)));
    }
}
