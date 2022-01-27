package com.example.projectbackend.repository.concert;

import com.example.projectbackend.entity.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

      Optional<Concert> findByConcertNo(Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.numberOfLikes = co.numberOfLikes + 1 where co.concertNo = :concertNo")
      void plusNumberOfLikes(Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.numberOfLikes = co.numberOfLikes - 1 where co.concertNo = :concertNo")
      void minusNumberOfLikes(Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.venueCapacity = co.venueCapacity - :numOfVisitors where co.concertNo = :concertNo")
      void minusVenueCapacity(Integer numOfVisitors, Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.venueCapacity = co.venueCapacity + :numOfVisitors where co.concertNo = :concertNo")
      void plusVenueCapacity(Integer numOfVisitors, Long concertNo);

      @Query("select co from Concert co where co.concertGenre like :genreName%") //like(포함)
      List<Concert> findByGenreName(String genreName);

      @Query("select co from Concert co where co.concertName like :concertName%") //like(포함)
      List<Concert> findByConcertName(String concertName);

      @Query("select co from Concert co where co.locked = false")
      List<Concert> findUnlocked();

      @Query("select co.locked from Concert co where co.concertNo = :concertNo")
      boolean isLocked(Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.locked = false where co.concertNo = :concertNo")
      void unlockConcert(Long concertNo);

      @Transactional
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("update Concert co set co.locked = true where co.concertNo = :concertNo")
      void lockConcert(Long concertNo);

      @Transactional
      @Modifying
      void deleteByConcertNo(Long concertNo);
}
