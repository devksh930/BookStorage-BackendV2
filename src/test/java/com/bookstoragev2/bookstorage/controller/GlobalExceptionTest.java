package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.domain.RoleType;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.user.UserRepository;
import com.bookstoragev2.bookstorage.user.dto.UserLoginDto;
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
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntergrationTestWithRestDocs
public class GlobalExceptionTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("??????:?????? ???????????? ???????????? ??????????????? ??????")
    public void signUp_existUser() throws Exception {
        UserSignUpDto signUp = new UserSignUpDto();
        signUp.setUserId("testUser");
        signUp.setPassword("password");
        signUp.setNickname("?????????");
        signUp.setEmail("email@email.com");
        User user = new User("testUSer", "email@eamil.com", "?????????", "1234", RoleType.ROLE_USER);
        userRepository.save(user);

        ResultActions resultActions = mockMvc
                .perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(signUp)));


        resultActions
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(document("users/exist",
                        ApiDocumentUtils.getDocumentResponse(),
                        getResponseFieldsSnippet()));
    }


    @Test
    @DisplayName("??????: ????????? ???????????? ???????????? ???????????? ??????")
    @WithUser("test@test.com")
    public void login_fail() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("test@test.com");
        loginDto.setPassword("12345");

        ResultActions resultActions = mockMvc
                .perform(post("/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(loginDto)));

        resultActions
                .andExpect(status().isBadRequest())
                .andDo(document("auth/fail",
                        ApiDocumentUtils.getDocumentResponse(),
                        getResponseFieldsSnippet()));

    }

    @Test
    @DisplayName("??????:?????? ???????????? ????????? ????????? ???????????? redirect")
    public void getLoginUserinfo() throws Exception {
        mockMvc.perform(get("/users/me")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("AuthenticationEntrypoint exception ?????????")
    public void authenticationEntryPointException() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/me")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        resultActions
                .andExpect(status().isUnauthorized())
                .andDo(document("auth/requiredLogin",
                        ApiDocumentUtils.getDocumentResponse(),
                        getResponseFieldsSnippet()));

    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP ????????????"),
                fieldWithPath("code").type(JsonFieldType.STRING).description("????????????"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ??????"));
    }
}
