package com.example.demo.service.artistAuth;

import com.example.demo.entity.artistAuth.ConcertRequest;
import com.example.demo.entity.artistAuth.RequestReply;

import java.util.List;

public interface ConcertRequestService {

    public void regRequest(ConcertRequest concertRequest);

    public String getUserName(Integer memberNo);

    public List<ConcertRequest> getConcertRequestList(Integer memberNo);

    public ConcertRequest getConcertRequest(Integer concertRequestNo);

    public void approveOrNotRequest(Integer[] numArr);

    public void inputReply(RequestReply requestReply);

    //public String findRequestReply(Integer concertRequestNo);

    public void modifyConcertRequest(ConcertRequest concertRequest);

    public void deleteConcertRequest(Integer concertRequestNo);
}
