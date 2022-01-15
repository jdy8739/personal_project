package com.example.projectbackend.entity.artistAuth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="concert_request")
public class ConcertRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_request_no")
    private Long concertRequestNo;

    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "approved_or_not")
    private boolean approvedOrNot;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDate;
}
