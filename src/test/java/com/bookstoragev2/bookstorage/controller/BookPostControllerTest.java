package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.bookpost.dto.BookPostAddDto;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntergrationTestWithRestDocs
@Transactional
@Slf4j
class BookPostControllerTest {
    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(restDocumentation)).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("성공:내 bookPost의 Feed를 작성한다..")
    @WithUser("test@test.com")
    public void addBookPost() throws Exception {

        BookPostAddDto bookPostAddDto = new BookPostAddDto();
        bookPostAddDto.setBookPostType(BookPostType.FEED);
        bookPostAddDto.setTitle("게시글 제목");
        bookPostAddDto.setContent("게시글 내용");

        ResultActions resultActions = mockMvc.perform(post("/bookstorage/{bookStorageId}/bookposts", 5)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(this.objectMapper.writeValueAsString(bookPostAddDto))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isCreated())
                .andDo(
                        document(
                                "bookpost/add",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("bookStorageId").description("BookStorage의 ID")
                                ), getBookPostResponse(),
                                getBookPostRequest()
                        ));
    }

    @Test
    @DisplayName("성공:내 bookPost의 Feed를 수정한다.")
    @WithUser("test@test.com")
    public void modifyPost() throws Exception {
        BookPostAddDto bookPostAddDto = new BookPostAddDto();
        bookPostAddDto.setBookPostType(BookPostType.FEED);
        bookPostAddDto.setTitle("게시글 제목 수정");
        bookPostAddDto.setContent("게시글 내용 수정");

        ResultActions resultActions = mockMvc.perform(patch("/bookposts/{bookPostId}", 5)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(this.objectMapper.writeValueAsString(bookPostAddDto))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andDo(
                        document(
                                "bookpost/modify",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("bookPostId").description("BookPost의 ID")
                                ), getBookPostResponse(),
                                getBookPostRequest()
                        ));
    }

    @Test
    @DisplayName("성공: BookPostId로 게시글을 상세조회 한다.")
    public void getBookPostDetails() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/bookposts/{bookPostId}", 5)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andDo(
                        document("bookpost/getdetail",
                                ApiDocumentUtils.getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("bookPostId").description("BookPost의 ID")
                                ),
                                getBookPostResponse()
                        ));
    }

    @Test
    @DisplayName("성공 : BookPostType으로 게시글을 가져온다")
    public void getBookPostWithType() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/bookposts")
                .queryParam("type", BookPostType.FEED.name())
                .queryParam("size", "10")
                .queryParam("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andDo(
                        document("bookpost/getPostType",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("type").description("BookPost의 Type(FEED,REVIEW,SUMMARY)"),
                                        parameterWithName("page").description("불러올 page 0 부터 시작"),
                                        parameterWithName("size").description("한 페이지에 가져올 요소 수")
                                ),
                                getBookPostListResponse()
                        ));
    }

    @Test
    @DisplayName("성공 : 현재 로그인한 유저가 작성한 BookPost를 가져온다.")
    @WithUser("test@test.com")
    public void getBookPostWithCurrentUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/bookposts")
                .queryParam("size", "10")
                .queryParam("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions.andExpect(status().isOk())
                .andDo(
                        document("bookpost/getUser",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("page").description("불러올 page 0 부터 시작"),
                                        parameterWithName("size").description("한 페이지에 가져올 요소 수")
                                ),
                                getBookPostListResponse()
                        ));
    }

    private ResponseFieldsSnippet getBookPostListResponse() {
        return responseFields(
                fieldWithPath("success").description("성공여부"),
                fieldWithPath("size").description("한 페이지에 받아올 요소수"),
                fieldWithPath("currentPage").description("현재 페이지의 수"),
                fieldWithPath("totalElement").description("총 요소의 수"),
                fieldWithPath("result").description("호출된 결과값"),
                fieldWithPath("result.[].id").description("BookPost ID값"),
                fieldWithPath("result.[].title").description("BookPost 제목"),
                fieldWithPath("result.[].writer").description("BookPost 의 작성자 USER의 닉네임"),
                fieldWithPath("result.[].content").description("BookPost 내용"),
                fieldWithPath("result.[].count").description("BookPost 조회수"),
                fieldWithPath("result.[].bookPostType").description("BookPost 의 타입 FEED, REVIEW, SUMMARY"),
                fieldWithPath("result.[].createDate").description("글 작성 날짜Y"),
                fieldWithPath("result.[].modifiedDate").description("글 수정 날짜")
        );
    }

    private RequestFieldsSnippet getBookPostRequest() {
        return requestFields(
                fieldWithPath("bookPostType").description("BookPost 의 타입 FEED, REVIEW, SUMMARY"),
                fieldWithPath("title").description("BookPost 제목"),
                fieldWithPath("content").description("BookPost 내용")
        );
    }

    private ResponseFieldsSnippet getBookPostResponse() {
        return responseFields(
                fieldWithPath("success").description("성공여부"),
                fieldWithPath("result").description("호출된 결과값"),
                fieldWithPath("result.id").description("BookPost ID값"),
                fieldWithPath("result.title").description("BookPost 제목"),
                fieldWithPath("result.writer").description("BookPost 의 작성자 USER의 닉네임"),
                fieldWithPath("result.content").description("BookPost 내용"),
                fieldWithPath("result.count").description("BookPost 조회수"),
                fieldWithPath("result.bookPostType").description("BookPost 의 타입 FEED, REVIEW, SUMMARY"),
                fieldWithPath("result.createDate").description("글 작성 날짜Y"),
                fieldWithPath("result.modifiedDate").description("글 수정 날짜")
        );
    }
}

