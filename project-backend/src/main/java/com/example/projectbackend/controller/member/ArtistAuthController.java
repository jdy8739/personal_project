package com.example.projectbackend.controller.member;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import com.example.projectbackend.entity.artistAuth.RequestReply;
import com.example.projectbackend.service.artistAuth.ConcertRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member/concert_register")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ArtistAuthController {

    @Autowired
    ConcertRequestService concertRequestService;

    @PostMapping("/img_upload")
    @ResponseBody
    public ResponseEntity<String> requestUploadFile(@RequestParam("concertPic") List<MultipartFile> fileList,
                                    @RequestParam("concertName") String concertName,
                                    @RequestParam("code") String code,
                                    @RequestParam("preFolderName") String preFolderName) {

        concertRequestService.deletePicFile(preFolderName);

        log.info("requestUploadFile(): " + fileList + ", " + concertName + ", " + code);

        String path = "./images/registered_pics/" + concertName + "(" + code + ")";
        File newFolder = new File(path);

        if(!newFolder.exists()) {
            try {
                newFolder.mkdir();
            } catch (Exception e) {
                log.info("A folder making error has occured.");
            }
        }

        int i = 1;
        try {
            // 결국 저장되는 위치가 images/사진파일명.확장자
            // images/아이디/사진파일명.확장자
            for (MultipartFile multipartFile : fileList) {
                log.info("requestUploadFile(): Make File");

                FileOutputStream writer = new FileOutputStream(path + "/" + concertName + "-" + i + ".jpg");
                i++;
                writer.write(multipartFile.getBytes());
                writer.close();
            }

        } catch (FileSizeLimitExceededException e) {
            return new ResponseEntity<String>("image size too big.", HttpStatus.OK);
        } catch (Exception e) {
            log.info("e:" + e);
            return new ResponseEntity<String>("Upload Fail!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Upload Success!", HttpStatus.OK);
    }

    @PostMapping("/request")
    public ResponseEntity<Void> request(@Validated @RequestBody ConcertRequest concertRequest) {
        log.info("request(): " + concertRequest);

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

    @PutMapping("/modify")
    public ResponseEntity<Void> modifyRequest(@Validated @RequestBody ConcertRequest concertRequest) {
        log.info("modifyRequest(): " + concertRequest);
        concertRequestService.modifyRequest(concertRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/get_all_request")
    public ResponseEntity<List<ConcertRequest>> getAllRequest() {
        log.info("getAllRequest(): ");
        return new ResponseEntity<List<ConcertRequest>>(concertRequestService.getAllRequest(), HttpStatus.OK);
    }

    @PutMapping("/approve/{concertRequestNo}")
    public ResponseEntity<Void> approveRequest(@PathVariable("concertRequestNo") Long concertRequestNo) throws IOException {
        log.info("approveRequest(): " + concertRequestNo);
        concertRequestService.approveConcertRequest(concertRequestNo);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
