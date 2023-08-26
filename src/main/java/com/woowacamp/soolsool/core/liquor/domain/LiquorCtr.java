package com.woowacamp.soolsool.core.liquor.domain;

import static javax.persistence.GenerationType.AUTO;

import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorCtrClickConverter;
import com.woowacamp.soolsool.core.liquor.domain.converter.LiquorCtrImpressionConverter;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrClick;
import com.woowacamp.soolsool.core.liquor.domain.vo.LiquorCtrImpression;
import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "liquor_ctrs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorCtr extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "liquor_id", nullable = false)
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
    }
}
