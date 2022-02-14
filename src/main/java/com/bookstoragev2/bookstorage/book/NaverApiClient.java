package com.bookstoragev2.bookstorage.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverApiClient {
    private final RestTemplate restTemplate;

    @Value("${naver.clientID}")
    private String clientId;
    @Value("${naver.clientSecret}")
    private String clientSecret;
    @Value("${naver.apiURL}")
    private String apiURL;
    @Value("${naver.bookSearchPath}")
    private String bookSearchPath;


    private RequestEntity<Void> requestEntity(URI uri) {
        return RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    public ResponseEntity<BookDto> getBookToQuery(String query, int start) {
        log.info("requestQueryParam query: {}", query);
        log.info("requestQueryParam start: {}", start);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(apiURL + bookSearchPath)
                .queryParam("query", query)
                .queryParam("start", start)
                .encode()
                .build()
                .toUri();

        log.info("Request API URL {}", uri);

        return restTemplate.exchange(requestEntity(uri), BookDto.class);

    }
}
