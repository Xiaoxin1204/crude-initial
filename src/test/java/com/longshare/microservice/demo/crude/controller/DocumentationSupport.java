package com.longshare.microservice.demo.crude.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.longshare.rest.core.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.restdocs.hypermedia.LinksSnippet;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

/**
 * Created by Generator on 2020-03-24 10:32:10.
 */
 
@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@AutoConfigureRestDocs
@Slf4j
//@Import(AuthServiceImpl.class)
//@WebMvcTest(AuthController.class)
public abstract class DocumentationSupport {
    protected String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String token="test_-_";

    protected final LinksSnippet pagingLinks = links(
            linkWithRel("first").optional().description("第一页"),
            linkWithRel("last").optional().description("最后一页"),
            linkWithRel("next").optional().description("下一页"),
            linkWithRel("prev").optional().description("上一页"));

    protected String getTestId(){
        return token + UUIDUtil.generateShortUuid();
    }
}
