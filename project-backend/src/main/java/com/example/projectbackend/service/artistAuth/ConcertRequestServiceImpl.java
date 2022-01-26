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
        String preFolderName = concertRequestRepository.findByConcertRequestNo(concertRequestNo).get().getFolderName();
        deletePicFile(preFolderName);
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
        if(preFolderName != null) {
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

    @Override
    public List<ConcertRequest> getAllRequest() {
        return concertRequestRepository.findAll();
    }

    @Override
    public void approveConcertRequest(Long concertRequestNo) {
        boolean isApproved = concertRequestRepository.isApprovedOrNot(concertRequestNo);
        if(!isApproved) {
            //승인 거절을 여러번 왓다갓다 눌러서 여러번 생성되는 콘서트가 없도록
            //jpql과 isPresent()로 일치하는거 있는지 확인하고 없으면 생성하는 로직으로 가야할듯.
            concertRequestRepository.approveConcertRequest(concertRequestNo);
        } else concertRequestRepository.denyConcertRequest(concertRequestNo);
    }
}
