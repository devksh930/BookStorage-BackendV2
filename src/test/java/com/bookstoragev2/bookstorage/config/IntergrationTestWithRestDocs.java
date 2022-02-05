package com.bookstoragev2.bookstorage.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Target(ElementType.TYPE)
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public @interface IntergrationTestWithRestDocs {
}
