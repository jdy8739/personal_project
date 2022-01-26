package com.example.projectbackend.service.artistAuth;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import com.example.projectbackend.entity.artistAuth.RequestReply;

import java.io.IOException;
import java.util.List;

public interface ConcertRequestService {

    public void regRequest(ConcertRequest concertRequest);

    public List<ConcertRequest> getMyRequestList(Long memberNo);

    public ConcertRequest readRequest(Long concertRequestNo);

    public void deleteRequest(Long concertRequestNo);

    public void modifyRequest(ConcertRequest concertRequest);

    public void deletePicFile(String preFolderName);

    public List<ConcertRequest> getAllRequest();

    public void approveConcertRequest(Long concertRequestNo) throws IOException;
}
