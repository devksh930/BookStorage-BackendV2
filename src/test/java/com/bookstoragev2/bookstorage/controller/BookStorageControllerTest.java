package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageAddDto;
import com.bookstoragev2.bookstorage.config.ApiDocumentUtils;
import com.bookstoragev2.bookstorage.config.IntergrationTestWithRestDocs;
import com.bookstoragev2.bookstorage.config.WithUser;
import com.bookstoragev2.bookstorage.domain.BookReadType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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
class BookStorageControllerTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .build();

    }

    @Test
    @DisplayName("성공:내 서재에 책을 등록한다.")
    @WithUser("test@test.com")
    @Order(1)
    @Transactional
    public void addBookstorage() throws Exception {
        BookStorageAddDto bookStorageAddDto = new BookStorageAddDto();
        bookStorageAddDto.setIsbn("9788993827446");
        bookStorageAddDto.setReadType(BookReadType.READ);
        ResultActions resultActions = mockMvc.perform(post("/bookstorage")
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
                                fieldWithPath("result.bookReadType").type(JsonFieldType.STRING).description("책의 읽음 상태"),
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
    @Order(2)
    @Transactional
    public void getBookStorage() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/bookstorage")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

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
                                fieldWithPath("result.[].bookReadType").type(JsonFieldType.STRING).description("책의 읽음 상태"),
                                fieldWithPath("result.[].createdDate").type(JsonFieldType.STRING).description("서재 추가 날짜"),
                                fieldWithPath("result.[].modifiedDate").type(JsonFieldType.STRING).description("서재 수정 날짜"))
                ));
    }

}