package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.user.UserRepository;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.user.dto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntergrationTestWithRestDocs
class AuthControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }
    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("로그인을 한다")
    @WithUser("test@test.com")
    public void login() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("test@test.com");
        loginDto.setPassword("1234");

        ResultActions resultActions = mockMvc.perform(post("/auth")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginDto)));

        resultActions.andExpect(
                        status().isOk())
                .andDo(
                        document(
                                "auth",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                responseHeaders(
                                        headerWithName(HttpHeaders.SET_COOKIE).description("액세스토큰 쿠키"),
                                        headerWithName(HttpHeaders.SET_COOKIE).description("리프레쉬 토큰 쿠키")
                                )
                        ));
    }
}