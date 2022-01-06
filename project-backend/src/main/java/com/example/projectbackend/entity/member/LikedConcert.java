package com.example.projectbackend.entity.member;

import com.example.projectbackend.entity.concert.Concert;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="liked_concert")
public class LikedConcert { //extends Concert해도 될것같음.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_concert_no")
    private Long likedConcertNo;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(length = 20, nullable = false)
    private Long concertNo;
}
