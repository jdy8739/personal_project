package com.example.demo.service.member;

import com.example.demo.controller.member.request.MemberRequest;
import com.example.demo.controller.member.response.MemberResponse;
import com.example.demo.entity.member.LikedConcert;
import com.example.demo.entity.member.Member;

import java.util.List;

public interface MemberService {

    public boolean register(MemberRequest memberRequest) throws Exception;

    public Member login(MemberRequest memberRequest) throws Exception;

    public List<Member> getList() throws Exception;

    public Member read(Long memberNo) throws Exception;

    public void delete(Long memberNo) throws Exception;

    public MemberResponse modify(MemberRequest memberRequest) throws Exception;

    public boolean checkSessionValidation(String memberId) throws Exception;

    public void addLiked(LikedConcert likedConcert) throws Exception;

    public boolean isNotAlreadyLiked(Long memberNo, Long concertNo) throws Exception;

    public void deleteLiked(int[] intArrForDelete) throws Exception;

    public boolean checkPassword(MemberRequest memberRequest) throws Exception;
}
