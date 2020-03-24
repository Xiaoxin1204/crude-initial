package com.longshare.microservice.demo.crude;

import com.longshare.rest.core.problem.ProblemCodeDefinition;

/**
* Created by Generator on 2019-03-30 16:48:39.
*/
public enum ProblemCode implements ProblemCodeDefinition {
    LINK_CREATED_FAILED("创建连接失败"),
    ID_MISS("ID必传"),
    RESOURCE_CREATED_FAILED("资源创建失败"),
    RESOURCE_NOT_EXIST("资源不存在"),
    MENU_CODE_HAS_EXIST("菜单号已存在"),
    MENU_DELETIONS_PARAMS_NOT_EXIST("菜单批量删除入参不能为空"),
    UNDEFINED("未知异常");

    private String detail;

    ProblemCode(String detail) {
        this.detail = detail;
    }

    @Override
    public String getTitle() {
        return this.name().toLowerCase();
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String getTypeFormat() {
        return "https://example.org/problems/%s";
    }
}
