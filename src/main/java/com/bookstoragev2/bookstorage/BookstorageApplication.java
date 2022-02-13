package com.bookstoragev2.bookstorage;

import com.bookstoragev2.bookstorage.common.config.properties.CorsProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
})
public class BookstorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstorageApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
