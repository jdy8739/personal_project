<template>
    <div align="center" class="main-bg grey darken-4">
        <div class="container fonted">
            <h3>{{ concert.concertName }}</h3>
            <div>
                <img class="responsive-img round-img" @click="snsRequest"
                :src="require(`../../../../project-backend/images/artist_pics/${ concert.concertArtist }.jpg`)">
            </div>
            <br><br>
            <h5>{{ concert.concertArtist }}</h5>
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

        <!-- <div class="container" style="width: 25%;">
            <textarea class="infoText" style='height: 90px; color: white;' disabled v-bind:value="concert.concertInfo"/>
        </div>
          
        <div align="center" style="margin-bottom: 60px;"> -->
            <!-- <v-btn text="text" class="btn-flat red-text waves-effect waves-teal" style="margin-right: 30px;" 
            @click="onReservation" outlined>예약하기!</v-btn> -->
            <!-- <booking-dialogue v-if="notBookedYet || isLoggedIn == false" :member="member" :concertNo="concertNo"/> -->

            <!-- <v-tooltip bottom v-else-if="!notBookedYet && isLoggedIn == true">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn class="btn-flat red-text waves-effect waves-teal" style="margin-right: 30px;" @click="cancelBook" outlined color="pink" v-bind="attrs" v-on="on">
                        <v-icon>
                            email
                        </v-icon>
                    </v-btn>
                </template>
                <span>예약 취소</span>    
            </v-tooltip>

            <v-tooltip bottom v-if="notLikedYet == true || isLoggedIn == false">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn text="text" class="btn-flat red-text waves-effect waves-teal" style="margin-right: 10px;" outlined
                    @click="addLiked(concert.concertNo)" color="white" v-bind="attrs" v-on="on">
                        <v-icon>
                            mdi-heart
                        </v-icon>
                    </v-btn>
                </template>
                <span>찜하기!</span>    
            </v-tooltip>

            <v-tooltip bottom v-else-if="notLikedYet == false && isLoggedIn == true">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn text="text" class="btn-flat red-text waves-effect waves-teal" style="margin-right: 10px;" outlined
                    @click="unLiked(concert.concertNo)" color="pink" v-bind="attrs" v-on="on">
                        <v-icon>
                            mdi-heart
                        </v-icon>
                    </v-btn>
                </template>
                <span>찜해제</span>    
            </v-tooltip> -->

            <!-- <p>.....{{ likedList }}</p> -->
        <!-- </div> -->
    </div>    
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

//import BookingDialogue from '@/components/concertMainDialogue/BookingDialogue'

export default {
    name: 'ConcertDetailPage',
    components: {
        //BookingDialogue
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
        ...mapState(['concert', 'likedList', 'isLoggedIn', 'userProfile', 'notLikedYet', 'member', 'notBookedYet'])
    },
    methods: {
        ...mapActions(['fetchLikedOrNot', 'fetchConcert', 'fetchMember', 'fetchBookedOrNot']),

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

            if(this.$store.state.isLoggedIn == true) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data == true) {
                            // var index = this.concert.concertNo -1
                            // this.likedList.splice(index, 1, 1)

                            const memberNo = this.$store.state.userProfile.memberNo
                            const concertNo = this.$store.state.concert.concertNo
                            const concertName = this.$store.state.concert.concertName
                            const concertArtist = this.$store.state.concert.concertArtist
                            const concertVenue = this.$store.state.concert.concertVenue
                            const concertPrice = this.$store.state.concert.concertPrice
                            const concertDate = this.$store.state.concert.concertDate
                            const concertInfo = this.$store.state.concert.concertInfo

                            // console.log("{ memberNo }: " + memberNo)
                            // console.log("{ concert }: " + JSON.stringify(this.$store.state.concert))

                            axios.post('http://localhost:8888/member/addLiked', { memberNo, concertNo, concertName, concertArtist, concertVenue, concertPrice, concertDate, concertInfo })
                                .then(alert('관심 목록에 추가되었습니다!'))

                                this.$store.state.concert.numberOfLikes ++
                                this.$store.state.notLikedYet = false

                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!')
                            this.$store.state.isLoggedIn = false
                        }
                    })
            } else {
                alert('로그인이 필요한 서비스입니다!')
            }
        },
        unLiked() {

            if(this.$store.state.isLoggedIn == true) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data == true) {
                            // var index = this.concert.concertNo -1
                            // this.likedList.splice(index, 1, 0)

                            let formData = new FormData()

                            formData.append("memberNo", this.$store.state.userProfile.memberNo)
                            formData.append("concertNo", this.$store.state.concert.concertNo)

                            axios.post('http://localhost:8888/member/deleteLiked', formData)
                                .then(alert('관심 목록에서 제거되었습니다!'))

                                this.$store.state.concert.numberOfLikes --
                                this.$store.state.notLikedYet = true

                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!')
                            this.$store.state.isLoggedIn = false
                        }
                    })
            } else {
                alert('로그인이 필요한 서비스입니다!')
            }
        },
        cancelBook() {
            alert('예약 취소는 마이페이지의 예약리스트에서 할 수 있습니다! :)')
        }
    },
    mounted() {
        this.fetchConcert(parseInt(this.concertNo));
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
    width: 35%;
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

</style>
