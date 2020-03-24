package com.longshare.microservice.demo.crude.controller;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;

import javax.servlet.RequestDispatcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

/**
 * Created by Generator on 2020-03-24 10:32:10.
 */

@WebMvcTest(IndexController.class)
public class GettingStartedDocumentation extends DocumentationSupport {

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/index").accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.users", is(notNullValue())))
                .andExpect(jsonPath("_links.users", is(notNullValue())))
        .andDo(document("index"))
        .andDo(document("index-example",
					links(
							linkWithRel("users").description("The <<resources-users,user resource>>"),
							linkWithRel("users").description("The <<resources-users,user resource>>"),
        					linkWithRel("self").description("index resource")
        			),
					responseFields(
							subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources")
					)
			));
    }

    @Test
    public void problemExample() throws Exception {
        this.mockMvc
                .perform(get("/problem")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
                                "/resource/problem")
                        .requestAttr("code", "error")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "失败详细信息")
                )
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("type", is(notNullValue())))
                .andExpect(jsonPath("title", is("bad_request")))
                .andExpect(jsonPath("detail", is(notNullValue())))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("instance", is("/resource/problem")))
                .andDo(document("problem-example",
                        responseFields(
                                fieldWithPath("type").description("错误类型，详细介绍该错误的url地址"),
                                fieldWithPath("title").description("错误码, e.g. `error`"),
                                fieldWithPath("detail").description("错误详细描述"),
                                fieldWithPath("instance").description("发生错误的地址"),
                                fieldWithPath("status").description("HTTP状态码, e.g. `400`"),
                                fieldWithPath("timestamp").description("错误发生时间"))));
    }

    @Test
    public void errorExample() throws Exception {
        this.mockMvc
                .perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
                                "/error")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "does not exist"))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andDo(document("error-example",
                        responseFields(
                                fieldWithPath("error").description("HTTP错误码, e.g. `Bad Request`"),
                                fieldWithPath("message").description("错误描述"),
                                fieldWithPath("path").description("发生错误的地址"),
                                fieldWithPath("status").description("HTTP状态码, e.g. `400`"),
                                fieldWithPath("timestamp").description("错误发生时间"))));
    }
}
