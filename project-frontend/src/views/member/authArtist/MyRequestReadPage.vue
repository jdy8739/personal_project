<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT REQUEST</p>
        <p class="description" >회원님이 작성한 공연 게시 요청 정보입니다. 거부되어도 재요청을 보낼 수 있습니다. :)</p>
        <div class="modal-frame" :class="{ hide: !showModal }">
            <div class="modal-bg"></div>
            <div class="modal-content">
                <p class="mt-5 description">정말 해당 공연 요청을 삭제할까요?</p>
                <v-btn class="btn" dark @click="confirmDelete">네</v-btn>
                &emsp;
                <v-btn class="btn" dark @click="cancelDelete">아니오</v-btn>
            </div>
        </div>
        <div class="img-section">
            <div v-for="i in 2" :key="i" class="registered-img">
                <img :src="require( `../../../../../project-backend/images/registered_pics/${ concertRequest.folderName }/${ concertRequest.memberId }${ i }.jpg` )"/>
            </div>
        </div>
        <v-container class="shortened">
            <div class="footerText mt-5">
                <h5>{{ concertRequest.approvedOrNot ? '게시 승인된 공연' : '게시 승인 대기 중' }}</h5>
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
                <v-btn class="btn" @click="modifyRequerst" dark v-if="userIdentity !== 'manager'">
                    수정
                </v-btn>
                <v-btn class="btn" @click="approveRegister" dark v-if="userIdentity === 'manager'">
                    <span>{{ concertRequest.approvedOrNot ? '게시 보류' : '게시 승인' }}</span>
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
import { mapMutations, mapState } from 'vuex'

export default {
    name: 'MyRequestReadPage',
    props: {
        concertRequestNo: {
            type: Number,
            required: true
        }
    },
    computed: {
        ...mapState(['concertRequest', 'userProfile', 'userIdentity'])
    },
    data() {
        return {
            showModal: false
        }
    },
    methods: {
        ...mapMutations(['handleRequestApprove']),

        deleteRequest() {
            this.showModal = true;
        },
        cancelDelete() {
            this.showModal = false;
        },
        confirmDelete() {
            axios.delete(`http://localhost:8888/member/concert_register/delete/${parseInt(this.concertRequestNo)}`)
                .then(() => {
                    alert('요청이 정상적으로 취소되었습니다.');
                    this.$router.push({ name: 'MyRequestListPage' });
                })
                .catch(err => console.log(err));
        },
        modifyRequerst() {
            this.$router.push({ name: 'MyRequestModifyPage', params: { concertRequestNo: this.concertRequestNo } });
        },
        approveRegister() {
            axios.put(`http://localhost:8888/member/concert_register/approve/${parseInt(this.concertRequestNo)}`)
                .then(() => {
                   this.handleRequestApprove();
                })
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.$store.dispatch('fetchConcertRequest', this.concertRequestNo);
        } else {
            this.$router.push({ name: 'ExceptionPage' });
        }
    },
    watch: {
        concertRequest(a) {
            if(a === "notExist") {
                this.$router.push({ name: 'ExceptionPage' });
            
            } else if(this.userProfile.memberNo !== a.memberNo && this.userIdentity !== 'manager') {
                alert('접근할 수 없는 게시물입니다.');
                this.$router.push({ name: 'MainPage' });
            }
        }
    }
}
</script>

<style>
    .hide {
        display: none;
    }

    .shortened {
        width: 35%;
    }

    .modal-frame {
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
        z-index: 10;
        transition: all 3s;
        box-shadow: 0 0 5px 3px #555;
    }

    .modal-bg {
        background-color: rgb(151, 151, 151, 0.4);
        width: 100%;
        height: 100%;
    }

    .modal-content {
        position: absolute;
        width: 400px;
        height: 120px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -60%);
        background-color: rgb(48, 48, 48);
        border-radius: 12px;
    }
    
    .registered-img {
        display: inline-block;
        padding: 12px;
    }

    .registered-img>img {
        width: 300px;
        height: 390px;
    }
</style>

<style scoped>
    th {
        color: grey;
    }

    td {
        text-align: right;
    }
</style>