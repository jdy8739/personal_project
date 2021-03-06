package com.example.projectbackend.service.member;

import com.example.projectbackend.controller.member.request.MemberRequest;
import com.example.projectbackend.controller.session.MemberInfo;
import com.example.projectbackend.entity.member.*;
import com.example.projectbackend.repository.artistAuth.ConcertRequestRepository;
import com.example.projectbackend.repository.board.BoardReplyRepository;
import com.example.projectbackend.repository.concert.ConcertRepository;
import com.example.projectbackend.repository.board.BoardRepository;
import com.example.projectbackend.repository.member.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberIdentityRepository memberIdentityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LikedConcertRepository likedConcertRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardReplyRepository boardReplyRepository;

    @Autowired
    private MemberTasteRepository memberTasteRepository;

    @Autowired
    private ConcertRequestRepository concertRequestRepository;

    @Autowired
    private BookedConcertRepository bookedConcertRepository;

    @Override
    @Transactional
    public boolean register(MemberRequest memberRequest) throws Exception {

        if(!memberRepository.findByMemberId(memberRequest.getId()).isEmpty()) {
            log.info("ID that Already Exists!");
            return false;

        } else {

            String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());

            String location = memberRequest.getLocation().replaceAll("\\[", "").replaceAll("\\]","").
                    replaceAll("\"", "");

            MemberIdentity memberIdentity = new MemberIdentity(memberRequest.getIdentity());
            Member member = new Member(memberRequest.getId(), encodedPassword, memberRequest.getName(),
                    location, memberRequest.getBirthDay(), memberRequest.getPhoneNo());

//            LikedConcert likedConcert = new LikedConcert();
//            likedConcert.setConcertNo(new Long(0));
//            likedConcert.setConcertName("default");
//            likedConcert.setConcertArtist("default");
//            likedConcert.setConcertVenue("default");
//            likedConcert.setConcertPrice("default");
//            likedConcert.setConcertDate("default");
//            likedConcert.setConcertInfo("default");

//            String[] defaultOne = new String[0];
//            String[] defaultTwo = new String[0];

            //MemberTaste memberTaste = new MemberTaste("default", "default", "default", "default");

//            Board board = new Board("default", "default"); ????????? ????????? ????????? ????????? ??????
//            board.setId("default");
//            BoardReply boardReply = new BoardReply("default", "default");
//            board.addBoardReply(boardReply);

            //BookedConcert bookedConcert = new BookedConcert(null, null, null, null); ??????????????? ?????????

            member.addIdentity(memberIdentity);
            //member.addLikedConcert(likedConcert);
            //member.addMemberTaste(memberTaste);
            //member.addBoardContent(board);
            //member.addBookedConcert(bookedConcert);
            memberRepository.save(member);

            log.info("New ID has registered!");
            return true;
        }
    }

    @Override
    @Transactional
    public int login(MemberRequest memberRequest) throws Exception {

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberRequest.getId());
        if(maybeMember.isEmpty()) {
            return 1;
        } else if(!passwordEncoder.matches(memberRequest.getPassword(), maybeMember.get().getPassword())) {
            return 2;
        } else return 0;
    }

    @Override
    public boolean checkSessionValidation(String memberId) throws Exception {
        return memberRepository.findByMemberId(memberId).isPresent();
    }

    @Override
    public List<Member> getList() throws Exception {
        return memberRepository.getList();
    }

    @Override
    public Member read(Long memberNo) throws Exception {
        return memberRepository.findByMemberNo(memberNo).get();
    }

    @Override
    public void delete(Long memberNo) throws Exception {
        memberIdentityRepository.delete(memberNo); //?????? ?????? ???????????? ?????? ????????? ????????? ?????? ???????????? ?????????

        Long deleteNo;
        List<LikedConcert> likedConcertList = likedConcertRepository.findByMemberNo(memberNo);//????????? ??? ?????? ???????????? ???????????? ?????? concert?????? number_of_likes??? 1??? ????????? ????????????

        for(int i=0; i<likedConcertList.size(); i++) {
            deleteNo = likedConcertList.get(i).getConcertNo();
            concertRepository.minusNumberOfLikes(deleteNo);
        }

        Integer numOfVisitorsForDelete;
        List<BookedConcert> bookedConcertList = bookedConcertRepository.findByMemberNo(memberNo);

        for(int i=0; i<bookedConcertList.size(); i++) {
            numOfVisitorsForDelete = bookedConcertList.get(i).getNumOfVisitors();
            deleteNo = bookedConcertList.get(i).getConcertNo();
            concertRepository.plusVenueCapacity(numOfVisitorsForDelete, deleteNo);
        }

        String id = memberRepository.findByMemberNo(memberNo).get().getId(); //????????? ????????? ????????? ????????? id ?????????.
        boardRepository.makeIdAsWithdrawn(id);
        boardReplyRepository.makeIdAsWithdrawn(id);

        //concertRequestRepository.makeIdAsWithdrawn(memberNo); //????????? ????????? ????????? ?????? ????????? id ?????????.

        likedConcertRepository.delete(memberNo);

        memberTasteRepository.delete(memberNo);
        bookedConcertRepository.delete(memberNo);
        memberRepository.delete(memberNo);
    }

    @Override
    public MemberInfo modify(MemberRequest memberRequest) throws Exception { //?????? ?????????????????? ?????? ?????????????????? ????????? ??????????????? ???
        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());
        memberRequest.setPassword(encodedPassword);

        Long memberNo = memberRequest.getMemberNo().longValue();

        String location = memberRequest.getLocation().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

        memberIdentityRepository.modify(memberRequest.getIdentity(), memberNo);

        memberRepository.modify(memberRequest.getId(), memberRequest.getPassword(), memberRequest.getName(),
                location, memberRequest.getBirthDay(), memberRequest.getPhoneNo(), memberNo);

        //?????? ????????? ??????, ????????? ????????? ?????? ?????? ??????

        MemberInfo memberInfo = new MemberInfo(memberRequest.getMemberNo().longValue(), memberRequest.getId(), memberRequest.getIdentity());
        return memberInfo;
    }

    @Override
    public String addLiked(LikedConcert likedConcert) throws Exception {

        if(!isNotAlreadyLiked(likedConcert.getMemberNo(), likedConcert.getConcertNo())) {

            likedConcertRepository.save(likedConcert);
            concertRepository.plusNumberOfLikes(likedConcert.getConcertNo().longValue());

            return "";

        } else {
            log.info("isAlreadyLiked!");
            return "?????? ???????????? ???????????????!";
        }
    }

    @Override
    public boolean isNotAlreadyLiked(Long memberNo, Long concertNo) {
        return !likedConcertRepository.findByMemberNoAndConcertNo(memberNo, concertNo).isEmpty();
    }

    @Override
    public void deleteLiked(int[] intArrForDelete) throws Exception {
        Integer A = intArrForDelete[0];
        Integer B = intArrForDelete[1];
        likedConcertRepository.deleteByConcertNo(A.longValue(), B.longValue());
        concertRepository.minusNumberOfLikes(A.longValue()); //concert???????????? number_of_likes??? 1??? ??????
    }

    @Override
    public boolean checkPassword(MemberRequest memberRequest) throws Exception {

        Optional<Member> tmpMember = memberRepository.findByMemberId(memberRequest.getId());
        boolean ismatchedPassword = false;

        if(!tmpMember.isEmpty()) {
            ismatchedPassword = passwordEncoder.matches(memberRequest.getPassword(), tmpMember.get().getPassword()); //???????????? ????????? ?????????
            return ismatchedPassword;
        }
        return ismatchedPassword;
    }
}
