package com.github.vovaolexienko.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(indexes = {@Index(name = "book_rating", columnList = "avg_rating,views_number")})
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "image", nullable = false, length = 1000)
    private String image;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Embedded
    private Rating rating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="book")
    private List<Vote> votes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
}