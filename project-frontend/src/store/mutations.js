import {
    FETCH_CONCERT,

    FETCH_MEMBER_LIST,
    FETCH_MEMBER,
    
    CRAWL_FIND,

    FETCH_LIKED_LIST,
    FETCH_LIKED_OR_NOT,

    FETCH_BOARD_LIST,
    FETCH_BOARD,

    FETCH_DECIDE_OR_NOT,
    FETCH_TASTE,
    
    FETCH_USER_NAME,

    FETCH_CONCERT_REQUEST_LIST,
    FETCH_CONCERT_REQUEST,
    FETCH_MY_REQUEST_LIST,

    FETCH_BOOKED_OR_NOT,
    FETCH_BOOKED_LIST,
    FETCH_BOOKED_CONCERT,

    FETCH_SEARCHED_RESULTS,

    FETCH_CONCERT_LIST,
    FETCH_CONCERT_LIST_ALL

} from './mutation-types'

const NOT_EXIST = 'notExist';
const NUMBER_OF_ROW = 4;

export default {
    [FETCH_CONCERT] (state, payload) {
        state.concert = payload
    },
    
    [FETCH_MEMBER_LIST] (state, payload) {
        state.members = payload
    },

    [FETCH_MEMBER] (state, payload) {
        state.taste = payload.memberTasteList[0];
        delete payload.memberTasteList;
        state.member = payload;
    },

    [CRAWL_FIND] (state, payload) {
        state.newsList = payload
    },

    [FETCH_LIKED_LIST] (state, payload) {
        state.likedList = payload
    },

    [FETCH_LIKED_OR_NOT] (state, payload) {
        state.notLikedYet = payload
    },

    [FETCH_BOARD_LIST] (state, payload) {
        state.boards = payload
    },

    [FETCH_BOARD] (state, payload) {
        if(payload === '') {
            state.board = NOT_EXIST;
            return;
        }
        state.replyList = payload.boardReplyList;
        delete payload['boardReplyList'];
        state.board = payload;
    },

    [FETCH_DECIDE_OR_NOT] (state, payload) {
        state.notDecidedYet = payload
    },

    [FETCH_TASTE] (state, payload) {
        if(payload) state.taste = payload
    },

    [FETCH_USER_NAME] (state, payload) {
        state.userName = payload
    },

    [FETCH_CONCERT_REQUEST_LIST] (state, payload) {
        state.requestList = payload
    },

    [FETCH_CONCERT_REQUEST] (state, payload) {
        if(payload === "") payload = NOT_EXIST;
        state.concertRequest = payload;
    },

    [FETCH_MY_REQUEST_LIST] (state, payload) {
        state.requestList = payload
    },

    [FETCH_BOOKED_OR_NOT] (state, payload) {
        state.notBookedYet = payload
    },

    [FETCH_BOOKED_LIST] (state, payload) {
        state.bookedList = payload
    },

    [FETCH_BOOKED_CONCERT] (state, payload) {
        state.bookedConcert = payload
    },

    [FETCH_SEARCHED_RESULTS] (state, payload) {
        state.searchedResults = payload
    },

    [FETCH_CONCERT_LIST] (state, payload) {

        const arr = [];
        let deposit = [];
        let cnt = 1;

        for(let i=0; i<payload.length; i++) {

            deposit.push(payload[i]);

            if(cnt % NUMBER_OF_ROW == 0) {
                arr.push(deposit);
                deposit = [];
            }
            cnt ++;
        }
        state.concertList = [...arr, deposit];
    },

    handleUserLogin(state, payload) {
        if(payload) {
            state.isLoggedIn = true;
            state.userIdentity = payload.identity;
            state.userProfile = payload;
        }
        else {
            state.isLoggedIn = false;
            state.userIdentity = '';
            state.userProfile = '';
            state.userTaste = '';
        }
    },

    delLikedList(state, payload) {
        state.likedList.splice(payload, 1);
    },

    handleDislikeConcert(state) {
        state.concert.numberOfLikes --;
        state.notLikedYet = false;
    },

    handleLikeConcert(state) {
        state.concert.numberOfLikes ++;
        state.notLikedYet = true;
    },

    handleReplyRequest(state, payload) {
        if(payload[1] === undefined) {
            state.replyList.splice(payload, 1);
        } else {
            state.replyList.splice(payload[0], 1, payload[1]);
        }  
    },

    handleRequestApprove(state) {
        if(state.concertRequest.approvedOrNot) state.concertRequest.approvedOrNot = false;
        else state.concertRequest.approvedOrNot = true;
    },

    deleteMember(state, payload) {
        const targetIndex = state.members.findIndex(member => member.memberNo === payload);
        state.members.splice(targetIndex, 1);
    },

    handleConcertBook(state, payload) {
        state.concert.venueCapacity -= parseInt(payload);
        state.notBookedYet = true;
    },

    handleDeleteBooking(state, payload) {
        const targetIndex = state.bookedList.findIndex(booked => booked.bookedConcertNo === payload);
        state.bookedList.splice(targetIndex, 1);
    },

    [FETCH_CONCERT_LIST_ALL] (state, payload) {
        state.concertListAll = payload;
    },

    handlePostConcert(state, payload) {
        const targetIndex = state.concertListAll.findIndex(concert => {
            return concert.concertNo === payload;
        });
        state.concertListAll[targetIndex].locked = !state.concertListAll[targetIndex].locked;
    },

    setDateCriteria(state, payload) {
        const [ startDate, endDate ] = payload;
        state.concertNoFilteredByDate = [];
        let dateElem = '';
        for(let i=0; i<state.concertList.length; i++) {
            for(let j=0; j<state.concertList[i].length; j++) {
                dateElem = state.concertList[i][j].concertDate;
                if(startDate <= dateElem && endDate >= dateElem) {
                    state.concertNoFilteredByDate.push(i * NUMBER_OF_ROW + j + 1);
                }
            }
        }
    },

    setConcertListByUserTaste(state, payload) {
        state.taste.chosenGenres = [...payload];
    },

    joinConcertList(state, payload) {
        let i = state.concertList.length;
        let j = state.concertList[i - 1].length;
        if(j === 0) {
            i -= 1;
            j = NUMBER_OF_ROW;
        }
        if(j === NUMBER_OF_ROW) {
            state.concertList.push(payload);
        } else {
            const cut = NUMBER_OF_ROW - j;
            for(let k=0; k<cut; k++) {
                state.concertList[i - 1].push(payload[k]);
            }
            state.concertList.push(payload.slice(cut, payload.length)); 
        }
    }
}
    