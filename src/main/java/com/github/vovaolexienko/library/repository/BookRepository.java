package com.github.vovaolexienko.library.repository;

import com.github.vovaolexienko.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByNameContainingIgnoreCaseOrAuthorsFioContainingIgnoreCase(String name, String fio, Pageable pageable);

    Page<Book> findByGenresId(Long genreId, Pageable pageable);
}
