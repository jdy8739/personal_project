package com.example.projectbackend.controller.board;

import com.example.projectbackend.entity.board.Board;
import com.example.projectbackend.entity.board.BoardReply;
import com.example.projectbackend.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class BoardController {

    @Autowired
    BoardService service;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@Validated @RequestBody Board board) throws Exception {
        log.info("upload(): " + board);

        service.upload(board);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Board>> list() throws Exception {
        log.info("getList(): " + service.list());

        List<Board> list = service.list();
        //List<Board> list = null;

        return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
    }

    @GetMapping("/read/{boardNo}")
    public ResponseEntity<Board> read(@PathVariable("boardNo") Integer boardNo) throws Exception {
        log.info("read() - boardNo: " + boardNo);

        Board board = service.read(boardNo);

        return new ResponseEntity<Board>(board, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{boardNo}")
    public ResponseEntity<Void> delete(@PathVariable("boardNo") Integer boardNo) throws Exception {
        log.info("delete() - boardNo: " + boardNo);

        service.delete(boardNo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<Void> modify(@Validated @RequestBody Board board) throws Exception {
        log.info("modify(): " + board);

        service.modify(board);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/addReply")
    public ResponseEntity<Void> addReply(@Validated @RequestBody BoardReply boardReply) throws Exception {
        log.info("addReply(): " + boardReply);

        service.addReply(boardReply);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @GetMapping("/replyList/{boardNo}")
//    public ResponseEntity<List<BoardReply>> replyList(@PathVariable("boardNo") Integer boardNo) throws Exception {
//        log.info("replyList() - boardNo: " + boardNo);
//
//        List<BoardReply> replyList = service.replyList(boardNo);
//
//        return new ResponseEntity<List<BoardReply>>(replyList, HttpStatus.OK);
//    }

    @DeleteMapping("/deleteReply/{replyNo}")
    public ResponseEntity<Void> replyDelete(@PathVariable("replyNo") Integer replyNo) throws Exception {
        log.info("replyDelete(): " + replyNo);

        service.deleteReply(replyNo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/modifyReply")
    public ResponseEntity<Void> modifyReply(@Validated @RequestBody BoardReply boardReply) throws Exception {
        log.info("modifyReply(): " + boardReply);

        service.modifyReply(boardReply);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Void> TestServlet() throws Exception {

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
