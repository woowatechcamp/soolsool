package com.woowacamp.soolsool.core.liquor.domain;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Region {

    GYEONGGIDO("경기도");
    
    @Column(name = "name", length = 20)
    private String name;


}
