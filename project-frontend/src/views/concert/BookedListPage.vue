<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MY BOOKED LIST</p>
        <p class="description">회원님이 예약한 공연 정보입니다. :)</p>
        <div class="modal-frame" :class="{ hide: !showModal }">
            <div class="modal-bg"></div>
            <div class="modal-content">
                <p class="mt-5 description">정말 해당 예약 요청을 취소할까요?</p>
                <v-btn class="btn" dark @click="confirm">네</v-btn>
                &emsp;
                <v-btn class="btn" dark @click="cancel">아니오</v-btn>
            </div>
        </div>
        <v-container class="description">
            <p class="float-right">회원님이 예약한 공연 수 {{ bookedList.length }}</p>
            <div style="clear: both;"></div>
            <v-simple-table dense dark>
                <tr>
                    <td>공연 이름</td>
                    <td>연락처</td>
                    <td>인원 수</td>
                    <td>메세지</td>
                    <td>예약일</td>
                    <td>수정</td>
                </tr>
                <tr v-for="(booked, i) in bookedList" :key="i">
                    <td>{{ booked.concertName }}</td>
                    <td>{{ booked.phoneNumber }}</td>
                    <td>{{ booked.numOfVisitors }}</td>
                    <td>{{ booked.message ? booked.message : '남긴 메세지 없음' }}</td>
                    <td>{{ booked.regDate }}</td>
                    <td style="width: 120px;">
                        <v-btn class="btn" @click="cancelBook(booked.bookedConcertNo)">예약 취소</v-btn>
                    </td>
                </tr>
            </v-simple-table>
        </v-container>
    </div>

</template>

<script>
import axios from 'axios';
import { mapActions, mapState } from 'vuex'
export default {
    data() {
        return {
            showModal: false
        }
    },
    computed: {
        ...mapState(['bookedList'])
    },
    methods: {
        ...mapActions(['fetchBookedList']),

        cancelBook() {
            this.showModal = true;
        },
        cancel() {
            this.showModal = false;
        },
        confirm(bookedConcertNo) {
            axios.delete(`http://localhost:8888/concert/deleteBooking/${ bookedConcertNo }`)
                .then(() => {
                    this.$store.commit('handleDeleteBooking', bookedConcertNo);
                })
                .catch(err => {
                    console.log(err);
                });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.fetchBookedList(userInfo.memberNo);
        }
    }
}
</script>