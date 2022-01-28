package com.example.projectbackend.controller.member;

import com.example.projectbackend.controller.member.request.MemberRequest;
import com.example.projectbackend.controller.session.MemberInfo;
import com.example.projectbackend.entity.member.LikedConcert;
import com.example.projectbackend.entity.member.Member;
import com.example.projectbackend.repository.member.MemberIdentityRepository;
import com.example.projectbackend.repository.member.MemberRepository;
import com.example.projectbackend.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private MemberRepository memberRepository;

    private MemberInfo info;

    private HttpSession session;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> singup(@Validated @RequestBody MemberRequest memberRequest) throws Exception {
        log.info("memberRequest(): " + memberRequest);

        boolean NotIdThatExists = service.register(memberRequest);

        return new ResponseEntity<Boolean>(NotIdThatExists, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberInfo> login(@Validated @RequestBody MemberRequest memberRequest, HttpServletRequest request) throws Exception {

        int loginResult = service.login(memberRequest);
        info = new MemberInfo();

        switch (loginResult) {
            case 0:
                log.info("login Success");

                Member member = memberRepository.findByMemberId(memberRequest.getId()).get();

                info.setMemberNo(member.getMemberNo());
                info.setId(memberRequest.getId());
                info.setIdentity(member.getMemberIdentityList().get(0).getIdentity());
                log.info("Session Info: " + info);

                session = request.getSession();
                session.setAttribute(memberRequest.getId(), info);

                // * memberNo, id, identity 3가지만 login에 대한 응답으로 보내줌 *
                return new ResponseEntity<MemberInfo>(info, HttpStatus.OK);
            case 1:
                info.setId("no such id");
                return new ResponseEntity<MemberInfo>(info, HttpStatus.OK);
            case 2:
                info.setId("wrong pw input");
                return new ResponseEntity<MemberInfo>(info, HttpStatus.OK);
        }
        return null;
    }

    @PostMapping("/needSession/{memberId}")
    public ResponseEntity<Boolean> needSession(@PathVariable("memberId") String memberId, HttpServletRequest request) throws Exception {

        Boolean isLoggedIn = false;

        try {
            Object obj = session.getAttribute(memberId);
            // log.info("obj: " + obj);

            if(obj != null) {
                isLoggedIn = service.checkSessionValidation(memberId);

                log.info("Session Validation: " + isLoggedIn);
                return new ResponseEntity<Boolean>(isLoggedIn, HttpStatus.OK);
            } else {
                log.info("Session Validation: " + isLoggedIn);
                return new ResponseEntity<Boolean>(isLoggedIn, HttpStatus.OK);
            }
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<Boolean>(isLoggedIn, HttpStatus.OK);

        } catch (IllegalStateException illegalStateException) {
            return new ResponseEntity<Boolean>(isLoggedIn, HttpStatus.OK);

        } finally {
            ;
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Member>> list() throws Exception {
        log.info("getList(): " + service.getList());

        List<Member> list = service.getList();

        return new ResponseEntity<List<Member>>(list, HttpStatus.OK);
    }

    @GetMapping("/read/{memberNo}")
    public ResponseEntity<Member> read(@PathVariable("memberNo") Integer memberNo) throws Exception {
        log.info("getRead(): " + service.read(memberNo.longValue()));
        return new ResponseEntity<Member>(service.read(memberNo.longValue()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{memberNo}")
    public ResponseEntity<Void> delete(@PathVariable("memberNo") Integer memberNo) throws Exception {
        log.info("delete(): memberNo " + memberNo);
        service.delete(memberNo.longValue());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<MemberInfo> modify(@Validated @RequestBody MemberRequest memberRequest) throws Exception {
        log.info("modify(): " + memberRequest);
        MemberInfo memberInfo = service.modify(memberRequest);
        return new ResponseEntity<MemberInfo>(memberInfo, HttpStatus.OK);
    }

    @PostMapping("/addLiked")
    public ResponseEntity<String> addLiked(@Validated @RequestBody LikedConcert likedConcert) throws Exception {
        log.info("addLiked(): concertRequest - " + likedConcert);
        String res = service.addLiked(likedConcert);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PostMapping("/deleteLiked")
    public ResponseEntity<Void> deleteLiked(@RequestParam("memberNo") Integer memberNo,
                                            @RequestParam("concertNo") Integer concertNo) throws Exception {

        log.info("deleteLiked(): concertNo - " + concertNo + ", memberNo - " + memberNo);
        int[] intArrForDelete = { concertNo, memberNo };
        service.deleteLiked(intArrForDelete);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/fetchLikedOrNot") //ConcertDetailPage로 들어갈 때 이 공연이 이미 찜된건지 아닌건지를 판별해서 true또는 false를 반환, 그리고 화면에서 찜하기버튼이 나올지 취소버튼이 나올지를 결정
    public ResponseEntity<Boolean> fetchLikedOrNot(@RequestParam("likedOrNotNumArr") Integer[] likedOrNotNumArr) throws Exception {

        log.info("likedOrNotNumArr: " + likedOrNotNumArr[0] + ", " + likedOrNotNumArr[1]);

        boolean isAlreadyLiked = false;

        try {
            Long memberNo = new Long(likedOrNotNumArr[0]);
            Long concertNo = new Long(likedOrNotNumArr[1]);

            isAlreadyLiked = service.isNotAlreadyLiked(memberNo, concertNo);

        } catch (NullPointerException nullPointerException) {

            log.info("Not LoggedIn");
            return new ResponseEntity<Boolean>(!isAlreadyLiked, HttpStatus.OK);

        } finally {
            ;
        }

        return new ResponseEntity<Boolean>(isAlreadyLiked, HttpStatus.OK);
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<Boolean> checkPassword(@Validated @RequestBody MemberRequest memberRequest) throws Exception {
        log.info("checkPassword: " + memberRequest);
        boolean isRightPassword = false;
        isRightPassword = service.checkPassword(memberRequest);
        return new ResponseEntity<Boolean>(isRightPassword, HttpStatus.OK);
    }

    @PostMapping("/sendMemberPic")
    @ResponseBody
    public String requestUploadFile(@RequestParam("memberPic") List<MultipartFile> fileList,
                                    @RequestParam("memberNo") Integer memberNo,
                                    @RequestParam("id") String id) {

        log.info("requestUploadFile(): " + fileList);

        try {
            // 결국 저장되는 위치가 images/사진파일명.확장자
            // images/아이디/사진파일명.확장자
            for (MultipartFile multipartFile : fileList) {
                //File file = new File("c:/java_work/test.jpg");
                log.info("requestUploadFile(): Make File");

                FileOutputStream writer = new FileOutputStream("./images/memberPic/" + memberNo +  "_" + id + ".jpg");
                writer.write(multipartFile.getBytes());
                writer.close();
            }

        } catch (Exception e) {
            return "Upload Fail!!!";
        }
        log.info("requestUploadFile(): Success!!!");
        return "Upload Success!!!";
    }
}
