package com.example.demo.service.member;

import com.example.demo.entity.member.MemberTaste;
import com.example.demo.repository.member.MemberTasteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TasteServiceImple implements TasteService{

    @Autowired
    MemberTasteRepository memberTasteRepository;

    @Override
    public void addTaste(MemberTaste memberTaste) throws Exception {

        String chosenGenres = memberTaste.getChosenGenres().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

        String chosenArtists = memberTaste.getChosenArtists().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

        memberTaste.setChosenGenres(chosenGenres);
        memberTaste.setChosenArtists(chosenArtists);

        //memberTasteRepository.deleteDefault(new Long(tasteRequest.getMemberNo())); //굳이?
        memberTasteRepository.save(memberTaste);
    }

    @Override
    public boolean isDecidedOrNot(Integer memberNo) throws Exception {

        boolean isNotDecidedYet = memberTasteRepository.isTasteThatExists(new Long(memberNo)).isEmpty();

        if(isNotDecidedYet) {
            log.info("isNotDecidedYet(): " + isNotDecidedYet);
            return true;
        }
        log.info("isNotDecidedYet(): " + isNotDecidedYet);
        return false;
    }

    @Override
    public void updateTaste(MemberTaste memberTaste) throws Exception {

//        String chosenGenres = tasteRequest.getChosenGenres().replace("[", "");
//        chosenGenres = chosenGenres.replace("]", "");
//        chosenGenres = chosenGenres.replace("\"", "");

        String chosenGenres = memberTaste.getChosenGenres().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

//        String chosenArtists = tasteRequest.getChosenArtists().replace("[", "");
//        chosenArtists = chosenArtists.replace("]", "");
//        chosenArtists = chosenArtists.replace("\"", "");

        String chosenArtists = memberTaste.getChosenArtists().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

        memberTasteRepository.updateTaste(chosenGenres, memberTaste.getSpeedTaste(), memberTaste.getAtmosphereTaste(), chosenArtists,
                new Long(memberTaste.getMemberNo()));
    }

    @Override
    public Optional<MemberTaste> getTaste(Integer memberNo) throws Exception {

        Optional<MemberTaste> memberTaste = memberTasteRepository.isTasteThatExists(new Long(memberNo));

        return memberTaste;
    }
}
