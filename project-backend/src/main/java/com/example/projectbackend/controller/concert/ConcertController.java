package com.example.projectbackend.controller.concert;

import com.example.projectbackend.entity.concert.Concert;
import com.example.projectbackend.entity.member.BookedConcert;
import com.example.projectbackend.entity.member.LikedConcert;
import com.example.projectbackend.service.concert.ConcertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/concert")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping("/{concertNo}")
    public ResponseEntity<Concert> read(@PathVariable("concertNo") Integer concertNo) throws Exception {
        log.info("concertNo: " + concertNo);

        Optional<Concert> maybeConcert = concertService.findByConcertNo(new Long(concertNo));
        Concert concert = maybeConcert.get();
        log.info("concert: " + concert);

        return new ResponseEntity<Concert>(concert, HttpStatus.OK);
    }
    
    //한 회원의 좋아요한 공연 리스트 뽑아오기
    @GetMapping("/likedList/{memberNo}")
    public ResponseEntity<List<Concert>> getLikedList(@PathVariable("memberNo") Integer memberNo) {

        log.info("memberNo: " + memberNo);

        List<Concert> likedList = concertService.getLikedList(new Long(memberNo));
        return new ResponseEntity<List<Concert>>(likedList, HttpStatus.OK);
    }

    @PostMapping("/makeBooking")
    public ResponseEntity<Void> makeBooking(@Validated @RequestBody BookedConcert BookedConcert) throws Exception {
        log.info("makeBooking: " + BookedConcert);

        concertService.makeBooking(BookedConcert);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/fetchBookedOrNot")
    public ResponseEntity<Boolean> fetchBookedOrNot(@RequestParam("likedOrNotNumArr") Integer[] likedOrNotNumArr) throws Exception {

        Long memberNo = new Long(likedOrNotNumArr[0]);
        Long concertNo = new Long(likedOrNotNumArr[1]);

        boolean isNotAlreadyBooked = concertService.isNotAlreadyBooked(memberNo, concertNo);

        log.info("fetchBookedOrNot(): " + concertNo + ", " + isNotAlreadyBooked);

        return new ResponseEntity<Boolean>(isNotAlreadyBooked, HttpStatus.OK);
    }

    @GetMapping("/fetchBookedList/{memberNo}")
    public ResponseEntity<List<BookedConcert>> fetchBookedList(@PathVariable("memberNo") Integer memberNo) throws Exception {
        log.info("fetchBookedList(): " + memberNo);

        return new ResponseEntity<List<BookedConcert>>(concertService.getBookedConcertList(new Long(memberNo)), HttpStatus.OK);
    }

    @GetMapping("/fetchBookedConcert/{memberNo}")
    public ResponseEntity<BookedConcert> fetchBookedConcert(@PathVariable("memberNo") Integer memberNo) throws Exception {
        log.info("fetchBookedConcert(): " + memberNo);

        return new ResponseEntity<BookedConcert>(concertService.getBookedConcert(new Long(memberNo)), HttpStatus.OK);
    }

    @PutMapping("/alterBooking")
    public ResponseEntity<Void> alterBooking(@Validated @RequestBody BookedConcert BookedConcert) throws Exception {

        concertService.alterBooking(BookedConcert);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteBooking/{bookedConcertNo}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("bookedConcertNo") Integer bookedConcertNo) throws Exception {
        //log.info("deleteBooking(): " + bookedConcertNo);

        concertService.deleteBooking(bookedConcertNo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/searchText/{searchedText}")
    public ResponseEntity<List<Concert>> searchText(@PathVariable("searchedText") String searchedText) throws Exception {
        //log.info("searchText(): " + searchedText);

        List<Concert> SearchedList = concertService.searchText(searchedText);

        return new ResponseEntity<List<Concert>>(SearchedList, HttpStatus.OK);
    }

    @PostMapping("/searchArtist/{searchedText}")
    public ResponseEntity<List<Concert>> searchArtist(@PathVariable("searchedText") String searchedArtist) throws Exception {
        //log.info("searchText(): " + searchedText);

        List<Concert> SearchedList = concertService.searchArtist(searchedArtist);

        return new ResponseEntity<List<Concert>>(SearchedList, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Concert>> getList() {
        log.info("getList(): ");

        return new ResponseEntity<List<Concert>>(concertService.getList(), HttpStatus.OK);
    }
}
