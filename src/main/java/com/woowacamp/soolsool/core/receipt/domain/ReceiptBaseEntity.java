package com.woowacamp.soolsool.core.receipt.domain;

import com.woowacamp.soolsool.global.common.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;


public class ReceiptBaseEntity extends BaseEntity {

    @Column(name = "expired_date")
    private LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(20);
}
