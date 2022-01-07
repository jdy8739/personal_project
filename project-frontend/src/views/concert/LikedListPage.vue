<template>
    <div align="center" class="main-bg grey darken-4">
        <div class="container">
            <h3 class="topBar">LIKED LIST</h3>
            <p class="description mr-5">찜하신 공연을 한눈에 확인해보세요 :)</p>
            <p class="description total">좋아요한 공연 총 &ensp;{{ likedList.length }} 개</p>
            <v-simple-table class="table mt-8" dark dense>
                <tr>
                    <td>콘서트명</td>
                    <td>아티스트</td>
                    <td>수용 인원</td>
                    <td>일시</td>
                    <td style="text-align: center;">수정</td>
                </tr>
                <tr v-for="(item, i) in likedList" :key="i" class="t-item">
                    <td>{{ item.concertName }}</td>
                    <td>{{ item.concertArtist }}</td>
                    <td>{{ item.venueCapacity }}</td>
                    <td>{{ item.concertDate }} {{ item.concertTime }}</td>
                    <td style="text-align: right;">
                        <v-btn class="btn" @click="delLikedConcert(item.concertNo, i)">
                            좋아요 취소
                        </v-btn>
                        <v-btn class="btn" @click="goToDetailPage(item.concertNo)">
                            바로가기
                        </v-btn>
                    </td>
                </tr>
            </v-simple-table>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'LikedPage',
    computed: {
       ...mapState(['likedList', 'userProfile', 'isLoggedIn'])
    },
    data() {
        return {

        }
    },
    methods: {
        ...mapActions(['fetchLikedList', 'fetchConcert']),
        ...mapMutations(['handleUserLogin', 'delLikedList']),

        goToDetailPage(concertNo) {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: concertNo.toString() } 
            });
        },
        delLikedConcert(conNo, index) { //conNo는 concertNo랑 같음
            
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {

                            let formData = new FormData();

                            formData.append("memberNo", this.userProfile.memberNo);
                            formData.append("concertNo", conNo);

                            axios.post('http://localhost:8888/member/deleteLiked', formData)
                                .then(() => {
                                    this.delLikedList(index);
                                    alert('관심 목록에서 제거되었습니다!');
                                });

                        } else {
                            this.handleUserLogin();
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                        }
                    });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
            
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);

            const memberNo = this.userProfile.memberNo;
            this.fetchLikedList(memberNo);
        } 
    }
}
</script>

<style>

.table {
    width: 100%;
    border: 0.5px solid rgb(88, 88, 88);
    border-radius: 8px;
    font-size: 12.5px;
    padding: 5px 12px;
}

.container {
    width: 70%;
}

</style>

<style scoped>

tr td:first-child {
    width: 28%;
}

tr td:nth-child(2) {
    width: 12%;
}

tr td:nth-child(3) {
    width: 10%;
}

tr td:last-child {
    width: 30%;
}

.t-item:hover {
    color: rgb(255, 152, 152);
}

.total {
    text-align: right;
    margin: 0px;
}

.btn {
    font-size: 12px;
    margin: 5px;
}

.btn:first-child {
    color: rgb(255, 153, 153);
}

.btn:last-child {
    color: rgb(247, 144, 27);
}

</style>