package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
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
    private MemberEmail email;

    @Column(name = "password", nullable = false, length = 60)
    private MemberPassword password;

    @Column(name = "name", nullable = false, length = 20)
    private MemberName name;

    @Column(name = "phone_number", nullable = false, length = 13)
    private MemberPhoneNumber phoneNumber;

    @Column(name = "mileage", nullable = false, length = 255)
    private MemberMileage mileage;

    @Column(name = "address", nullable = false, length = 100)
    private MemberAddress address;

    @JoinColumn(name = "role")
    @ManyToOne(fetch = FetchType.LAZY)
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
