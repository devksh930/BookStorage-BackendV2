package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.book.BookRepository;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntergrationTestWithRestDocs
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }


    @Test
    @DisplayName("책을 검색한다.")
    public void searchBook() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/books")
                .queryParam("query", "전문가를 위한")
                .queryParam("start", "1")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(
                        status().isOk())
                .andDo(
                        document(
                                "book",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("query").description("검색할 키워드(예: 저자, 책재목 등등)"),
                                        parameterWithName("start").description("시작할 요소의 갯수 1부터 시작")
                                ),
                                responseFields(
                                        fieldWithPath("success").description("성공여부"),
                                        fieldWithPath("result").description("호출된 결과값"),
                                        fieldWithPath("result.lastBuildDate").description("검색시간"),
                                        fieldWithPath("result.total").description("검색결과 총 개수"),
                                        fieldWithPath("result.start").description("검색결과에서 시작 요소(1부터시작)"),
                                        fieldWithPath("result.display").description("보여지는 갯수"),
                                        fieldWithPath("result.items").description("검색된 책 리스트"),
                                        fieldWithPath("result.items[].title").description("책의 제목"),
                                        fieldWithPath("result.items[].link").description("책의 링크"),
                                        fieldWithPath("result.items[].image").description("책의 이미지링크"),
                                        fieldWithPath("result.items[].author").description("저자"),
                                        fieldWithPath("result.items[].price").description("정가격"),
                                        fieldWithPath("result.items[].discount").description("할인가격"),
                                        fieldWithPath("result.items[].publisher").description("출판사"),
                                        fieldWithPath("result.items[].pubdate").description("출시일"),
                                        fieldWithPath("result.items[].isbn").description("isbn"),
                                        fieldWithPath("result.items[].description").description("설명")
                                )
                        )
                );

    }

    @DisplayName("책을 ISBN으로 상세검색한다")
    @Test
    public void searchBookDetail() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/books/{isbn}", "9791160506815")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(
                        status().isOk())
                .andDo(
                        document(
                                "book/detail",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("isbn").description("책의 ISBN")
                                ), responseFields(

                                        fieldWithPath("success").description("성공여부"),
                                        fieldWithPath("result").description("호출된 결과값"),
                                        fieldWithPath("result.title").description("책의 제목"),
                                        fieldWithPath("result.link").description("책의 링크"),
                                        fieldWithPath("result.image").description("책의 이미지링크"),
                                        fieldWithPath("result.author").description("저자"),
                                        fieldWithPath("result.price").description("정가격"),
                                        fieldWithPath("result.discount").description("할인가격"),
                                        fieldWithPath("result.publisher").description("출판사"),
                                        fieldWithPath("result.pubdate").description("출시일"),
                                        fieldWithPath("result.isbn").description("isbn"),
                                        fieldWithPath("result.description").description("설명")
                                )));
    }
}