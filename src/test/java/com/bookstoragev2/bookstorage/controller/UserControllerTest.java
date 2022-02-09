package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.domain.RoleType;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.user.UserRepository;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@IntergrationTestWithRestDocs
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(restDocumentation)).build();
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("성공:회원가입을 한다")
    public void signUp() throws Exception {
        UserSignUpDto signUp = new UserSignUpDto();
        signUp.setUserId("testUser");
        signUp.setPassword("password");
        signUp.setNickname("닉네임");
        signUp.setEmail("email@email.com");

        ResultActions resultActions = mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUp)));


        resultActions.andExpect(
                        MockMvcResultMatchers.status().isCreated())
                .andDo(document("users",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        getRequestFieldsSnippet(),
                        getResponseFieldsSnippet()));
    }


    @Test
    @DisplayName("성공:현재 로그인한 유저의 정보를 가져온다")
    @WithUser("test@test.com")
    public void getLoginUserinfo() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(
                        MockMvcResultMatchers.status().isOk())
                .andDo(document("users/me",
                        ApiDocumentUtils.getDocumentResponse(),
                        getResponseFieldsSnippet()));
    }

    @Test
    @DisplayName("실패:이미 존재하는 회원으로 회원가입을 한다")
    public void signUp_existUser() throws Exception {
        UserSignUpDto signUp = new UserSignUpDto();
        signUp.setUserId("testUser");
        signUp.setPassword("password");
        signUp.setNickname("닉네임");
        signUp.setEmail("email@email.com");
        User user = new User("testUSer", "email@eamil.com", "닉네임", "1234", RoleType.ROLE_USER, true);
        userRepository.save(user);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUp)));


        resultActions.andExpect(
                        MockMvcResultMatchers.status().isConflict())
                .andDo(document("users/exist",
                        ApiDocumentUtils.getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("에러번호"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("에러 사유")
                        )));
    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                fieldWithPath("result.nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("result.email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("result.profileImageUrl").type(JsonFieldType.STRING).description("프로파일이미지 위치"));
    }

    private RequestFieldsSnippet getRequestFieldsSnippet() {
        return requestFields(
                fieldWithPath("userId").type(JsonFieldType.STRING).description("유저의 ID"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("유저의 이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"));
    }
}