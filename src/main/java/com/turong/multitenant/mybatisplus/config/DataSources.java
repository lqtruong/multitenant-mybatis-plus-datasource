package com.turong.multitenant.mybatisplus.config;

import lombok.Getter;

public enum DataSources {

    MASTER("master"),
    IND("ind"),
    TR("tr");

    @Getter
    private String tenant;

    DataSources(final String tenant) {
        this.tenant = tenant;
    }


}
