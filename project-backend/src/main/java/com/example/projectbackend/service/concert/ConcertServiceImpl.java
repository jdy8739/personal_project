package com.example.projectbackend.service.concert;

import com.example.projectbackend.entity.concert.Concert;
import com.example.projectbackend.entity.member.BookedConcert;
import com.example.projectbackend.entity.member.LikedConcert;
import com.example.projectbackend.entity.member.Member;
import com.example.projectbackend.repository.concert.ConcertRepository;
import com.example.projectbackend.repository.member.BookedConcertRepository;
import com.example.projectbackend.repository.member.LikedConcertRepository;
import com.example.projectbackend.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Optional<Concert> findByConcertNo(Long concertNo) {
        return concertRepository.findByConcertNo(concertNo);
    }

    @Override
    public List<Concert> getLikedList(Long memberNo) {
        List<LikedConcert> userLikedList = likedConcertRepository.findByMemberNo(memberNo);
        List<Concert> likedConcertList = new ArrayList<Concert>();
        Concert concert;

        for(int i=0; i<userLikedList.size(); i++) {
            concert = concertRepository.findByConcertNo(userLikedList.get(i).getConcertNo()).get();
            likedConcertList.add(concert);
        }
        return likedConcertList;
    }

    @Override
    public void makeBooking(BookedConcert bookedConcert) {
        Integer numOfVisitors = bookedConcert.getNumOfVisitors();
        concertRepository.minusVenueCapacity(numOfVisitors, bookedConcert.getConcertNo().longValue());

        if(bookedConcert.getName() == null || bookedConcert.getPhoneNumber() == null) {
            Optional<Member> maybeMember = memberRepository.findByMemberNo(bookedConcert.getMemberNo());

            bookedConcert.setName(maybeMember.get().getName());
            bookedConcert.setPhoneNumber(maybeMember.get().getPhoneNo());
        }
        bookedConcertRepository.save(bookedConcert);
    }

    @Override
    public boolean isNotAlreadyBooked(Long memberNo, Long concertNo) {
        return bookedConcertRepository.findByMemberNoAndConcertNo(memberNo, concertNo).isPresent();
    }

    @Override
    public List<BookedConcert> getBookedConcertList(Long memberNo) {
        List<BookedConcert> bookedConcertList = bookedConcertRepository.findByMemberNo(memberNo);
        return bookedConcertList;
    }

    @Override
    public BookedConcert getBookedConcert(Long bookedConcertNo) {
        return bookedConcertRepository.findByBookedConcertNo(bookedConcertNo).get();
    }

    @Override
    public void alterBooking(BookedConcert bookedConcert) {

        Long bookedConcertNo = bookedConcert.getBookedConcertNo();
        Optional<BookedConcert> tmpBookedConcert = bookedConcertRepository.findByBookedConcertNo(bookedConcertNo.longValue());

        Integer numOfVisitorsForPlus = tmpBookedConcert.get().getNumOfVisitors();
        Long concertNo = tmpBookedConcert.get().getConcertNo();
        concertRepository.plusVenueCapacity(numOfVisitorsForPlus, concertNo);

        concertRepository.minusVenueCapacity(bookedConcert.getNumOfVisitors(), concertNo);
        bookedConcertRepository.save(bookedConcert);
    }

    @Override
    public void deleteBooking(Integer bookedConcertNo) {

        Optional<BookedConcert> tmpBookedConcert = bookedConcertRepository.findByBookedConcertNo(bookedConcertNo.longValue());
        Integer numOfVisitors = tmpBookedConcert.get().getNumOfVisitors();
        Long concertNo = tmpBookedConcert.get().getConcertNo();
        concertRepository.plusVenueCapacity(numOfVisitors, concertNo);
        bookedConcertRepository.deleteByBookedConcertNo(bookedConcertNo.longValue());
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
        return concertRepository.findByConcertArtist(searchedArtist);
    }

    @Override
    public List<Concert> getList() {
        return concertRepository.findAll();
    }

    @Override
    public List<Concert> getUnlockedList() {
        return concertRepository.findUnlocked();
    }
}
