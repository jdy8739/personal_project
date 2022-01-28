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

//            Board board = new Board("default", "default"); 보드를 멤버에 엮을까 하다가 취소
//            board.setId("default");
//            BoardReply boardReply = new BoardReply("default", "default");
//            board.addBoardReply(boardReply);

            //BookedConcert bookedConcert = new BookedConcert(null, null, null, null); 안넣어줘도 되네???

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
        memberIdentityRepository.delete(memberNo); //자식 먼저 지워주고 뒤에 부모를 지워야 전부 깔끔하게 지워짐

        Long deleteNo;
        List<LikedConcert> likedConcertList = likedConcertRepository.findByMemberNo(memberNo);//탈퇴할 때 해당 탈퇴하는 사용자가 찜한 concert에서 number_of_likes를 1씩 빼주는 알고리즘

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

        String id = memberRepository.findByMemberNo(memberNo).get().getId(); //탈퇴한 회원이 작성한 보드의 id 바꿔줌.
        boardRepository.makeIdAsWithdrawn(id);
        boardReplyRepository.makeIdAsWithdrawn(id);

        //concertRequestRepository.makeIdAsWithdrawn(memberNo); //탈퇴한 회원이 작성한 공연 요청의 id 바꿔줌.

        likedConcertRepository.delete(memberNo);

        memberTasteRepository.delete(memberNo);
        bookedConcertRepository.delete(memberNo);
        memberRepository.delete(memberNo);
    }

    @Override
    public MemberInfo modify(MemberRequest memberRequest) throws Exception { //자식 레퍼지토리와 부모 레퍼지토리를 동시에 수정해줘야 함
        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());
        memberRequest.setPassword(encodedPassword);

        Long memberNo = memberRequest.getMemberNo().longValue();

        String location = memberRequest.getLocation().replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"", "");

        memberIdentityRepository.modify(memberRequest.getIdentity(), memberNo);

        memberRepository.modify(memberRequest.getId(), memberRequest.getPassword(), memberRequest.getName(),
                location, memberRequest.getBirthDay(), memberRequest.getPhoneNo(), memberNo);

        //위는 넣어서 수정, 아래는 수정된 기본 정보 반환

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
            return "이미 좋아요한 공연이에요!";
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
        concertRepository.minusNumberOfLikes(A.longValue()); //concert테이블에 number_of_likes가 1씩 감소
    }

    @Override
    public boolean checkPassword(MemberRequest memberRequest) throws Exception {

        Optional<Member> tmpMember = memberRepository.findByMemberId(memberRequest.getId());
        boolean ismatchedPassword = false;

        if(!tmpMember.isEmpty()) {
            ismatchedPassword = passwordEncoder.matches(memberRequest.getPassword(), tmpMember.get().getPassword()); //암호화된 코드가 먼저옴
            return ismatchedPassword;
        }
        return ismatchedPassword;
    }
}
