package com.example.projectbackend.entity.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="member_taste")
public class MemberTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_taste_no")
    private Long memberTasteNo;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(length = 100, nullable = false)
    private String chosenGenres;

    @Column(length = 20, nullable = false)
    private String speedTaste;

    @Column(length = 20, nullable = false)
    private String atmosphereTaste;

    @Column(length = 30, nullable = true)
    private String chosenArtists;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date updDate;
}
