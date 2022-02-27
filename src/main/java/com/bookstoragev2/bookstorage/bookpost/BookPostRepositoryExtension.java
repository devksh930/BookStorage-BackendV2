package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.bookpost.dto.BookPostResponseDto;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.bookstoragev2.bookstorage.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BookPostRepositoryExtension {

    Page<BookPostResponseDto> getBookPostWithTypeList(BookPostType bookPostType, Pageable pageable);

    Page<BookPostResponseDto> getBookPostWithUserList(User user, Pageable pageable);
}
