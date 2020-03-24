package com.longshare.microservice.demo.crude;

import com.longshare.microservice.demo.crude.Application;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* Created by Generator on 2020-03-24 10:32:10.
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class SwaggerDocumentation{

    static String SNIPPET_BASE_URI = "target/generated-snippets/swagger";
    /**
    * swagger生成的时候需要通过系统配置来传递
    */
    static {
        System.setProperty("swagger2markup.extensions.springRestDocs.snippetBaseUri", SNIPPET_BASE_URI);
        System.setProperty("swagger2markup.extensions.springRestDocs.defaultSnippets", "true");
    }

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createSwaggerJson() throws Exception {
        //将api-docs的json数据写入文件
        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String swaggerJson = response.getContentAsString();
        Files.createDirectories(Paths.get(SNIPPET_BASE_URI));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SNIPPET_BASE_URI, "swagger.json"), StandardCharsets.UTF_8)) {
            writer.write(swaggerJson);
        }

        //生成API文档
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
        .withOutputLanguage(Language.ZH)
        .withPathsGroupedBy(GroupBy.TAGS)
        .withGeneratedExamples()
        .build();
        Swagger2MarkupConverter.from(swaggerJson)
        .withConfig(config)
        .build()
        .toFolder(Paths.get(SNIPPET_BASE_URI));
    }
}
