package com.example.projectbackend.service.artistAuth;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import com.example.projectbackend.entity.artistAuth.RequestReply;
import com.example.projectbackend.repository.artistAuth.ConcertRequestRepository;
import com.example.projectbackend.repository.artistAuth.RequestReplyRepository;
import com.example.projectbackend.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
        concertRequestRepository.save(concertRequest);
    }

    @Override
    public List<ConcertRequest> getMyRequestList(Long memberNo) {
        return concertRequestRepository.findByMemberNo(memberNo);
    }

    @Override
    public ConcertRequest readRequest(Long concertRequestNo) {
        Optional<ConcertRequest> optionalConcertRequest = concertRequestRepository.findByConcertRequestNo(concertRequestNo);
        if(optionalConcertRequest.isEmpty()) return null;
        else return optionalConcertRequest.get();
    }

    @Override
    public void deleteRequest(Long concertRequestNo) {


        concertRequestRepository.deleteByConcertRequestNo(concertRequestNo);
    }

    @Override
    public void modifyRequest(ConcertRequest concertRequest) {
        String preFolderName = concertRequestRepository.findByConcertRequestNo(concertRequest.getConcertRequestNo()).get().getFolderName();
        deletePicFile(preFolderName);
        concertRequestRepository.save(concertRequest);
    }

    @Override
    public void deletePicFile(String preFolderName) {
        if(preFolderName != "") {
            File dir = new File("./images/registered_pics/" + preFolderName);
            if(dir.exists()) {
                File[] files = dir.listFiles();
                for(File file : files) file.delete();
                dir.delete();
            } else {
                log.info("File does noe exist.");
            }
        }
    }





//    @Override
//    public String getUserName(Integer memberNo) {
//
//        String userName = memberRepository.findByMemberNo(new Long(memberNo)).get().getName();
//
//        return userName;
//    }
//
//    @Override
//    public List<ConcertRequest> getConcertRequestList(Integer memberNo) {
//
//        List<ConcertRequest> concertRequestList;
//
//        if(memberNo == 0) { //코드 재활용 --> 관리자용 전체 리스트 보기
//            concertRequestList = concertRequestRepository.findAll();
//        } else { // --> 개인이 스스로 요청한 리스트 보기
//            concertRequestList = concertRequestRepository.findByMemberNo(memberNo.longValue());
//        }
//        return concertRequestList;
//    }
//
//    @Override
//    public ConcertRequest getConcertRequest(Integer concertRequestNo) {
//
//        Optional<ConcertRequest> tmpConcertRequest = concertRequestRepository.findByConcertRequestNo(new Long(concertRequestNo));
//
//        ConcertRequest concertRequest = tmpConcertRequest.get();
//
//        try {
//            RequestReply requestReply = requestReplyRepository.findByConcertRequestNo(new Long(concertRequestNo)).get();
//
////            concertRequest.addRequestReply(requestReply);
//
//        } catch (NoSuchElementException noSuchElementException) {
//
////            concertRequest.addRequestReply(new RequestReply(" "));
//        }
//
//        return concertRequest;
//    }
//
//    @Override
//    public void approveOrNotRequest(Integer[] numArr) {
//
//        int concertRequestNo = numArr[0];
//        int statusNum = numArr[1];
//
//        Integer conReqNo = concertRequestNo;
//
//        if(statusNum == 1) {
//            concertRequestRepository.approveConcertRequest(conReqNo.longValue());
//        } else if(statusNum == 2) {
//            concertRequestRepository.denyConcertRequest(conReqNo.longValue());
//        }
//    }
//
//    @Override
//    public void inputReply(RequestReply requestReply) {
//
//        boolean isNotRepliedYet = requestReplyRepository.findByConcertRequestNo(requestReply.getConcertRequestNo()).isEmpty();
//
//        log.info("isNotRepliedYet: " + isNotRepliedYet);
//
//        if(isNotRepliedYet) requestReplyRepository.save(requestReply);
//        else {
//
//            Long concertRequestNo = requestReply.getConcertRequestNo();
//            String reply = requestReply.getRequestReply();
//
//            requestReplyRepository.updateReply(reply, concertRequestNo);
//        }
//    }
//
////    @Override
////    public String findRequestReply(Integer concertRequestNo) {
////
////        Optional<RequestReply> tmpRequestReply = requestReplyRepository.findByConcertRequestNo(new Long(concertRequestNo));
////        String requestReply = tmpRequestReply.get().getRequestReply();
////
////        return requestReply;
////    }
//
//
//
//    @Override
//    public void deleteConcertRequest(Integer concertRequestNo) {
//
//        Long conReqNo = concertRequestNo.longValue();
//
//        requestReplyRepository.deleteByConcertRequestNo(conReqNo);
//        concertRequestRepository.deleteByConcertRequestNo(conReqNo);
//    }
}
