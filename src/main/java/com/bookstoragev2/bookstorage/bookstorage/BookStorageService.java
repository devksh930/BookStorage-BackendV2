package com.bookstoragev2.bookstorage.bookstorage;

import com.bookstoragev2.bookstorage.book.BookService;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageAddDto;
import com.bookstoragev2.bookstorage.bookstorage.dto.BookStorageResponseDto;
import com.bookstoragev2.bookstorage.domain.Book;
import com.bookstoragev2.bookstorage.domain.BookStorage;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.error.exception.BookStorageExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStorageService {
    private final BookStorageRepository bookStorageRepository;
    private final BookService bookService;

    public List<BookStorageResponseDto> getBookStorage(User user) {
        List<BookStorage> bookStorages = bookStorageRepository.findByUser(user);

        return bookStorages.stream()
                .map(BookStorageResponseDto::new)
                .collect(Collectors.toList());
    }

    public BookStorageResponseDto addBookStorage(User user, BookStorageAddDto bookStorageAddDto) {
        existByUserAndBook(user, bookStorageAddDto.getIsbn());
        Book book = bookService.apiSearchAndBookAdd(bookStorageAddDto.getIsbn());
        BookStorage bookStorage = new BookStorage(bookStorageAddDto.getReadType(), user, book);
        bookStorageRepository.save(bookStorage);
        return new BookStorageResponseDto(bookStorage);
    }

    private void existByUserAndBook(User user, String isbn) {
        if(bookStorageRepository.existsByUserAndBook(user, isbn)){
            throw new BookStorageExistException();
        }
    }
}
