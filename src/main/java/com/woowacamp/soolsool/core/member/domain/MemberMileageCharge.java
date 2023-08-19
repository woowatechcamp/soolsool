package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.core.member.domain.converter.MemberMileageConverter;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_mileage_charges")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageCharge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "amount", nullable = false)
    @Convert(converter = MemberMileageConverter.class)
    private MemberMileage amount;

    @Builder
    public MemberMileageCharge(final Member member, final String amount) {
        this.member = member;
        this.amount = MemberMileage.from(amount);
    }
}
