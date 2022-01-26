<template>
    <div align="center" class="main-bg grey darken-4">
        <div class="container fonted">
            <h3>{{ concert.concertName }}</h3>
            <img class="responsive-img round-img" @click="snsRequest"
            :src="require(`../../../../project-backend/images/concert_pics/${ concert.folderName }/${ concert.concertName }-2.jpg`)">
            <br>
            <h4>{{ concert.concertArtist }}</h4>
            <h5>{{ concert.concertVenue }}</h5>
            <h5>{{ concert.concertGenre }}</h5>
            <br>
            <h5>
                {{ concert.concertDate }}
                <span>&ensp;
                    {{ concert.concertTime }}
                </span>
            </h5>
            <h5>
                가격
                <span>
                    {{ concert.concertPrice }}
                </span>
            </h5>
            <h5>
                좋아요 수
                <span>
                    {{ concert.numberOfLikes }}
                </span>
            </h5>
            <h5>
                인원
                <span>
                    {{ concert.venueCapacity }}
                </span>
            </h5>
            <p>{{ concert.concertInfo }}</p>
        </div>
        <hr style="width: 25%">
        <div class="flex-box mt-6 fonted">
            <p class="mt-2 mr-5">이 공연에 관심있으세요?</p>
            <span>
                <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                        <div v-if="!isLoggedIn || !notLikedYet">
                            <v-btn text="text" class="btn-flat red-text waves-effect waves-teal mr-4" outlined
                            @click="addLiked(concert.concertNo)" color="white" v-bind="attrs" v-on="on">
                                <v-icon>mdi-heart</v-icon>
                            </v-btn>
                        </div>
                        <div v-else-if="isLoggedIn">
                            <v-btn text="text" class="btn-flat red-text waves-effect waves-teal mr-4" outlined
                            @click="unLiked(concert.concertNo)" color="pink" v-bind="attrs" v-on="on">
                                <v-icon>mdi-heart</v-icon>
                            </v-btn>
                        </div>
                    </template>
                    <span v-if="!isLoggedIn || !notLikedYet">찜하기</span>
                    <span v-else-if="isLoggedIn">찜해제</span>
                </v-tooltip> 
            </span>
            <booking-dialogue :concertNo="parseInt(concertNo)"/>
        </div>
    </div>    
</template>

<script>
import axios from 'axios'
import { mapActions, mapMutations, mapState } from 'vuex'
import BookingDialogue from '@/components/concertMainDialogue/BookingDialogue'

export default {
    name: 'ConcertDetailPage',
    components: {
        BookingDialogue
    },
    props: {
        concertNo: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            urlList: [
                'https://ko-kr.facebook.com/bandflyingdog',
                'https://www.instagram.com/kidcozyboy/?hl=ko',
                'https://ko-kr.facebook.com/Messgram/',
                'https://www.instagram.com/crackshot_official/?hl=ko',
                'https://www.facebook.com/singersongwritersoeun',
                'https://www.instagram.com/rolling_quartz/?hl=ko',
                'https://www.instagram.com/oceanfromtheblue/',
                'http://www.beastsandnatives.com/#subscribe'
            ]
        }
    },
    computed: {
        ...mapState(['concert', 'likedList', 'isLoggedIn', 'userProfile', 'member', 'notBookedYet', 'notLikedYet'])
    },
    methods: {
        ...mapActions(['fetchLikedOrNot', 'fetchConcert', 'fetchBookedOrNot']),
        ...mapMutations(['handleUserLogin', 'handleDislikeConcert', 'handleLikeConcert']),
        // urlRequest() {
        //     const urlListNo = parseInt(this.concertNo) - 1;
        //     try {
        //         return this.urlList[urlListNo];
        //     } catch (e) {
        //         return 'MainPage';
        //     }
        // },

        // imgRequest() {
        //         try {                
        //             return require(`@/assets/img/artist_pics/${this.concert.concertArtist}.jpg`)
        //         } catch (e) {
        //             return require(`@/assets/logo.png`)
        //         }
        // },
        snsRequest() {
            const urlListNo = parseInt(this.concertNo) - 1;
            window.open(`${ this.urlList[urlListNo] }`, "_blank");    
        },

        addLiked() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                           
                            const memberNo = this.userProfile.memberNo;
                            const concertNo = this.concertNo;
                         
                            axios.post('http://localhost:8888/member/addLiked', { memberNo, concertNo })
                                .then(() => {
                                    this.handleLikeConcert();
                                    alert('관심 목록에 추가되었습니다!');
                                });
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                        }
                    });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        unLiked() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                            let formData = new FormData();

                            formData.append("memberNo", this.userProfile.memberNo);
                            formData.append("concertNo", this.concertNo);

                            axios.post('http://localhost:8888/member/deleteLiked', formData)
                                .then(() => {
                                    this.handleDislikeConcert();
                                    alert('관심 목록에서 제거되었습니다!');
                                });
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                        }
                    });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        cancelBook() {
            alert('예약 취소는 마이페이지의 예약리스트에서 할 수 있습니다! :)')
        }
    },
    mounted() {
        this.fetchConcert(parseInt(this.concertNo));

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.handleUserLogin(userInfo);

            const chkArr = [ userInfo.memberNo, this.concertNo ];
            const formData = new FormData();
            formData.append('likedOrNotNumArr', chkArr);

            this.fetchLikedOrNot(formData);
            this.fetchBookedOrNot(formData);
        } 
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&display=swap');

.fonted {
    font-family: 'Do Hyeon', sans-serif;
    font-style: italic;
}

hr{
	margin: 0px auto;
}

h5>span {
    color: rgb(221, 107, 107);
}

.round-img {
    border-radius: 50%;
    min-width: 200px;
    width: 25%;
    filter: grayscale(0.9);
    cursor: pointer;
    transition: all 1s;
}

.round-img:hover {
    filter: grayscale(0.1);
}

.search {
    position: absolute; 
    left: 50%;
    top: 25%;
    transform: translate(-50%, -50%);
    color: #ffffff;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 13px;
}

.flex-box {
    display: flex;
    justify-content: center;
}

</style>
