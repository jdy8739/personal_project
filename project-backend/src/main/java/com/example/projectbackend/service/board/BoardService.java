package com.example.projectbackend.service.board;

import com.example.projectbackend.entity.board.Board;
import com.example.projectbackend.entity.board.BoardReply;

import java.util.List;

public interface BoardService {

    public void upload(Board board) throws Exception;

    public List<Board> list() throws Exception;

    public Board read(Integer boardNo) throws Exception;

    public void delete(Integer boardNo) throws Exception;

    public void modify(Board board) throws Exception;

    public void addReply(BoardReply boardReply) throws Exception;

    public List<BoardReply> replyList(Integer boardNo) throws Exception;

    public void deleteReply(Integer replyNo) throws Exception;

    public void modifyReply(BoardReply boardReply) throws Exception;

}
