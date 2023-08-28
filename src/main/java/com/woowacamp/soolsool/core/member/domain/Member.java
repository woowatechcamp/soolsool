package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.core.member.code.MemberErrorCode;
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
import com.woowacamp.soolsool.core.member.dto.request.MemberModifyRequest;
import com.woowacamp.soolsool.global.common.BaseEntity;
import com.woowacamp.soolsool.global.exception.SoolSoolException;
import java.math.BigInteger;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private MemberRole role;

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


    @Builder
    public Member(
        final MemberRole role,
        final String email,
        final String password,
        final String name,
        final String phoneNumber,
        final String mileage,
        final String address
    ) {
        this.role = role;
        this.email = new MemberEmail(email);
        this.password = new MemberPassword(password);
        this.name = new MemberName(name);
        this.phoneNumber = new MemberPhoneNumber(phoneNumber);
        this.mileage = new MemberMileage(new BigInteger(mileage));
        this.address = new MemberAddress(address);
    }

    public void update(final MemberModifyRequest memberModifyRequest) {
        this.password = new MemberPassword(memberModifyRequest.getPassword());
        this.name = new MemberName(memberModifyRequest.getName());
        this.address = new MemberAddress(memberModifyRequest.getAddress());
    }

    public void updateMileage(final String amount) {
        this.mileage = this.mileage.charge(new MemberMileage(new BigInteger(amount)));
    }

    public void updateMileage2(final BigInteger mileage) {
        this.mileage = this.mileage.charge(new MemberMileage(mileage));
    }

    public boolean matchPassword(final String password) {
        return this.password.matchPassword(password);
    }

    public BigInteger getMileage() {
        return mileage.getMileage();
    }

    public void decreaseMileage(final BigInteger mileageUsage) {
        if (this.mileage.isLessThan(new MemberMileage(mileageUsage))) {
            throw new SoolSoolException(MemberErrorCode.NOT_ENOUGH_MILEAGE);
        }

        this.mileage = this.mileage.subtract(new MemberMileage(mileageUsage));
    }

    public String getRoleName() {
        return this.role.getName().getType();
    }
}
