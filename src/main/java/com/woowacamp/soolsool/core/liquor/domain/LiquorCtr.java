package com.woowacamp.soolsool.core.liquor.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacamp.soolsool.core.liquor.code.LiquorCtrErrorCode;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorCtrClickConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorCtrImpressionConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.global.common.BaseEntity;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "liquor_ctrs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorCtr extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    private Long id;

    @Column(name = "liquor_id", nullable = false)
    @Getter
    private Long liquorId;

    @Column(name = "impression", nullable = false)
    @Convert(converter = LiquorCtrImpressionConverter.class)
    private LiquorCtrImpression impression;

    @Column(name = "click", nullable = false)
    @Convert(converter = LiquorCtrClickConverter.class)
    private LiquorCtrClick click;

    @Builder
    public LiquorCtr(@NonNull final Long liquorId) {
        this.liquorId = liquorId;
        this.impression = new LiquorCtrImpression(0L);
        this.click = new LiquorCtrClick(0L);
    }

    public void updateCtr(final LiquorCtrImpression impression, final LiquorCtrClick click) {
        this.impression = impression;
        this.click = click;
    }

    public double getCtr() {
        if (impression.getImpression() == 0) {
            throw new SoolSoolException(LiquorCtrErrorCode.DIVIDE_BY_ZERO_IMPRESSION);
        }

        final double ratio = (double) click.getClick() / impression.getImpression();

        return Math.round(ratio * 100) / 100.0;
    }

    public Long getImpression() {
        return impression.getImpression();
    }

    public Long getClick() {
        return click.getClick();
    }
}
