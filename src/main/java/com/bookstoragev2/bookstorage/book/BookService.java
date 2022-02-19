package com.bookstoragev2.bookstorage.book;

import com.bookstoragev2.bookstorage.domain.Book;
import com.bookstoragev2.bookstorage.error.exception.NotFoundBookDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final NaverApiClient naverApiClient;
    private final ModelMapper modelMapper;

    public BookDto searchBook(String query, int start) {
        return naverApiClient.getBookToQuery(query, start).getBody();
    }

    public BookItemDto searchBookDetail(String isbn) {
        Book book = apiSearchAndBookAdd(isbn);
        return modelMapper.map(book, BookItemDto.class);
    }

    public Book apiSearchAndBookAdd(String isbn) {
        return bookRepository.findByIsbnLike(isbn)
                .orElseGet(() -> addBook(findIsbnWithAPI(isbn)));
    }

    private Book addBook(BookDto bookDto) {
        BookItemDto item = bookDto.getItems().get(0);
        Book book = new Book(item.getTitle(), item.getLink(), item.getImage(), item.getAuthor(), item.getPrice(), item.getDiscount(), item.getPublisher(), item.getPubdate(), item.getIsbn(), item.getDescription());
        return bookRepository.save(book);
    }

    private BookDto findIsbnWithAPI(String isbn) {
        BookDto body = naverApiClient.getBookToIsbn(isbn).getBody();
        return itemSizeCheck(Objects.requireNonNull(body));
    }

    private BookDto itemSizeCheck(BookDto bookDto) {
        List<BookItemDto> items = bookDto.getItems();
        if (items.isEmpty() || items == null) {
            throw new NotFoundBookDetail();
        }
        return bookDto;
    }
}
