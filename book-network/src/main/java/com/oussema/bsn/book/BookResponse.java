package com.oussema.bsn.book;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner;
    private double rate;
    private byte[] cover;
    private boolean archived;
    private boolean sharable;

}
