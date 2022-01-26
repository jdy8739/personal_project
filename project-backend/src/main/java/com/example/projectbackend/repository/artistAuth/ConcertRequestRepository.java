package com.example.projectbackend.repository.artistAuth;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConcertRequestRepository extends JpaRepository<ConcertRequest, Long> {

    Optional<ConcertRequest> findByConcertRequestNo(Long concertRequestNo);

    List<ConcertRequest> findByMemberNo(Long memberNo);

    @Transactional
    @Modifying
    void deleteByConcertRequestNo(Long concertRequestNo);

    @Query("select cr.approvedOrNot from ConcertRequest cr where cr.concertRequestNo = :concertRequestNo")
    boolean isApprovedOrNot(Long concertRequestNo);

    @Transactional
    @Modifying
    @Query("update ConcertRequest cr set cr.approvedOrNot = true where cr.concertRequestNo = :concertRequestNo")
    void approveConcertRequest(Long concertRequestNo);

    @Transactional
    @Modifying
    @Query("update ConcertRequest cr set cr.approvedOrNot = false where cr.concertRequestNo = :concertRequestNo")
    void denyConcertRequest(Long concertRequestNo);
}
