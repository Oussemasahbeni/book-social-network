package com.oussema.bsn.book;

import com.oussema.bsn.common.BaseEntity;
import com.oussema.bsn.feedBack.Feedback;
import com.oussema.bsn.history.BookTransactionHistory;
import com.oussema.bsn.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {


    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean sharable;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedBacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;


    @Transient
    public double getRate(){
        if(feedBacks == null || feedBacks.isEmpty()){
            return 0.0;
        }
        var rate= this.feedBacks
                .stream()
                .mapToDouble(Feedback::getRate)
                .average()
                .orElse(0.0);

        return Math.round(rate * 100.0) / 100.0;
    }

}
