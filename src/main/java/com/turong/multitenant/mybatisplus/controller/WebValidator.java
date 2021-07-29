package com.turong.multitenant.mybatisplus.controller;

public interface WebValidator<WebRequest> {

    boolean validate(final WebRequest request);

}
