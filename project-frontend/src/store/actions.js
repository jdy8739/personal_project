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

import axios from 'axios'

export default {

    fetchConcert({ commit }, num) {
        return axios.get(`http://localhost:8888/concert/${num}`)
            .then((res) => {
                commit(FETCH_CONCERT, res.data);
            })
            .catch(err => {
                alert(err.response.data.message);
            });
    },

    fetchMemberList({ commit }) {
        return axios.get('http://localhost:8888/member/list')
            .then((res) => {
                commit(FETCH_MEMBER_LIST, res.data)
            });
    },

    fetchMember({ commit }, memberNo) {
        return axios.get(`http://localhost:8888/member/read/${memberNo}`)
            .then((res) => {
                commit(FETCH_MEMBER, res.data);
            });
    },

    async crawlFind({ commit }, keyword) {
        axios.get(`http://localhost:8888/${keyword}`)
            .then(({ data }) => {
                commit(CRAWL_FIND, data);
            });
    },
    
    fetchLikedList({ commit }, payload) { //한 회원의 좋아요한 공연 리스트 뽑아오기
        const memberNo = payload
        return axios.get(`http://localhost:8888/concert/likedList/${ memberNo }`)
            .then((res) => {
                commit(FETCH_LIKED_LIST, res.data)
            })
    },

    fetchLikedOrNot({ commit }, payload) {
        return axios.post('http://localhost:8888/member/fetchLikedOrNot', payload)
            .then((res) => {
                commit(FETCH_LIKED_OR_NOT, res.data);
            })
    },

    fetchBoardList({ commit }) {
        return axios.get('http://localhost:8888/board/list')
            .then((res) => {
                commit(FETCH_BOARD_LIST, res.data)
            })
    },

    fetchBoard({ commit }, num) {
        return axios.get(`http://localhost:8888/board/read/${ num }`)
            .then((res) => {
                commit(FETCH_BOARD, res.data)
            })
    },

    fetchAlreadyDecidePrefOrNot({ commit }, num) {
        
        return axios.get(`http://localhost:8888/member/taste/alreadyDecidePref/${ num }`)
            .then(res => {
                commit(FETCH_DECIDE_OR_NOT, res.data)
            })
    },

    fetchTaste({ commit }, num) {
        return axios.get(`http://localhost:8888/member/taste/getTaste/${ num }`)
            .then(res => {
                commit(FETCH_TASTE, res.data)
            })
    },

    fetchUserName({ commit }, num) {
        return axios.get(`http://localhost:8888/member/concert_register/getName/${ num }`)
            .then(res => {
                commit(FETCH_USER_NAME, res.data)
            })
    },

    fetchConcertRequestList({ commit }) {
        return axios.get('http://localhost:8888/member/concert_register/get_all_request')
            .then(res => {
                commit(FETCH_CONCERT_REQUEST_LIST, res.data);
            })
    },
    
    fetchConcertRequest({ commit }, num) {
        return axios.get(`http://localhost:8888/member/concert_register/get_concert_request/${ num }`)
            .then(res => {
                commit(FETCH_CONCERT_REQUEST, res.data);
            });
    },

    fetchMyRequestList({ commit }, num) {
        return axios.get(`http://localhost:8888/member/concert_register/get_my_request/${ num }`)
            .then(res => {
                commit(FETCH_MY_REQUEST_LIST, res.data);
            })
    },

    fetchBookedOrNot({ commit }, payload) {
        // ** 원래 변수명을 bookedOrNotCheckNums라고 해줘야하지만 스프핑을 바꿔줘야해서 그냥 likedOrNotCheckNums로 간다.
        return axios.post('http://localhost:8888/concert/fetchBookedOrNot', payload)
            .then((res) => {
                commit(FETCH_BOOKED_OR_NOT, res.data);
            })
    },

    fetchBookedList({ commit }, num) {
        return axios.get(`http://localhost:8888/concert/fetchBookedList/${ num }`)
            .then(res => {
                commit(FETCH_BOOKED_LIST, res.data);
            })
    },

    fetchBookedConcert({ commit }, num) {
        return axios.get(`http://localhost:8888/concert/fetchBookedConcert/${ num }`)
            .then(res => {
                commit(FETCH_BOOKED_CONCERT, res.data);
            })
    },

    fetchSearchedResults({ commit }, keyword) {
        return axios.post(`http://localhost:8888/concert/searchText/${keyword}`)
            .then(res => {
                commit(FETCH_SEARCHED_RESULTS, res.data);
            })
            .catch(err => {
                alert('검색기능이 원활하지 않습니다. 잠시후에 다시 시도해주세요.');
                console.log(err);
            })
    },

    fetchConcertListAll({ commit }) {
        return axios.get('http://localhost:8888/concert/list')
            .then((res) => {
                commit(FETCH_CONCERT_LIST_ALL, res.data);
            })
            .catch((err) => {
                console.log(err);
            })
    },

    fetchUnlockedConcertList({ commit }) {
        return axios.get('http://localhost:8888/concert/unlockedList')
            .then((res) => {
                commit(FETCH_CONCERT_LIST, res.data);
            })
            .catch((err) => {
                console.log(err);
            })
    }
}