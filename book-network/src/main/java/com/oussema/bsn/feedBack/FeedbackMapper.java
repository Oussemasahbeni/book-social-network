package com.oussema.bsn.feedBack;

import com.oussema.bsn.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class FeedbackMapper {

    public  Feedback toEntity(FeedbackRequest request) {
        return Feedback.builder()
                .rate(request.rate())
                .comment(request.comment())
                .book(
                        Book.builder()
                                .id(request.bookId())
                                .archived(false)
                                .sharable(false)
                                .build()
                )
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .rate(feedback.getRate())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
