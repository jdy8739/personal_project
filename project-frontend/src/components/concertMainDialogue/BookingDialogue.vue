<template>
    <v-dialog v-model="bookingDialog" persistent max-width="400">
        <template v-slot:activator="{ on, attrs }">
            <v-btn color="pink" v-bind="attrs" @click="cancelBook" v-if="notBookedYet">
                <v-icon>email</v-icon>
            </v-btn>
            <v-btn outlined color="white" v-bind="attrs" v-on="on" v-else-if="!notBookedYet">
                <v-icon>email</v-icon>
            </v-btn>
        </template>
        <span>{{ notBookedYet ? '예약 취소' : '예약 하기' }}</span>
        <v-card class="grey darken-3">
            <v-card-title class="headLine">
                <p id="headline">Booking Receipt</p>
            </v-card-title>
            <v-card-text>
                <v-layout wrap>
                    <form>
                        <v-flex xs12>
                            <v-text-field label="휴대폰 번호" type="text" required v-model="phoneNo" dark/>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field label="이름" type="text" style="width: 70%" required v-model="name" dark/>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field label="예약 인원" type="number" style="width: 40%" required v-model="numOfVisitors" dark/>
                        </v-flex>
                        <v-flex xs12>
                            <input 
                                type="text" 
                                id="message" 
                                label="메시지"
                                v-model="message" 
                                placeholder="해당 공연장 매니저에게 미리 남길 말이 있나요?"
                            />
                        </v-flex>
                        <p id="description">확인을 누르시면 예약정보가 문자로 전송됩니다!<br>
                        본 예약 서비스는 로그인 정보를 기반으로 해당 공연장소로 전달됩니다 :)</p>
                    </form>
                </v-layout>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="teal darken-1" text @click.native="confirm($event)">확인!</v-btn>
                <v-btn color="pink" text @click.native="cancel($event)">취소</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import axios from 'axios'
import { mapMutations, mapState } from 'vuex'

export default {
    name: 'BookingDialogue',
    props: {
        concertNo: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
            phoneNo: '',
            name: '',
            numOfVisitors: 0,
            message: '',
            bookingDialog: false
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile', 'notBookedYet', 'concert']),
        ...mapMutations(['handleConcertBook', 'handleUserLogin'])
    },
    methods: {
        confirm() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                            if(!/^[가-힣|a-z|A-Z|0-9|?'#!$|/\s/g]+$/.test(this.name)) {
                                alert('올바른 이름를 입력해주세요. :)');
                            } else if(!/^[0-1]{3}[0-9]{3,4}[0-9]{4}/.test(this.phoneNo)) {
                                alert('올바른 휴대번호를 입력해주세요!');
                            } else if(this.numOfVisitors > 6 || this.numOfVisitors < 1) {
                                alert('함께 예약 할 수 있는 인원은 최소 1명 이상 6명 이하입니다!');
                            } else {
                                
                                const memberNo = this.userProfile.memberNo;
                                const concertNo = this.concertNo;
                                const name = this.name;
                                const concertName = this.concert.concertName;
                                const phoneNumber = this.phoneNo;
                                const numOfVisitors = this.numOfVisitors;
                                const message = this.message;

                                axios.post('http://localhost:8888/concert/makeBooking', { memberNo, concertNo, name, concertName, phoneNumber, numOfVisitors, message })
                                    .then(() => {
                                        alert('예약 정보가 전송되었습니다 :) 이용해주셔서 감사합니다.');
                                        this.$store.commit('handleConcertBook', numOfVisitors);
                                        this.cancel();
                                    })
                                    .catch(err => {
                                        console.log(err);
                                        alert('잠시 후에 다시 시도해주세요!');
                                    })
                                this.bookingDialog = false;
                            }
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                        }
                    })
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        cancel() {
            this.bookingDialog = false;
            this.phoneNumber = '';
            this.name = '';
            this.numOfVisitors = '';
            this.message =  '';
        },
        cancelBook() {
            alert('예약 취소는 마이페이지의 예약 취소에서 진행해주세요!');
        }
    }
}
</script>

<style>

#message {
    font-size: 12px;
    color: white;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
}

</style>
