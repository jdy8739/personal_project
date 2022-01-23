package com.example.projectbackend.entity.concert;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="concert")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_no")
    private Long concertNo;

    @Column(length = 20, nullable = false)
    private String concertName;

    @Column(length = 20, nullable = false)
    private String concertArtist;

    @Column(length = 30, nullable = false)
    private String concertVenue;

    @Column(length = 30, nullable = false)
    private String concertGenre;

    @Column(length = 10, nullable = false)
    private Integer venueCapacity;

    @Column(length = 25, nullable = false)
    private String concertPrice;

    @Column(length = 20, nullable = false)
    private String concertDate;

    @Column(length = 20, nullable = false)
    private String concertTime;

    @Column(nullable = false)
    private String concertInfo;

    @Column(nullable = true)
    private String folderName;

    @Column(columnDefinition = "integer default 0")
    private Integer numberOfLikes;

    @CreationTimestamp
    private Date regDate;
}
