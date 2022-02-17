package com.bookstoragev2.bookstorage.common;

import com.bookstoragev2.bookstorage.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.util.LinkedHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
       ErrorResponse errorMessage = toErrorResponse(response.getBody());
        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

            log.debug("=====서버에러 ======{}", response.getBody());
            throw new ServerException("ServoerError");

        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            log.info("=====클라이언트에러 ======{}", errorMessage);

            throw new BookAPIBadRequest(errorMessage);
        }
    }
    public ErrorResponse toErrorResponse(InputStream inputStream) {
        JSONParser jsonParser = new JSONParser(inputStream);
        LinkedHashMap<String, Object> json = null;
        try {
            json = jsonParser.object();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String errorMessage = (String) json.get("errorMessage");
        String errorCode = (String) json.get("errorCode");
        return new ErrorResponse(400,errorCode,errorMessage);
    }
}
