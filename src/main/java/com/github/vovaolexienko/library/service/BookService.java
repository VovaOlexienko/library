package com.github.vovaolexienko.library.service;

import com.github.vovaolexienko.library.dto.GetBooksDto;
import com.github.vovaolexienko.library.dto.SaveBookDto;
import com.github.vovaolexienko.library.entity.Book;
import com.github.vovaolexienko.library.entity.Rating;
import com.github.vovaolexienko.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final Base64.Encoder base64Encoder;
    private final GenreService genreService;
    private final AuthorService authorService;

    public byte[] openBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.getRating().upViewsNumber();
        bookRepository.save(book);
        return book.getContent();
    }

    public Optional<Book> findBook(Long id) {
        return bookRepository.findById(id);
    }

    @SneakyThrows
    @Transactional
    public void saveBook(SaveBookDto saveBookDto) {
        Book book = new Book();
        book.setId(saveBookDto.getId());
        book.setName(saveBookDto.getName());
        book.setContent(saveBookDto.getContentParam().getBytes());
        book.setImage(base64Encoder.encodeToString(saveBookDto.getImageParam().getBytes()));
        book.setGenres(genreService.getGenres(saveBookDto.getGenres()));
        book.setAuthors(authorService.findAuthors(saveBookDto.getAuthors()));
        Rating rating = bookRepository.findById(book.getId())
                .map(Book::getRating)
                .orElseGet(Rating::new);
        book.setRating(rating);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> getMostPopularBooks() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "rating.viewsNumber"));
        return bookRepository.findAll(pageRequest).toList();
    }

    public Page<Book> getBookPage(GetBooksDto getBooksDto) {
        if (Objects.nonNull(getBooksDto.getGenre())) {
            return bookRepository.findByGenresId(getBooksDto.getGenre(), toPagination(getBooksDto.getPage()));
        }
        if (StringUtils.isNotBlank(getBooksDto.getAuthorOrTitle())) {
            return bookRepository.findByNameContainingIgnoreCaseOrAuthorsFioContainingIgnoreCase(getBooksDto.getAuthorOrTitle(), getBooksDto.getAuthorOrTitle(), toPagination(getBooksDto.getPage()));
        }
        return bookRepository.findAll(toPagination(getBooksDto.getPage()));
    }

    private PageRequest toPagination(int pageSize) {
        return PageRequest.of(pageSize, 20, Sort.by(Sort.Direction.ASC, "name"));
    }
}