package com.longshare.microservice.demo.crude.controller;

import com.longshare.microservice.demo.crude.controller.UserController;
import com.longshare.microservice.demo.crude.model.User;
import com.longshare.microservice.demo.crude.service.UserService;
import com.longshare.microservice.demo.crude.service.impl.UserServiceImpl;
import com.longshare.rest.core.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Generator on 2020-03-24 10:32:10.
 */

@Slf4j
@WebMvcTest(UserController.class)
@Import(UserServiceImpl.class)
public class UserAPIDocumentation extends DocumentationSupport {

    @Autowired
	UserService userService;

	@Test
	public void userListExample() throws Exception {
		String testId = getTestId();
		User user = new User();
		user.setId(testId);
		//TODO
		userService.create(user);

		this.mockMvc.perform(get("/v1/users").header(AUTHORIZATION_HEADER,token))
			.andExpect(status().isOk())
			.andDo(document("user-list-example",
					this.pagingLinks.and(
							//linkWithRel("profile").description("The ALPS profile for this resource"),
							linkWithRel("create").description("创建此资源的规范链接"),
							linkWithRel("self").description("此资源的规范链接")
					),
					responseFields(
							subsectionWithPath("page").description("分页信息").type("Object"),
							subsectionWithPath("_embedded.users").optional().description("An array of <<resources-user, user resources>>"),
//							subsectionWithPath("_embedded.vos").optional().description("An array of <<resources-user, user resources>>"),
							subsectionWithPath("_links").description("<<resources-user-list-links, Links>> to other resources")
					)
			));

		//删除
		this.userService.deleteById(testId);
	}

	@Test
	public void userCreateExample() throws Exception {
		String testId = getTestId();
		User user = new User();
		user.setId(testId);
		//TODO 设置创建的bean

		this.mockMvc.perform(
				post("/v1/users").header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8).content(
						this.objectMapper.writeValueAsString(user)
				)
		)
				.andExpect(status().isCreated())
				.andDo(document("user-create-example",
						requestFields(
							    	fieldWithPath("id").description("用户id（对应邮箱）"),
							    	fieldWithPath("username").description("用户名"),
							    	fieldWithPath("password").description("密码"),
							    	fieldWithPath("name").description("姓名"),
							    	fieldWithPath("creator").description("创建者"),
							    	fieldWithPath("createdAt").description("创建时间"),
							    	fieldWithPath("updater").description("修改者"),
							    	fieldWithPath("updatedAt").description("修改时间"),
									fieldWithPath("id").description("主键")
						)
				));
		//删除
		this.userService.deleteById(testId);
	}

	@Test
	public void userGetExample() throws Exception {
		String testId = getTestId();
		User user = new User();
		user.setId(testId);
		//TODO
		//userService.create(user);

		String userLocation = this.mockMvc
				.perform(
						post("/v1/users").header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8).content(
								this.objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		this.mockMvc.perform(get(userLocation))
			.andExpect(status().isOk())
			//TODO 
			.andExpect(jsonPath("id", is(user.getId())))
			.andExpect(jsonPath("username", is(user.getUsername())))
			.andExpect(jsonPath("password", is(user.getPassword())))
			.andExpect(jsonPath("name", is(user.getName())))
			.andExpect(jsonPath("creator", is(user.getCreator())))
			.andExpect(jsonPath("createdAt", is(notNullValue())))
			.andExpect(jsonPath("updater", is(user.getUpdater())))
			.andExpect(jsonPath("updatedAt", is(notNullValue())))
			.andExpect(jsonPath("_links.self.href", is(userLocation)))
			.andExpect(jsonPath("_links.collection", is(notNullValue())))
			.andDo(print())
			.andDo(document("user-get-example",
					links(
							linkWithRel("patch").description("更新<<resources-user,user>>"),
							linkWithRel("delete").description("删除<<resources-user,user>>"),
							linkWithRel("collection").description("列表查询"),
							linkWithRel("self").description("Canonical link for this <<resources-user,user>>")
					),
					responseFields(
							fieldWithPath("id").description("用户id（对应邮箱）"),
							fieldWithPath("username").description("用户名"),
							fieldWithPath("password").description("密码"),
							fieldWithPath("name").description("姓名"),
							fieldWithPath("creator").description("创建者"),
							fieldWithPath("createdAt").description("创建时间"),
							fieldWithPath("updater").description("修改者"),
							fieldWithPath("updatedAt").description("修改时间"),
							subsectionWithPath("_links").description("<<resources-user-links,Links>> to other resources")
					)
			));

		//删除
		this.userService.deleteById(testId);
	}

	@Test
	public void userUpdateExample() throws Exception {
		String testId = getTestId();
		User user = new User();
		user.setId(testId);

		String tagLocation = this.mockMvc
				.perform(
						post("/v1/users").header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8).content(
								this.objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		User userUpdate = new User();
		userUpdate.setId(testId);
		//TODO 修改属性

		this.mockMvc.perform(
				patch(tagLocation).header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8).content(
						this.objectMapper.writeValueAsString(userUpdate)
				)
		)
				.andExpect(status().isOk())
				.andDo(document("user-update-example",
						requestFields(
							    fieldWithPath("id").description("用户id（对应邮箱）"),
							    fieldWithPath("username").description("用户名"),
							    fieldWithPath("password").description("密码"),
							    fieldWithPath("name").description("姓名"),
							    fieldWithPath("creator").description("创建者"),
							    fieldWithPath("createdAt").description("创建时间"),
							    fieldWithPath("updater").description("修改者"),
							    fieldWithPath("updatedAt").description("修改时间"),
								fieldWithPath("id").description("主键")

						)
				));
		//删除
		this.userService.deleteById(testId);
	}

	@Test
	public void userDeleteExample() throws Exception {
		String testId = getTestId();
		User user = new User();
		user.setId(testId);

		String tagLocation = this.mockMvc
				.perform(
						post("/v1/users").header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8).content(
								this.objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		this.mockMvc.perform(
				delete(tagLocation).header(AUTHORIZATION_HEADER,token).contentType(MediaTypes.HAL_JSON_UTF8)
		)
				.andExpect(status().isNoContent())
				.andDo(document("user-delete-example"));
	}
}
