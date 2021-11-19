package com.example.demo.service.concert;

import com.example.demo.entity.concert.Concert;
import com.example.demo.entity.member.BookedConcert;
import com.example.demo.entity.member.LikedConcert;

import java.util.List;
import java.util.Optional;

public interface ConcertService {

    public Optional<Concert> findByConcertNo(Long concertNo);

    public List<LikedConcert> getLikedList(Long memberNo);

    public void makeBooking(BookedConcert bookedConcert);

    public boolean isNotAlreadyBooked(Long memberNo, Long concertNo);

    public List<BookedConcert> getBookedConcertList(Long memberNo);

    public BookedConcert getBookedConcert(Long memberNo);

    public void alterBooking(BookedConcert bookedConcert);

    public void deleteBooking(Integer bookedConcertNo);

    public List<Concert> searchText(String searchedText);

    public List<Concert> searchArtist(String searchedArtist);
}
