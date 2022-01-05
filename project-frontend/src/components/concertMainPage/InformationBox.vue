<template>
    <div class="info-box">
        
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import axios from 'axios'
import { mapState } from 'vuex'

export default {
    name: 'InformationBox',
    props: {
        concert: {
            type: Object
        }
    },
    data() {
        return {
            infoBar: false,
            date: '일시 :'
        }
    },
    computed: {
        ...mapState(['likedList', 'isLoggedIn', 'userProfile', 'notLikedYet'])
    },
    methods: {
         //...mapActions(['fetchLikedOrNot']),

        offInfoBox() {
            EventBus.$emit('hideInfoBar')
            this.infoBar = false
        },
        sendToDetailPage() {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: this.concert.concertNo.toString() }
            })
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
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&display=swap');

.infoText {
    color: #FFFFFF;
    font-family: 'Do Hyeon', sans-serif;
    font-style: italic;
    font-size: 13px;
    line-height: 20px;
}

textarea {
    resize: none;
}

.info-box {
    width: 100%;
    height: 300px;
    background-color: rgb(53, 56, 59);
}

</style>
