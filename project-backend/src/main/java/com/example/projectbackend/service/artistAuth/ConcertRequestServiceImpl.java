package com.example.demo.service.artistAuth;

import com.example.demo.entity.artistAuth.ConcertRequest;
import com.example.demo.entity.artistAuth.RequestReply;
import com.example.demo.repository.artistAuth.ConcertRequestRepository;
import com.example.demo.repository.artistAuth.RequestReplyRepository;
import com.example.demo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ConcertRequestServiceImpl implements ConcertRequestService {

    @Autowired
    ConcertRequestRepository concertRequestRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RequestReplyRepository requestReplyRepository;

    @Override
    public void regRequest(ConcertRequest concertRequest) {

//        RequestReply requestReply = new RequestReply("");
//        concertRequest.addRequestReply(requestReply);

        concertRequestRepository.save(concertRequest);
    }

    @Override
    public String getUserName(Integer memberNo) {

        String userName = memberRepository.findByMemberNo(new Long(memberNo)).get().getName();

        return userName;
    }

    @Override
    public List<ConcertRequest> getConcertRequestList(Integer memberNo) {

        List<ConcertRequest> concertRequestList;

        if(memberNo == 0) { //코드 재활용 --> 관리자용 전체 리스트 보기
            concertRequestList = concertRequestRepository.getList();
        } else { // --> 개인이 스스로 요청한 리스트 보기
            concertRequestList = concertRequestRepository.findByMemberNo(new Long(memberNo)); 
        }

        return concertRequestList;
    }

    @Override
    public ConcertRequest getConcertRequest(Integer concertRequestNo) {

        Optional<ConcertRequest> tmpConcertRequest = concertRequestRepository.findByConcertRequestNo(new Long(concertRequestNo));

        ConcertRequest concertRequest = tmpConcertRequest.get();

        try {
            RequestReply requestReply = requestReplyRepository.findByConcertRequestNo(new Long(concertRequestNo)).get();

            concertRequest.addRequestReply(requestReply);

        } catch (NoSuchElementException noSuchElementException) {

            concertRequest.addRequestReply(new RequestReply(" "));
        }

        return concertRequest;
    }

    @Override
    public void approveOrNotRequest(Integer[] numArr) {

        int concertRequestNo = numArr[0];
        int statusNum = numArr[1];

        if(statusNum == 1) {
            concertRequestRepository.approveConcertRequest(new Long(concertRequestNo));
        } else if(statusNum == 2) {
            concertRequestRepository.denyConcertRequest(new Long(concertRequestNo));
        } else if(statusNum == 3) {
            concertRequestRepository.deferConcertRequest(new Long(concertRequestNo));
        }
    }

    @Override
    public void inputReply(RequestReply requestReply) {

        boolean isNotRepliedYet = requestReplyRepository.findByConcertRequestNo(requestReply.getConcertRequestNo()).isEmpty();

        log.info("isNotRepliedYet: " + isNotRepliedYet);

        if(isNotRepliedYet) requestReplyRepository.save(requestReply);
        else {

            Long concertRequestNo = requestReply.getConcertRequestNo();
            String reply = requestReply.getRequestReply();

            requestReplyRepository.updateReply(reply, concertRequestNo);
        }
    }

//    @Override
//    public String findRequestReply(Integer concertRequestNo) {
//
//        Optional<RequestReply> tmpRequestReply = requestReplyRepository.findByConcertRequestNo(new Long(concertRequestNo));
//        String requestReply = tmpRequestReply.get().getRequestReply();
//
//        return requestReply;
//    }

    @Override
    public void modifyConcertRequest(ConcertRequest concertRequest) {

        Long concertRequestNo = new Long(concertRequest.getConcertRequestNo());
        String regName = concertRequest.getRegName();
        String artistName = concertRequest.getRegName();
        String venueName = concertRequest.getVenueName();
        String concertName = concertRequest.getConcertName();
        Date dateOfConcert = concertRequest.getDateOfConcert();
        String timeOfConcert = concertRequest.getTimeOfConcert();

        concertRequestRepository.modifyConcertRequest(regName, artistName, venueName, concertName, dateOfConcert, timeOfConcert, concertRequestNo);
    }

    @Override
    public void deleteConcertRequest(Integer concertRequestNo) {

        requestReplyRepository.deleteByConcertRequestNo(new Long(concertRequestNo));
        concertRequestRepository.deleteByConcertRequestNo(new Long(concertRequestNo));
    }
}
