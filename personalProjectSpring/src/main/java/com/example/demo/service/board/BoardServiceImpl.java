package com.example.demo.service.board;

import com.example.demo.entity.board.Board;
import com.example.demo.entity.board.BoardReply;
import com.example.demo.repository.board.BoardReplyRepository;
import com.example.demo.repository.board.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardReplyRepository boardReplyRepository;

    @Override
    public void upload(Board board) throws Exception {
        boardRepository.save(board);
    }

    @Override
    public List<Board> list() throws Exception {
        return boardRepository.getList();
    }

    @Override
    public Board read(Integer boardNo) throws Exception {
        return boardRepository.getRead(new Long(boardNo));
    }

    @Override
    public void delete(Integer boardNo) throws Exception {
        boardReplyRepository.delete(new Long(boardNo));
        boardRepository.delete(new Long(boardNo));
    }

    @Override
    public void modify(Board board) throws Exception {

        String title = board.getTitle();
        String content = board.getContent();
        String category = board.getCategory();
        Long boardNo = new Long(board.getBoardNo());

        boardRepository.modify(title, content, category, boardNo);
    }

    @Override
    public void addReply(BoardReply boardReply) throws Exception {

        boardReplyRepository.save(boardReply);
    }

    @Override
    public List<BoardReply> replyList(Integer boardNo) throws Exception {

        return boardReplyRepository.getReplyList(new Long(boardNo));
    }

    @Override
    public void deleteReply(Integer replyNo) throws Exception {
        boardReplyRepository.deleteReply(new Long(replyNo));
    }

    @Override
    public void modifyReply(BoardReply boardReply) throws Exception {

        String content = boardReply.getContent();
        Long boardReplyNo = new Long(boardReply.getBoardReplyNo());

        boardReplyRepository.modifyReply(content, boardReplyNo);
    }
}

