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
@RequestMapping("/member/concert_register")
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
    public ResponseEntity<Void> request(@Validated @RequestBody ConcertRequest concertRequest) {
        log.info("request(): concertRequest - " + concertRequest);

        concertRequestService.regRequest(concertRequest);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/get_my_request/{memberNo}")
    public ResponseEntity<List<ConcertRequest>> getMyRequests(@PathVariable("memberNo") Long memberNo) {
        log.info("getMyRequest(): " + memberNo);

        return new ResponseEntity<List<ConcertRequest>>(concertRequestService.getMyRequestList(memberNo), HttpStatus.OK);
    }

    @GetMapping("/get_concert_request/{concertRequestNo}")
    public ResponseEntity<ConcertRequest> readRequest(@PathVariable("concertRequestNo") Long concertRequestNo) {
        log.info("readRequest(): " + concertRequestNo);

        return new ResponseEntity<ConcertRequest>(concertRequestService.readRequest(concertRequestNo) ,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{concertRequestNo}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("concertRequestNo") Long concertRequestNo) {
        log.info("deleteRequest(): " + concertRequestNo);
        concertRequestService.deleteRequest(concertRequestNo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
