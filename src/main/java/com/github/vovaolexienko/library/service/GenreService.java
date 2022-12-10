package com.github.vovaolexienko.library.service;

import com.github.vovaolexienko.library.dto.SaveGenreDto;
import com.github.vovaolexienko.library.entity.Genre;
import com.github.vovaolexienko.library.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public List<Genre> getGenres(List<Long> genreIds) {
        return genreRepository.findAllById(genreIds);
    }

    public Genre getGenre(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new ValidationException(String.format("Genre with id = [%s] is absent", genreId)));
    }

    @Transactional
    public void saveGenre(SaveGenreDto saveGenreDto) {
        Genre genre = new Genre();
        genre.setId(saveGenreDto.getId());
        genre.setName(saveGenreDto.getName());
        genreRepository.save(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}