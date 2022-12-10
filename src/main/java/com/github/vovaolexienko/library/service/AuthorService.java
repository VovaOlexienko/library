package com.github.vovaolexienko.library.service;

import com.github.vovaolexienko.library.dto.SaveAuthorDto;
import com.github.vovaolexienko.library.entity.Author;
import com.github.vovaolexienko.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> findAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthors(List<Long> authorIds) {
        return authorRepository.findAllById(authorIds);
    }

    @Transactional
    public void saveAuthor(SaveAuthorDto saveAuthorDto) {
        Author author = new Author();
        author.setId(saveAuthorDto.getId());
        author.setFio(saveAuthorDto.getFio());
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }
}
