package com.example.projectbackend.service.concert;

import com.example.projectbackend.entity.concert.Concert;
import com.example.projectbackend.entity.member.BookedConcert;
import com.example.projectbackend.entity.member.LikedConcert;
import com.example.projectbackend.repository.concert.ConcertRepository;
import com.example.projectbackend.repository.member.BookedConcertRepository;
import com.example.projectbackend.repository.member.LikedConcertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private LikedConcertRepository likedConcertRepository;

    @Autowired
    private BookedConcertRepository bookedConcertRepository;

    @Override
    public Optional<Concert> findByConcertNo(Long concertNo) {
        return concertRepository.findByConcertNo(concertNo);
    }

    @Override
    public List<LikedConcert> getLikedList(Long memberNo) {
        return likedConcertRepository.findByMemberNo(memberNo);
    }

    @Override
    public void makeBooking(BookedConcert bookedConcert) {

        Integer numOfVisitors = bookedConcert.getNumOfVisitors();
        concertRepository.minusVenueCapacity(numOfVisitors, new Long(bookedConcert.getConcertNo()));

        bookedConcertRepository.save(bookedConcert);
    }

    @Override
    public boolean isNotAlreadyBooked(Long memberNo, Long concertNo) {
        return bookedConcertRepository.findByMemberNoAndConcertNo(memberNo, concertNo).isEmpty();
    }

    @Override
    public List<BookedConcert> getBookedConcertList(Long memberNo) {

        List<BookedConcert> bookedConcertList = bookedConcertRepository.findByMemberNo(memberNo);

        return bookedConcertList;
    }

    @Override
    public BookedConcert getBookedConcert(Long bookedConcertNo) {

        Optional<BookedConcert> bookedConcert = bookedConcertRepository.findByBookedConcertNo(bookedConcertNo);

        return bookedConcert.get();
    }

    @Override
    public void alterBooking(BookedConcert bookedConcert) {

        //log.info("bookedConcert: " + bookedConcert);
        Long bookedConcertNo = bookedConcert.getBookedConcertNo();
        Optional<BookedConcert> tmpBookedConcert = bookedConcertRepository.findByBookedConcertNo(new Long(bookedConcertNo));

        Integer numOfVisitorsForPlus = tmpBookedConcert.get().getNumOfVisitors();
        Long concertNo = tmpBookedConcert.get().getConcertNo();
        concertRepository.plusVenueCapacity(numOfVisitorsForPlus, concertNo);

        String name = bookedConcert.getName();
        String phoneNumber = bookedConcert.getPhoneNumber();
        Integer numOfVisitors = bookedConcert.getNumOfVisitors();
        String message = bookedConcert.getMessage();

        concertRepository.minusVenueCapacity(numOfVisitors, concertNo);

        bookedConcertRepository.alterBooking(name, phoneNumber, numOfVisitors, message, bookedConcert.getBookedConcertNo());
    }

    @Override
    public void deleteBooking(Integer bookedConcertNo) {

        Optional<BookedConcert> tmpBookedConcert = bookedConcertRepository.findByBookedConcertNo(new Long(bookedConcertNo));
        Integer numOfVisitors =  tmpBookedConcert.get().getNumOfVisitors();
        Long concertNo = tmpBookedConcert.get().getConcertNo();

        concertRepository.plusVenueCapacity(numOfVisitors, concertNo);

        bookedConcertRepository.deleteByBookedConcertNo(new Long(bookedConcertNo));
    }

    @Override
    public List<Concert> searchText(String searchedText) {

        String genreName = "";

        if(searchedText.toLowerCase().equals("rock") || searchedText.equals("락") || searchedText.equals("록")) {
            genreName = "rock";
        } else if(searchedText.toLowerCase().equals("hiphop") || searchedText.equals("힙합")) {
            genreName = "hiphop";
        } else if(searchedText.toLowerCase().equals("jazz") || searchedText.equals("재즈")) {
            genreName = "jazz";
        } else if(searchedText.toLowerCase().equals("edm") || searchedText.equals("이디엠")) {
            genreName = "edm";
        } else if(searchedText.toLowerCase().equals("acoustic") || searchedText.equals("어쿠스틱")) {
            genreName = "acoustic";
        }

        List<Concert> searchedList = null;

        if(genreName != "") {
            searchedList = concertRepository.findByGenreName(genreName); //장르로 찾기
        } else {
            log.info("no such genreName");
        }

        return searchedList;
    }

    @Override
    public List<Concert> searchArtist(String searchedArtist) {
        List<Concert> searchedList = concertRepository.findByConcertArtist(searchedArtist);

        return searchedList;
    }

    @Override
    public List<Concert> getList() {
        return concertRepository.findAll();
    }
}
