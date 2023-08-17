package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.core.member.domain.converter.MemberAddressConverter;
import com.woowacamp.soolsool.core.member.domain.converter.MemberEmailConverter;
import com.woowacamp.soolsool.core.member.domain.converter.MemberMileageConverter;
import com.woowacamp.soolsool.core.member.domain.converter.MemberNameConverter;
import com.woowacamp.soolsool.core.member.domain.converter.MemberPasswordConverter;
import com.woowacamp.soolsool.core.member.domain.converter.MemberPhoneNumberConverter;
import com.woowacamp.soolsool.core.member.domain.vo.MemberAddress;
import com.woowacamp.soolsool.core.member.domain.vo.MemberEmail;
import com.woowacamp.soolsool.core.member.domain.vo.MemberMileage;
import com.woowacamp.soolsool.core.member.domain.vo.MemberName;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPassword;
import com.woowacamp.soolsool.core.member.domain.vo.MemberPhoneNumber;
import com.woowacamp.soolsool.core.member.domain.vo.MemberRole;
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
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    @Convert(converter = MemberEmailConverter.class)
    private MemberEmail email;

    @Column(name = "password", nullable = false, length = 60)
    @Convert(converter = MemberPasswordConverter.class)
    private MemberPassword password;

    @Column(name = "name", nullable = false, length = 20)
    @Convert(converter = MemberNameConverter.class)
    private MemberName name;

    @Column(name = "phone_number", nullable = false, length = 13)
    @Convert(converter = MemberPhoneNumberConverter.class)
    private MemberPhoneNumber phoneNumber;

    @Column(name = "mileage", nullable = false, length = 255)
    @Convert(converter = MemberMileageConverter.class)
    private MemberMileage mileage;

    @Column(name = "address", nullable = false, length = 100)
    @Convert(converter = MemberAddressConverter.class)
    private MemberAddress address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false)
    private MemberRole role;

    @Builder
    public Member(
        final MemberEmail email,
        final MemberPassword password,
        final MemberName name,
        final MemberPhoneNumber phoneNumber,
        final MemberMileage mileage,
        final MemberAddress address,
        final MemberRole role
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mileage = mileage;
        this.address = address;
        this.role = role;
    }

}
