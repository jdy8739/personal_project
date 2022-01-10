package com.example.projectbackend.service.board;

import com.example.projectbackend.entity.board.Board;
import com.example.projectbackend.entity.board.BoardReply;
import com.example.projectbackend.repository.board.BoardReplyRepository;
import com.example.projectbackend.repository.board.BoardRepository;
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
        return boardRepository.findAll();
    }

    @Override
    public Board read(Integer boardNo) throws Exception {
        return boardRepository.findByBoardNo(boardNo.longValue());
    }

    @Override
    public void delete(Integer boardNo) throws Exception {
        boardReplyRepository.delete(boardNo.longValue());
        boardRepository.delete(boardNo.longValue());
    }

    @Override
    public void modify(Board board) throws Exception {

        String title = board.getTitle();
        String content = board.getContent();
        String category = board.getCategory();
        Long boardNo = board.getBoardNo().longValue();

        boardRepository.modify(title, content, category, boardNo);
    }

    @Override
    public void addReply(BoardReply boardReply) throws Exception {

        boardReplyRepository.save(boardReply);
    }

    @Override
    public void deleteReply(Integer replyNo) throws Exception {
        boardReplyRepository.deleteReply(new Long(replyNo));
    }

    @Override
    public void modifyReply(BoardReply boardReply) throws Exception {

        String content = boardReply.getContent();
        Long boardReplyNo = boardReply.getBoardReplyNo().longValue();

        boardReplyRepository.modifyReply(content, boardReplyNo);
    }
}

