package com.longshare.microservice.demo.crude.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDetail extends Menu{

    @ApiModelProperty("更新者")
    private String updater;

    @ApiModelProperty("创建者")
    private String creator;
}