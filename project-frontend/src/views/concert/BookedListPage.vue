<template>
    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;">

        <h3 class="topBar" style="margin-top: 0px; padding-top: 30px;">MY BOOKED LIST</h3>
        <p class="description" style="margin-right: 20px;">회원님이 예약한 공연 정보입니다. :)</p>

        <v-container style="margin-top: 40px;"> 
            
            <v-data-table :headers="headerTitle" dark
                            :items="bookedList"
                            :items-per-page="5"
                            class="elevation-1">

                        <template v-slot:item="{ item, index }"> 
                            <tr>
                                <td>{{ index + 1 }}</td>
                                <td @click="handleClick(item.concertNo)" class="footerText">{{ item.name }}</td> 
                                <td>{{ item.phoneNumber }}</td>
                                <td>{{ item.concertName }}</td>
                                <td>{{ item.numOfVisitors }}</td>
                                <td>{{ item.message }}</td>
                                <td>{{ item.regDate }}</td>
                                <td>
                                    <v-btn class="ma-2" text color="error lighten-4" @click="goToBookedAlterPage(item.bookedConcertNo)" style="font-size: 11px;">
                                        예약 변경
                                    </v-btn>

                                    <v-btn class="ma-2" text color="error lighten-2" @click="goToDetailPage(item.concertNo)" style="font-size: 11px;">
                                        해당 공연 정보
                                    </v-btn>
                                </td>
                
                            </tr>
                        </template>

            </v-data-table>
        </v-container>

    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    name: 'BookedListPage',
    data() {
        return {
            headerTitle: [
                { text: '순번', value: '', width: '5%' },
                { text: '이름', value: '', width: '5%'},
                { text: '휴대폰 번호', value: '', width: '8%'},
                { text: '콘서트명', value: '', width: '12%'},
                { text: '인원', value: '', width: '5%'},
                { text: '요청 메세지', value: '', width: '20%'},
                { text: '예약 일시', value: '', width: '8%'},
                { text: '비고', value: '', width: '18%'}
            ]
        }
    },
    methods: {
        ...mapActions(['fetchBookedList', 'fetchBookedConcert']),

        goToBookedAlterPage(bookedConcertNo) {

            //this.fetchBookedConcert(bookedConcertNo)

            this.$router.push({
                name: 'BookedAlterPage',
                params: { bookedConcertNo: bookedConcertNo }
            })
        },
        goToDetailPage(concertNo) {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: concertNo }
            })
        }
    },
    computed: {
        ...mapState(['bookedList'])
    },
    mounted() {
        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity

            this.fetchBookedList(this.$store.state.userProfile.memberNo)
        }
    }
}
</script>

<style scoped>

/* .footerText:hover {
  color: teal;
  font-size: 15px;
} */

</style>
