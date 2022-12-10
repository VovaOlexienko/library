package com.github.vovaolexienko.library.controller;

import com.github.vovaolexienko.library.dto.SaveAuthorDto;
import com.github.vovaolexienko.library.dto.SaveBookDto;
import com.github.vovaolexienko.library.dto.SaveGenreDto;
import com.github.vovaolexienko.library.service.AuthorService;
import com.github.vovaolexienko.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final GenreService genreDao;
    private final AuthorService authorDao;

    @GetMapping(value = {"/author"})
    public String author(Model model) {
        model.addAttribute("genres", genreDao.getGenres());
        model.addAttribute("authors", authorDao.findAuthors());
        model.addAttribute("newBook", new SaveBookDto());
        model.addAttribute("newAuthor", new SaveAuthorDto());
        return "authors";
    }

    @PostMapping(value = {"/saveAuthor"})
    public RedirectView saveAuthor(@ModelAttribute SaveAuthorDto saveAuthorDto) {
        authorDao.saveAuthor(saveAuthorDto);
        return new RedirectView("/author");
    }

    @GetMapping(value = {"/deleteAuthor"})
    public RedirectView deleteAuthor(@RequestParam Long authorId) {
        authorDao.deleteAuthor(authorId);
        return new RedirectView("/author");
    }

    @GetMapping(value = {"/genre"})
    public String genre(Model model) {
        model.addAttribute("genres", genreDao.getGenres());
        model.addAttribute("authors", authorDao.findAuthors());
        model.addAttribute("newBook", new SaveBookDto());
        model.addAttribute("newGenre", new SaveGenreDto());
        return "genres";
    }

    @PostMapping(value = {"/saveGenre"})
    public RedirectView saveGenre(@ModelAttribute SaveGenreDto saveGenreDto) {
        genreDao.saveGenre(saveGenreDto);
        return new RedirectView("/genre");
    }

    @GetMapping(value = {"/deleteGenre"})
    public RedirectView deleteGenre(@RequestParam Long genreId) {
        genreDao.deleteGenre(genreId);
        return new RedirectView("/genre");
    }
}