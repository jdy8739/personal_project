package com.example.projectbackend.controller.member;

import com.example.projectbackend.entity.member.MemberTaste;
import com.example.projectbackend.service.member.TasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/member/taste")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class TasteController {

    @Autowired
    TasteService tasteService;

    @PostMapping("/postTaste")
    public ResponseEntity<Void> postTaste(@Validated @RequestBody MemberTaste memberTaste) throws Exception {
        log.info("postTaste(): " + memberTaste);

        boolean isNotDecidedYet = tasteService.isDecidedOrNot(memberTaste.getMemberNo().intValue());

        if(isNotDecidedYet) {
            tasteService.addTaste(memberTaste);

        } else if(!isNotDecidedYet) {
            tasteService.updateTaste(memberTaste);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/alreadyDecidePref/{memberNo}")
    public ResponseEntity<Boolean> getDecidedOrNot(@PathVariable ("memberNo") Integer memberNo) throws Exception {
        log.info("getDecidedOrNot(): " + memberNo);

        boolean isNotDecidedYet = tasteService.isDecidedOrNot(memberNo);

        return new ResponseEntity<Boolean>(isNotDecidedYet, HttpStatus.OK);
    }

    @GetMapping("/getTaste/{memberNo}")
    public ResponseEntity<MemberTaste> getTaste(@PathVariable ("memberNo") Integer memberNo) throws Exception {
        log.info("getTaste(): " + memberNo);

        try {
            MemberTaste memberTaste = tasteService.getTaste(memberNo).get();

            return new ResponseEntity<MemberTaste>(memberTaste, HttpStatus.OK);

        } catch (NoSuchElementException noSuchElementException) {

            return null;
        }
    }
}
