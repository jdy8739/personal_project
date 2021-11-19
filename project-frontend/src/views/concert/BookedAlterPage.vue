<template>
    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;">
   
        <h3 class="topBar" style="margin-top: 0px; padding-top: 30px;">MY BOOKED CONCERT</h3>
        <p class="description" style="margin-right: 30px;">회원님이 예약한 공연 정보입니다. :) 수정할 사항들을 입력하세요.</p>

        <booked-concert-alter v-if="bookedConcert" :bookedConcert="bookedConcert" @submit="onSubmit"/>
        <p v-else-if="!bookedConcert" class="description">예약하신 공연 정보를 불러오고 있습니다. 잠시만 기다려주세요! :)</p>
    </div>
</template>

<script>
//import axios from 'axios'

import BookedConcertAlter from '@/components/bookedConcert/BookedConcertAlter'
import { mapActions, mapState } from 'vuex'
import axios from 'axios'

export default {
    name: 'BookedReadPage',
    components: {
        BookedConcertAlter
    },
    props: {
        bookedConcertNo: {
            type: String,
            required: true
        }
    },
    methods: {
        ...mapActions(['fetchBookedConcert']),

        onSubmit(payload) {

            const { bookedConcertNo, memberNo, concertNo, name, concertName, phoneNumber, numOfVisitors, message } = payload

            axios.put('http://localhost:8888/concert/alterBooking', { bookedConcertNo, memberNo, concertNo, name, concertName, phoneNumber, numOfVisitors, message })
                .then(() => {
                    alert('예약 변경이 전송되었습니다!')

                    this.$router.push({
                        name: 'BookedListPage'
                    })
                })
                .catch(() => {
                    alert('잠시 후에 다시 시도해주세요!')
                })
        }
    },
    computed: {
        ...mapState(['bookedConcert'])
    },

    mounted() {
        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity

            this.fetchBookedConcert(this.bookedConcertNo)
            //alert("bookedConcert: " + JSON.stringify(this.$store.state.bookedConcert))
        } else {
            alert('권한이 없습니다!')

            this.$router.push({
                name: 'MainPage'
            })
        }
    }
}
</script>
