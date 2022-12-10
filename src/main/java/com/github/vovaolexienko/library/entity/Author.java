package com.github.vovaolexienko.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "author_fio", columnList = "fio"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fio", nullable = false, length = 100)
    private String fio;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "authors")
    private List<Book> books;
}