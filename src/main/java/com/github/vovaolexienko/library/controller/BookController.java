package com.github.vovaolexienko.library.controller;

import com.github.vovaolexienko.library.dto.GetBooksDto;
import com.github.vovaolexienko.library.dto.SaveBookDto;
import com.github.vovaolexienko.library.entity.Book;
import com.github.vovaolexienko.library.service.AuthorService;
import com.github.vovaolexienko.library.service.BookService;
import com.github.vovaolexienko.library.service.GenreService;
import com.github.vovaolexienko.library.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final VoteService voteService;

    @GetMapping(value = {"", "/", "/book"})
    public String mainPage(Model model, GetBooksDto getBooksDto) {
        if (Objects.nonNull(getBooksDto.getGenre())) {
            model.addAttribute("genre", genreService.getGenre(getBooksDto.getGenre()).getName());
        } else if (StringUtils.isNotBlank(getBooksDto.getAuthorOrTitle())) {
            model.addAttribute("authorOrTitle", getBooksDto.getAuthorOrTitle());
        }
        Page<Book> bookPage = bookService.getBookPage(getBooksDto);
        model.addAttribute("page", bookPage);
        model.addAttribute("topFiveBooks", bookService.getMostPopularBooks());
        model.addAttribute("pageNumbers", getPagesNumbers(bookPage));
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("authors", authorService.findAuthors());
        model.addAttribute("newBook", new SaveBookDto());
        return "index";
    }

    @ResponseBody
    @GetMapping(value = {"/openBook"}, produces = "application/pdf")
    public byte[] openBook(@RequestParam("bookId") Long bookId) {
        return bookService.openBook(bookId);
    }

    @PostMapping(value = {"/saveBook"})
    public RedirectView saveBook(@ModelAttribute SaveBookDto saveBookDto) {
        bookService.saveBook(saveBookDto);
        return new RedirectView("/book");
    }

    @GetMapping(value = {"/deleteBook"})
    public RedirectView deleteBook(@RequestParam("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return new RedirectView("/book");
    }

    @PostMapping(value = {"/voting"})
    public void vote(@RequestParam("bookId") Long bookId, @RequestParam("vote") Integer newVoteValue) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        voteService.saveVote(user.getUsername(), bookId, newVoteValue);
    }

    public List<Integer> getPagesNumbers(Page<?> page) {
        int totalPagesNumber = page.getTotalPages();
        int currentPage = page.getNumber();
        if (totalPagesNumber > 0 && totalPagesNumber <= 10) {
            return IntStream.rangeClosed(0, totalPagesNumber - 1).boxed().toList();
        }
        if (totalPagesNumber > 10 && currentPage >= 4) {
            return IntStream.rangeClosed(currentPage - 4, currentPage + 5).boxed().toList();
        }
        if (totalPagesNumber > 10) {
            return IntStream.rangeClosed(0, 9).boxed().toList();
        }
        return Collections.emptyList();
    }
}