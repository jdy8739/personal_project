package com.example.projectbackend.service.artistAuth;

import com.example.projectbackend.entity.artistAuth.ConcertRequest;
import com.example.projectbackend.entity.artistAuth.RequestReply;
import com.example.projectbackend.entity.concert.Concert;
import com.example.projectbackend.repository.artistAuth.ConcertRequestRepository;
import com.example.projectbackend.repository.artistAuth.RequestReplyRepository;
import com.example.projectbackend.repository.concert.ConcertRepository;
import com.example.projectbackend.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
public class ConcertRequestServiceImpl implements ConcertRequestService {

    @Autowired
    ConcertRequestRepository concertRequestRepository;

    @Autowired
    ConcertRepository concertRepository;

    @Override
    public void regRequest(ConcertRequest concertRequest) {
        concertRequestRepository.save(concertRequest);
    }

    @Override
    public List<ConcertRequest> getMyRequestList(Long memberNo) {
        return concertRequestRepository.findByMemberNo(memberNo);
    }

    @Override
    public ConcertRequest readRequest(Long concertRequestNo) {
        Optional<ConcertRequest> optionalConcertRequest = concertRequestRepository.findByConcertRequestNo(concertRequestNo);
        if(optionalConcertRequest.isEmpty()) return null;
        else return optionalConcertRequest.get();
    }

    @Override
    public void deleteRequest(Long concertRequestNo) {
        String preFolderName = concertRequestRepository.findByConcertRequestNo(concertRequestNo).get().getFolderName();
        deletePicFile(preFolderName, "registered_pics");
        concertRequestRepository.deleteByConcertRequestNo(concertRequestNo);
    }

    @Override
    public void modifyRequest(ConcertRequest concertRequest) {
        String preFolderName = concertRequestRepository.findByConcertRequestNo(concertRequest.getConcertRequestNo()).get().getFolderName();
        deletePicFile(preFolderName, "registered_pics");
        concertRequestRepository.save(concertRequest);
    }

    @Override
    public void deletePicFile(String preFolderName, String targetFolder) {
        if(preFolderName != null) {
            File dir = new File("./images/" + targetFolder + "/" + preFolderName);
            if(dir.exists()) {
                File[] files = dir.listFiles();
                for(File file : files) file.delete();
                dir.delete();
            } else {
                log.info("File does noe exist.");
            }
        }
    }

    @Override
    public List<ConcertRequest> getAllRequest() {
        return concertRequestRepository.findAll();
    }

    @Override
    public void approveConcertRequest(Long concertRequestNo) throws IOException {
        boolean isApproved = concertRequestRepository.isApprovedOrNot(concertRequestNo);
        if(!isApproved) {
            ConcertRequest concertRequest = concertRequestRepository.findByConcertRequestNo(concertRequestNo).get();
            String fileName = concertRequest.getFolderName();

            if(!isFolderAlreadyInConcertPics(fileName)) {
                log.info("make folder in concert_pics.");

                File newFolder = new File("./images/concert_pics/" + fileName);
                newFolder.mkdir();

                File file = new File("./images/registered_pics/" + fileName);
                File newFile = new File("./images/concert_pics/" + fileName);

                copy(file, newFile);
                addOnConcertList(concertRequest);
            } else {
                log.info("do nothing.");
            }
            concertRequestRepository.approveConcertRequest(concertRequestNo);
        } else concertRequestRepository.denyConcertRequest(concertRequestNo);
    }

    public boolean isFolderAlreadyInConcertPics(String folderName) {
        String path = "./images/concert_pics/" + folderName;
        File newFolder = new File(path);
        if(newFolder.exists()) {
            return true;
        } else return false;
    }

    public void copy(File sourceF, File targetF){
        File[] target_file = sourceF.listFiles();
        for (File file : target_file) {
            File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
            if(file.isDirectory()){
                temp.mkdir();
                copy(file, temp);
            } else {
                FileInputStream fis = null;
                FileOutputStream fos = null;
                try {
                    fis = new FileInputStream(file);
                    fos = new FileOutputStream(temp) ;
                    byte[] b = new byte[4096];
                    int cnt = 0;
                    while((cnt=fis.read(b)) != -1){
                        fos.write(b, 0, cnt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    try {
                        fis.close();
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addOnConcertList(ConcertRequest concertRequest) {
        Concert newConcert = new Concert();
        newConcert.setConcertName(concertRequest.getConcertName());
        newConcert.setConcertArtist(concertRequest.getConcertArtist());
        newConcert.setConcertVenue(concertRequest.getConcertVenue());
        newConcert.setConcertGenre(concertRequest.getConcertGenre());
        newConcert.setVenueCapacity(concertRequest.getVenueCapacity());
        newConcert.setConcertPrice(concertRequest.getConcertPrice());
        newConcert.setConcertDate(concertRequest.getConcertDate());
        newConcert.setConcertTime(concertRequest.getConcertTime());
        newConcert.setConcertInfo(concertRequest.getConcertInfo());
        newConcert.setFolderName(concertRequest.getFolderName());
        newConcert.setNumberOfLikes(0);
        newConcert.setLocked(true);
        concertRepository.save(newConcert);
    }
}
