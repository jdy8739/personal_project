package com.example.projectbackend.repository.board;

import com.example.projectbackend.entity.board.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from BoardReply br where br.boardNo = :boardNo")
    void delete(Long boardNo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from BoardReply br where br.boardReplyNo = :replyNo")
    void deleteReply(Long replyNo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update BoardReply br set br.content = :content where br.boardReplyNo = :boardReplyNo")
    void modifyReply(String content, Long boardReplyNo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update BoardReply br set br.id = '탈퇴한 회원' where br.id = :id")
    void makeIdAsWithdrawn(String id);
}
