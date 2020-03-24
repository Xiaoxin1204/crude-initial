package com.longshare.microservice.demo.crude.controller;

import com.longshare.microservice.demo.crude.controller.UserController;
import com.longshare.microservice.demo.crude.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Generator on 2020-03-24 10:32:10.
 */

@WebMvcTest(UserController.class)
@Import(UserServiceImpl.class)
public class UserGSDocumentation extends DocumentationSupport {

    @Test
    public void list() throws Exception {
        this.mockMvc.perform(get("/v1/users").header(AUTHORIZATION_HEADER,token).accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(document("user-list"));
    }

    @Test
    public void creatingAUser() throws Exception {
        String userLocation = createUser();
        MvcResult user = getUser(userLocation);

        modifyExistingUser(userLocation);
        deleteUser(userLocation);
    }

    private void deleteUser(String userLocation) throws Exception {
        this.mockMvc.perform(delete(userLocation).header(AUTHORIZATION_HEADER,token))
                .andExpect(status().isNoContent())
                .andDo(document("user/{step}/"));
    }

    private String createUser() throws Exception {
        Map<String, String> user = new HashMap<String, String>();
        //user.put("id", "1");
        //user.put("username", "1");
        //user.put("password", "1");
        //user.put("name", "1");
        //user.put("creator", "1");
        //user.put("createdAt", "1");
        //user.put("updater", "1");
        //user.put("updatedAt", "1");

        String userLocation = this.mockMvc
                .perform(
                        post("/v1/users")
                                .header(AUTHORIZATION_HEADER,token)
                                .contentType(MediaTypes.HAL_JSON_UTF8)
                                .content(
                                    objectMapper.writeValueAsString(user)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andDo(document("user/{step}/"))
                .andReturn().getResponse().getHeader("Location");
        return userLocation;
    }

    private MvcResult getUser(String userLocation) throws Exception {
        return this.mockMvc.perform(get(userLocation).header(AUTHORIZATION_HEADER,token))
                .andExpect(status().isOk())
                //TODO
                //.andExpect(jsonPath("id", is(notNullValue())))
                //.andExpect(jsonPath("username", is(notNullValue())))
                //.andExpect(jsonPath("password", is(notNullValue())))
                //.andExpect(jsonPath("name", is(notNullValue())))
                //.andExpect(jsonPath("creator", is(notNullValue())))
                //.andExpect(jsonPath("createdAt", is(notNullValue())))
                //.andExpect(jsonPath("updater", is(notNullValue())))
                //.andExpect(jsonPath("updatedAt", is(notNullValue())))
                .andExpect(jsonPath("_links.self", is(notNullValue())))
                .andDo(document("user/{step}/"))
                .andReturn();
    }

    void modifyExistingUser(String userLocation) throws Exception {
        String modifyProperty = "userId";
        Map<String, Object> update = new HashMap<String, Object>();
        
        //TODO
        //update.put("id", "1");
        //update.put("username", "1");
        //update.put("password", "1");
        //update.put("name", "1");
        //update.put("creator", "1");
        //update.put("createdAt", "1");
        //update.put("updater", "1");
        //update.put("updatedAt", "1");

        this.mockMvc.perform(
                patch(userLocation)
                    .header(AUTHORIZATION_HEADER,token)
                    .contentType(MediaTypes.HAL_JSON_UTF8)
                    .content(
                        objectMapper.writeValueAsString(update)
                    )
        )
                .andExpect(status().isOk())
                .andDo(document("user/{step}/"));

        //验证
        this.mockMvc.perform(get(userLocation)
                .header(AUTHORIZATION_HEADER,token)
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("id", is("1")))
                //.andExpect(jsonPath("username", is("1")))
                //.andExpect(jsonPath("password", is("1")))
                //.andExpect(jsonPath("name", is("1")))
                //.andExpect(jsonPath("creator", is("1")))
                //.andExpect(jsonPath("createdAt", is("1")))
                //.andExpect(jsonPath("updater", is("1")))
                //.andExpect(jsonPath("updatedAt", is("1")))
                .andDo(document("user/{step}/"));
    }
}
