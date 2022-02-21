package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageAddDto;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.domain.BookReadType;
import com.bookstoragev2.bookstorage.user.UserRepository;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Order(1)
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
                        status().isCreated())
                .andDo(document("users",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        getRequestFieldsSnippet(),
                        getResponseFieldsSnippet()));
    }


    @Test
    @DisplayName("성공:현재 로그인한 유저의 정보를 가져온다")
    @Order(2)
    @WithUser("test@test.com")
    public void getLoginUserinfo() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));


        resultActions.andExpect(
                        status().isOk())
                .andDo(document("users/me",
                        ApiDocumentUtils.getDocumentResponse(),
                        getResponseFieldsSnippet()));
    }

    @Test
    @DisplayName("성공:내 서재에 책을 등록한다.")
    @WithUser("test@test.com")
    @Order(3)
    @Transactional
    public void addBookstorage() throws Exception {
        BookStorageAddDto bookStorageAddDto = new BookStorageAddDto();
        bookStorageAddDto.setIsbn("9788993827446");
        bookStorageAddDto.setReadType(BookReadType.READ);
        ResultActions resultActions = mockMvc.perform(post("/users/books")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(this.objectMapper.writeValueAsString(bookStorageAddDto))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                .andDo(document("users/book/add",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("서재 id"),
                                fieldWithPath("result.title").type(JsonFieldType.STRING).description("책의 제목"),
                                fieldWithPath("result.link").type(JsonFieldType.STRING).description("책의 링크"),
                                fieldWithPath("result.image").type(JsonFieldType.STRING).description("책의 이미지링크"),
                                fieldWithPath("result.author").type(JsonFieldType.STRING).description("저자"),
                                fieldWithPath("result.publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("result.isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("result.createdDate").type(JsonFieldType.STRING).description("서재 추가 날짜"),
                                fieldWithPath("result.modifiedDate").type(JsonFieldType.STRING).description("서재 수정 날짜")),

                        requestFields(
                                fieldWithPath("isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("readType").type(JsonFieldType.STRING).description("책읽음 여부(\"READ\",\"READING\",\"NOT_READ\")")
                        )));

    }

    @Test
    @DisplayName("성공:내 서재에 책을 가져온다.")
    @WithUser("test@test.com")
    @Order(4)
    @Transactional
    public void getBookStorage() throws Exception {
        BookStorageAddDto bookStorageAddDto = new BookStorageAddDto();
        bookStorageAddDto.setIsbn("9788993827446");
        bookStorageAddDto.setReadType(BookReadType.READ);
        mockMvc.perform(post("/users/books")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(this.objectMapper.writeValueAsString(bookStorageAddDto))
                .contentType(MediaType.APPLICATION_JSON));


        ResultActions resultActions = mockMvc.perform(get("/users/books")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andDo(document("users/book/get",
                        ApiDocumentUtils.getDocumentResponse(),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("result").type(JsonFieldType.ARRAY).description("결과"),
                                fieldWithPath("result.[].id").type(JsonFieldType.NUMBER).description("서재 id"),
                                fieldWithPath("result.[].title").type(JsonFieldType.STRING).description("책의 제목"),
                                fieldWithPath("result.[].link").type(JsonFieldType.STRING).description("책의 링크"),
                                fieldWithPath("result.[].image").type(JsonFieldType.STRING).description("책의 이미지링크"),
                                fieldWithPath("result.[].author").type(JsonFieldType.STRING).description("저자"),
                                fieldWithPath("result.[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("result.[].isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("result.[].createdDate").type(JsonFieldType.STRING).description("서재 추가 날짜"),
                                fieldWithPath("result.[].modifiedDate").type(JsonFieldType.STRING).description("서재 수정 날짜"))
                        ));


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