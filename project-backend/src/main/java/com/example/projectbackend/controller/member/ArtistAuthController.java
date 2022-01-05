package com.example.projectbackend.controller.member;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import com.example.projectbackend.entity.artistAuth.RequestReply;
import com.example.projectbackend.service.artistAuth.ConcertRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member/concertRegister")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ArtistAuthController {

    @Autowired
    ConcertRequestService concertRequestService;

    @PostMapping("/sendConcertPic")
    @ResponseBody
    public String requestUploadFile(@RequestParam("concertPic") List<MultipartFile> fileList,
                                    @RequestParam("regName") String regName,
                                    @RequestParam("concertName") String concertName) {

        log.info("requestUploadFile(): " + fileList + ", " + regName + ", " + concertName);

        int i = 1;
        try {
            // 결국 저장되는 위치가 images/사진파일명.확장자
            // images/아이디/사진파일명.확장자
            for (MultipartFile multipartFile : fileList) {
                //File file = new File("c:/java_work/test.jpg");
                log.info("requestUploadFile(): Make File");

                FileOutputStream writer = new FileOutputStream("./images/concertRequestPic/" + regName +  "_" + concertName + i + ".jpg");
                i++;
                writer.write(multipartFile.getBytes());
                writer.close();
            }
            /*
            FileOutputStream writer = new FileOutputStream("./images/" + fileList.getOriginalFilename());
            writer.write(fileList.getBytes());
            writer.close();
             */
        } catch (Exception e) {
            return "Upload Fail!!!";
        }
        log.info("requestUploadFile(): Success!!!");
        return "Upload Success!!!";
    }

    @PostMapping("/request")
    public ResponseEntity<Void> request(@Validated @RequestBody ConcertRequest concertRequest) throws Exception {
        log.info("request(): concertRequest - " + concertRequest);

        concertRequestService.regRequest(concertRequest);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/getName/{memberNo}")
    public ResponseEntity<String> getName(@PathVariable ("memberNo") Integer memberNo) throws Exception {
        //log.info("getName(): " + memberNo);

        String userName = concertRequestService.getUserName(memberNo);

        return new ResponseEntity<String>(userName, HttpStatus.OK);
    }

    @GetMapping("/getConcertRequestList")
    public ResponseEntity<List<ConcertRequest>> getConcertRequestList() throws Exception {

        List<ConcertRequest> allRequestList = concertRequestService.getConcertRequestList(0);

        return new ResponseEntity<List<ConcertRequest>>(allRequestList, HttpStatus.OK);
    }

    @GetMapping("/getConcertRequest/{concertRequestNo}")
    public ResponseEntity<ConcertRequest> getConcertRequest(@PathVariable ("concertRequestNo") Integer concertRequestNo) throws Exception {
        log.info("getConcertRequest(): " + concertRequestNo);

        return new ResponseEntity<ConcertRequest>(concertRequestService.getConcertRequest(concertRequestNo), HttpStatus.OK);
    }

    @GetMapping("/getMyRequestList/{memberNo}")
    public ResponseEntity<List<ConcertRequest>> getMyRequestList(@PathVariable ("memberNo") Integer memberNo) throws Exception {
        //log.info("getMyRequestList(): " + memberNo);

        List<ConcertRequest> myRequestList = concertRequestService.getConcertRequestList(memberNo);

        return new ResponseEntity<List<ConcertRequest>>(myRequestList, HttpStatus.OK);
    }

    @PostMapping("/approveOrNotRequest") //왜 PutMapping만 되는거지? Post도 된다.
    public ResponseEntity<Void> approveOrNotRequest(@RequestParam ("numArr") Integer[] numArr) throws Exception {
        log.info("approveOrNotRequest: " + numArr);

        concertRequestService.approveOrNotRequest(numArr);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/leaveReply")
    public ResponseEntity<Void> leaveReply(@Validated @RequestBody RequestReply requestReply) throws Exception {
        log.info("leaveReply(): " + requestReply);

        concertRequestService.inputReply(requestReply);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/requestModify")
    public ResponseEntity<Void> requestModify(@Validated @RequestBody ConcertRequest concertRequest) throws Exception {
        log.info("requestModify(): " + concertRequest);

        concertRequestService.modifyConcertRequest(concertRequest);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @GetMapping("/getRequestReply/{concertRequestNo}")
//    public ResponseEntity<String> getRequestReply(@PathVariable ("concertRequestNo") Integer concertRequestNo) throws Exception {
//        //log.info("getRequestReply: " + concertRequestNo);
//
//        String requestReply = concertRequestService.findRequestReply(concertRequestNo);
//
//        //log.info("requestReply: " + requestReply);
//
//        return new ResponseEntity<String>(requestReply, HttpStatus.OK);
//    }

    @DeleteMapping("/requestDelete/{concertRequestNo}")
    public ResponseEntity<Void> requestDelete(@PathVariable ("concertRequestNo") Integer concertRequestNo) throws Exception {

        //log.info("requestDelete(): " + concertRequestNo);

        concertRequestService.deleteConcertRequest(concertRequestNo);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
