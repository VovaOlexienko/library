package com.github.vovaolexienko.library.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Column(name = "total_rating", nullable = false)
    private int totalRating;

    @Column(name = "avg_rating", nullable = false)
    private int avgRating;

    @Column(name = "total_votes_number", nullable = false)
    private int totalVotesNumber;

    @Column(name = "views_number", nullable = false)
    private int viewsNumber;

    public void calculateAverageRating() {
        avgRating = totalRating != 0 && totalVotesNumber != 0 ? totalRating / totalVotesNumber : 0;
    }

    public void upViewsNumber() {
        viewsNumber++;
    }
}