<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MY BOOKED LIST</p>
        <p class="description">회원님이 예약한 공연 정보입니다. :)</p>
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
                        <v-btn class="btn" @click="cancelBook(booked.bookedConcertNo)">예약 정보 수정</v-btn>
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
    computed: {
        ...mapState(['bookedList'])
    },
    methods: {
        ...mapActions(['fetchBookedList']),

        cancelBook(bookedConcertNo) {
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