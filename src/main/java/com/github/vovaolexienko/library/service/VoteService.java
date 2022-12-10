package com.github.vovaolexienko.library.service;

import com.github.vovaolexienko.library.entity.Book;
import com.github.vovaolexienko.library.entity.Rating;
import com.github.vovaolexienko.library.entity.Vote;
import com.github.vovaolexienko.library.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final BookService bookService;
    private final UserService userService;

    public void saveVote(String username, Long bookId, Integer newVoteValue) {
        Vote vote = voteRepository.findByBookIdAndUserUsername(bookId, username)
                .map(v -> updateVote(v, newVoteValue))
                .orElseGet(() -> createVote(bookId, newVoteValue, username));
        voteRepository.save(vote);
    }

    private Vote createVote(Long bookId, Integer newVoteValue, String username) {
        Book book = bookService.findBook(bookId).orElseThrow();
        Rating rating = book.getRating();
        rating.setTotalRating(rating.getTotalRating() + newVoteValue);
        rating.setTotalVotesNumber(rating.getTotalVotesNumber() + 1);
        rating.calculateAverageRating();
        return new Vote(-1L, newVoteValue, book, userService.findByUsername(username).orElseThrow());
    }

    private Vote updateVote(Vote vote, Integer newVoteValue) {
        Rating rating = vote.getBook().getRating();
        rating.setTotalRating(rating.getTotalRating() - vote.getValue() + newVoteValue);
        rating.calculateAverageRating();
        vote.setValue(newVoteValue);
        return vote;
    }
}
