<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT REQUEST</p>
        <p class="description" >회원님이 작성한 공연 게시 요청 정보입니다. 거부되어도 재요청을 보낼 수 있습니다. :)</p>
        <v-container>
            <div class="footerText mt-5">
                <h5 v-if="concertRequest.approvedOrNot">게시 승인된 공연</h5>
                <h5 v-if="!concertRequest.approvedOrNot">게시 승인 대기 중</h5>
            </div>
            <v-simple-table class="mt-12 description" dark dense>
                <tr>
                    <th>공연 명</th>
                    <td>{{ concertRequest.concertName }}</td>
                </tr>
                <tr>
                    <th>아티스트 명</th>
                    <td>{{ concertRequest.concertArtist }}</td>
                </tr>
                <tr>
                    <th>공연 장소</th>
                    <td>{{ concertRequest.concertVenue }}</td>
                </tr>
                <tr>
                    <th>장르</th>
                    <td>
                        <span v-for="(genre, i) in JSON.parse(concertRequest.concertGenre)" :key="i">
                            {{ genre }} &emsp;
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>수용 인원</th>
                    <td>{{ concertRequest.venueCapacity }}</td>
                </tr>
                <tr>
                    <th>입장료</th>
                    <td>{{ concertRequest.concertPrice }}</td>
                </tr>
                <tr>
                    <th>공연 시간</th>
                    <td>{{ concertRequest.concertDate }} {{ concertRequest.concertTime }}</td>
                </tr>
                <tr>
                    <th>추가 정보</th>
                    <td>{{ concertRequest.concertInfo }}</td>
                </tr>
                <tr>
                    <th>요청일</th>
                    <td>{{ concertRequest.regDate }}</td>
                </tr>
            </v-simple-table>
            <div class="float-right mt-5">
                <v-btn class="btn" dark>
                    수정
                </v-btn>
                <v-btn class="btn" @click="deleteRequest" dark>
                    요청 삭제
                </v-btn>
            </div>
        </v-container>
    </div>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
    name: 'MyRequestReadPage',
    props: {
        concertRequestNo: {
            type: Number,
            required: true
        }
    },
    computed: {
        ...mapState(['concertRequest', 'userProfile'])
    },
    methods: {
        deleteRequest() {
            axios.delete(`http://localhost:8888/member/concert_register/delete/${parseInt(this.concertRequestNo)}`)
                .then(() => {
                    alert('요청이 정상적으로 취소되었습니다.');
                    this.$router.push({ name: 'MyRequestListPage' });
                })
                .catch(err => console.log(err));
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.$store.dispatch('fetchConcertRequest', this.concertRequestNo);
        }
        if(this.userProfile.memberNo !== this.concertRequest.memberNo) {
            alert('존재하지 않거나 접근할 수 없는 게시물입니다.');
            this.$router.push({ name: 'MainPage' });
        } 
    }
}
</script>

<style scoped>
    th {
        color: grey;
    }
</style>