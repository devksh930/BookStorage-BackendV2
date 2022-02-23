package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.bookpost.dto.BookPostAddDto;
import com.bookstoragev2.bookstorage.bookpost.dto.BookPostResponseDto;
import com.bookstoragev2.bookstorage.bookstorage.BookStorageRepository;
import com.bookstoragev2.bookstorage.domain.BookPost;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.bookstoragev2.bookstorage.domain.BookStorage;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookPostService {
    private final BookPostRepository bookPostRepository;
    private final BookStorageRepository bookStorageRepository;

    public BookPostResponseDto addBookPost(User user, Long bookStorageId, BookPostAddDto bookPostAddDto) {
        Optional<BookStorage> byId = bookStorageRepository.findByIdAndUser(bookStorageId, user);
        BookStorage bookStorage = byId.orElseThrow(() -> new RuntimeException("책을 먼저 등록하고 작성할수 있습니다."));
//        isBookStorageOwner(bookStorage, user);
        BookPost bookPost = BookPost.builder()
                .bookPostType(bookPostAddDto.getBookPostType())
                .bookStorage(bookStorage)
                .title(bookPostAddDto.getTitle())
                .content(bookPostAddDto.getContent())
                .isDeleted(false)
                .isReportPrivate(false)
                .build();
        BookPost save = bookPostRepository.save(bookPost);
        return new BookPostResponseDto(save);
    }

    public Page<BookPost> getBookPostWithType(BookPostType bookPostType, Pageable pageable) {
        return bookPostRepository.findByBookPostType(bookPostType, pageable);
    }

    public Page<BookPost> getBookPostWithCurrentUser(User user, Pageable pageable) {
        return bookPostRepository.findByBookStorageUser(user, pageable);
    }

    public BookPostResponseDto getBookPostDetails(Long bookPostId) {
        BookPost bookPost = bookPostRepository.findById(bookPostId)
                .orElseThrow(() -> new RuntimeException("찾는 포스트가 없음"));
        bookPost.postCount();
        return new BookPostResponseDto(bookPost);
    }

    public BookPostResponseDto modifyPost(User user, Long bookPostId, BookPostAddDto bookPostAddDto) {
        BookPost bookPost = bookPostRepository.findById(bookPostId)
                .orElseThrow(() -> new RuntimeException("찾는 포스트가 없음"));
        isBookPostOwner(bookPost, user);
        bookPost.bookPostModified(bookPostAddDto);
        return new BookPostResponseDto(bookPost);
    }

    private List<BookPostResponseDto> bookPostListToDto(List<BookPost> byBookPostType) {
        List<BookPostResponseDto> bookPostResponseDtos = new ArrayList<>();
        for (BookPost bookPost : byBookPostType) {
            BookPostResponseDto bookPostResponseDto = new BookPostResponseDto(bookPost);
            bookPostResponseDtos.add(bookPostResponseDto);
        }
        return bookPostResponseDtos;
    }

    private void isBookPostOwner(BookPost bookPost, User user) {
        if (!bookPost.isBookPostOwner(user)) {
            throw new RuntimeException("소유자가아님");
        }
    }
}
