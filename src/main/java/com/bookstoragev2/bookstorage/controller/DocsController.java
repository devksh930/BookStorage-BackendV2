package com.bookstoragev2.bookstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docs")
public class DocsController {
    @GetMapping
    public String getAPIdocs() {
        return "/docs/api-guide.html";
    }
}
