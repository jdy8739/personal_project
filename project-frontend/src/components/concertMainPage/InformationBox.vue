<template>
    <div class="info-box info-text mt-0 mb-7 row">
        <div class="col-md-5 col-sm-12">
            <h3 class="mt-0">{{ getChosenConcert.concertName }}</h3>
            <h5><span>artist</span>&ensp; {{ getChosenConcert.concertArtist }}</h5>
            <h5><span>place</span>&ensp; {{ getChosenConcert.concertVenue }}</h5>
            <span>
                <h5>
                    time
                    &ensp;
                    {{ getChosenConcert.concertDate }}
                    {{ getChosenConcert.concertTime }}
                </h5>
            </span>
        </div>
        <div class="col-md-4 com-sm-12">
            {{ notLikedYet }}
            <h5>입장료 {{ getChosenConcert.concertPrice }}</h5>
            <h5>{{ getChosenConcert.concertInfo }}</h5>
        </div>
        <div class="col-md-3 com-sm-12 btn-section">
            <v-btn @click="addLiked">찜하기</v-btn>
            <v-btn @click="sendToDetailPage">바로가기</v-btn>
            <v-btn @click="closeInfoBox">닫기</v-btn>
            <p>MUSIC GHUETTO</p>
        </div>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

export default {
    name: 'InformationBox',
    props: {
        concertIdx: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
        
        }
    },
    computed: {
        ...mapState(['likedList', 'isLoggedIn', 'userProfile', 'notLikedYet', 'concertList']),

        getChosenConcert() {
            const row = Math.floor(this.concertIdx / 4);
            const col = this.concertIdx % 4;

            return this.concertList[row][col];
        }
    },
    methods: {
        ...mapActions(['fetchLikedOrNot']),
        sendToDetailPage() {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: (this.concertIdx + 1).toString() }
            });
        },
        closeInfoBox() {
            EventBus.$emit('offBox');
        },
        addLiked() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                           
                            const memberNo = this.userProfile.memberNo;
                            const concertNo = this.concertIdx + 1;
                         
                            axios.post('http://localhost:8888/member/addLiked', { memberNo, concertNo })
                                .then((res) => {
                                    if(res.data.length > 10) alert('이미 좋아요한 공연이에요!!');
                                    else alert('관심 목록에 추가되었습니다!');
                                });
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                        }
                    });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&display=swap');

.info-text {
    color: #FFFFFF;
    font-family: 'Do Hyeon', sans-serif;
    font-style: italic;
    font-size: 28px;
    line-height: 20px;
}

textarea {
    resize: none;
}

.info-box {
    width: 100%;
    height: 300px;
    background-color: rgb(53, 56, 59);
    border-top: 1px solid white;
    border-bottom: 1px solid white;
    color: white;
    padding: 45px 110px;
}

.info-box span {
    color: rgb(175, 175, 175);
}

.btn-section {
    display: flex;
    align-items: center;
    position: relative;
}

.btn-section button {
    margin: 10px;
}

.btn-section p {
    font-size: 18px;
    position: absolute;
    bottom: 0px;
    right: 0px;
    color: grey
}

@media screen and (max-width: 950px) {
    .info-box {
        height: 700px;
    }

    .info-box>div {
        height: 33.33%;
    }

    .btn-section {
        flex-direction: column;
        align-items: flex-start;
    }
}

</style>
