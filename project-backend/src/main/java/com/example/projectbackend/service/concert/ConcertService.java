package com.example.projectbackend.service.concert;

import com.example.projectbackend.entity.concert.Concert;
import com.example.projectbackend.entity.member.BookedConcert;
import com.example.projectbackend.entity.member.LikedConcert;

import java.util.List;
import java.util.Optional;

public interface ConcertService {

    public Optional<Concert> findByConcertNo(Long concertNo);

    public List<Concert> getLikedList(Long memberNo);

    public void makeBooking(BookedConcert bookedConcert);

    public boolean isNotAlreadyBooked(Long memberNo, Long concertNo);

    public List<BookedConcert> getBookedConcertList(Long memberNo);

    public BookedConcert getBookedConcert(Long memberNo);

    public void alterBooking(BookedConcert bookedConcert);

    public void deleteBooking(Integer bookedConcertNo);

    public List<Concert> searchText(String searchedText);

    public List<Concert> searchArtist(String searchedArtist);

    public List<Concert> getList();
}
