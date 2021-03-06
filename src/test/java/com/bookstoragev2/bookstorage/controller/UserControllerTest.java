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
    @DisplayName("??????:??????????????? ??????")
    public void signUp() throws Exception {
        UserSignUpDto signUp = new UserSignUpDto();
        signUp.setUserId("testUser");
        signUp.setPassword("password");
        signUp.setNickname("?????????");
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
    @DisplayName("??????:?????? ???????????? ????????? ????????? ????????????")
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
    @DisplayName("??????:??? ????????? ?????? ????????????.")
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
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("?????? id"),
                                fieldWithPath("result.title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("result.link").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("result.image").type(JsonFieldType.STRING).description("?????? ???????????????"),
                                fieldWithPath("result.author").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("result.publisher").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("result.isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("result.createdDate").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("result.modifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ??????")),

                        requestFields(
                                fieldWithPath("isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("readType").type(JsonFieldType.STRING).description("????????? ??????(\"READ\",\"READING\",\"NOT_READ\")")
                        )));

    }

    @Test
    @DisplayName("??????:??? ????????? ?????? ????????????.")
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
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                fieldWithPath("result").type(JsonFieldType.ARRAY).description("??????"),
                                fieldWithPath("result.[].id").type(JsonFieldType.NUMBER).description("?????? id"),
                                fieldWithPath("result.[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("result.[].link").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("result.[].image").type(JsonFieldType.STRING).description("?????? ???????????????"),
                                fieldWithPath("result.[].author").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("result.[].publisher").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("result.[].isbn").type(JsonFieldType.STRING).description("isbn"),
                                fieldWithPath("result.[].createdDate").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("result.[].modifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ??????"))
                        ));


    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {

        return responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                fieldWithPath("result.nickname").type(JsonFieldType.STRING).description("?????????"),
                fieldWithPath("result.email").type(JsonFieldType.STRING).description("?????????"),
                fieldWithPath("result.profileImageUrl").type(JsonFieldType.STRING).description("????????????????????? ??????"));
    }

    private RequestFieldsSnippet getRequestFieldsSnippet() {
        return requestFields(
                fieldWithPath("userId").type(JsonFieldType.STRING).description("????????? ID"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ?????????"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"));
    }
}