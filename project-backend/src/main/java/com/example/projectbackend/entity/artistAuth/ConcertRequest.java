package com.example.demo.entity.artistAuth;

import com.example.demo.entity.member.MemberIdentity;
import com.example.demo.entity.member.MemberTaste;
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

    @Column(name = "member_no")
    private Long memberNo;

    @Column(length = 20, nullable = false)
    private String regName;

    @Column(length = 30, nullable = false)
    private String artistName;

    @Column(length = 30, nullable = false)
    private String venueName;

    @Column(length = 30, nullable = false)
    private String concertName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(length = 25, nullable = false)
    private Date dateOfConcert;

    @Column(length = 30, nullable = false)
    private String timeOfConcert;

    @Column(length = 3, nullable = true)
    private String approvedOrNot;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "concert_request_no")
    private List<RequestReply> requestReplies = new ArrayList<RequestReply>();

    public void addRequestReply(RequestReply requestReply) {
        requestReplies.add(requestReply);
    }
}
