package com.woowacamp.soolsool.core.member.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(name = "email", length = 255)
    private String email;


    @Column(name = "password")
    private String password;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "mileage", length = 255)
    private String mileage;

    @Column(name = "address", length = 100)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    private RoleType role;

    @Builder
    public Member(
        String email,
        String password,
        String phoneNumber,
        String address,
        RoleType role
    ) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
}
