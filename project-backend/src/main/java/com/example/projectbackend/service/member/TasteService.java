package com.example.demo.service.member;

import com.example.demo.entity.member.MemberTaste;

import java.util.Optional;

public interface TasteService {

    public void addTaste(MemberTaste memberTaste) throws Exception;

    public void updateTaste(MemberTaste memberTaste) throws Exception;

    public boolean isDecidedOrNot(Integer memberNo) throws Exception;

    public Optional<MemberTaste> getTaste(Integer memberNo) throws Exception;
}
