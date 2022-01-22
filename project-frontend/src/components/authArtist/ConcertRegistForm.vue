<template>
    <div class="mt-10 register-form">
        <form @submit.prevent="onSubmit">
            <div class="input-row">
                <div class="input-box">
                    <label class="float-left ml-12">공연명</label>
                    <br>
                    <input placeholder="공연이름을 입력해주세요." class="concert-input" v-model="concertName" required/>
                </div>
                <div class="input-box">
                    <label class="float-left ml-12">아티스트</label>
                    <br>
                    <input placeholder="어떤 아티스트가 출연하나요." class="concert-input" v-model="concertArtist" required/>
                </div>
            </div>
            <div class="input-row mt-5">
                <div>
                    <v-select
                        dark
                        v-model="city"
                        :items="cityList"
                        menu-props="auto"
                        hide-details
                        label="공연 위치"
                        outlined
                        color="teal"
                        style="padding: 20px;"
                    ></v-select>
                </div>
            </div>
            <div class="input-row">
                <div class="input-box">
                    <label class="float-left ml-12">공연장 주소(상세)</label>
                    <br>
                    <input placeholder="공연장 주소를 입력해주세요." class="concert-input" v-model="place" required/>
                </div>
                <div class="input-capacity">
                    <label class="float-left ml-12">수용 인원</label>
                    <br>
                    <input placeholder="수용 인원" class="concert-input" v-model="venueCapacity" required/>
                </div>
            </div>
            <div class="input-genre mt-5">
                <div>
                    <v-select
                        dark
                        v-model="concertGenre"
                        :items="genres"
                        menu-props="auto"
                        hide-details
                        label="공연 장르"
                        outlined
                        color="teal"
                        style="padding: 20px;"
                        multiple
                    ></v-select>
                </div>
            </div>
            <div style="clear: both;"></div>
            <div class="input-row">
                <div class="input-box-date">
                    <label class="float-left ml-12">공연 날자</label>
                    <br>
                    <input type="date" class="concert-input" v-model="concertDate" required/>
                </div>
                <div class="input-box-date">
                    <label class="float-left ml-12">시간</label>
                    <br>
                    <input type="time" class="concert-input" v-model="concertTime" required/>
                </div>
                <div class="input-box-price">
                    <label class="float-left ml-12">입장료</label>
                    <br>
                    <input placeholder="입장료" class="concert-input" v-model="concertPrice" required/>
                </div>
            </div>
            <div class="mt-10" style="width: 94.5%">
                <v-textarea outlined color="teal" label="공연 설명" height="200px" dark v-model="concertInfo"/>
            </div>
            <div style="margin-top: 70px;"></div>
            <div class="mt-10 buttons">
                <button class="form-btn submit" type="submit">제출</button>
                <button class="form-btn cancel" @click="cancel">취소</button>
            </div>
        </form>
    </div>
</template>

<script>
import { mapState } from 'vuex';
export default {
    data() {
        return {
            concertName: '',
            concertArtist: '',
            city: '',
            place: '',
            venueCapacity: 0,
            concertGenre: [],
            concertPrice: 0,
            concertDate: '',
            concertTime: '00:00',
            concertInfo: '',

            files: '',

            cityList: [
                '강남', '홍대', '서울', '이태원',
                '부산', '대구', '속초', '그 외'
            ],
            genres: [
                'rock', 'hip-hop', 'jazz', 'acoustic', 'edm'
            ],
        }
    },
    computed: {
        ...mapState(['userProfile'])
    },
    methods: {
        onSubmit() {
            if(!parseInt(this.concertPrice) > 0) {
                alert(this.makeMsg('입장료'));
                return;
            }

            const memberNo = this.userProfile.memberNo;
            const memberId = this.userProfile.id;
            const concertVenue = this.city + ' ' + this.place;
            const concertGenre = JSON.stringify(this.concertGenre);
            const { concertName, concertArtist, venueCapacity, concertDate, concertTime, concertInfo, concertPrice } = this;

            this.$emit('submit', 
            { memberNo, memberId, concertName, concertArtist, concertVenue, venueCapacity, concertGenre, concertDate, concertTime, concertInfo, concertPrice });
        },
        cancel() {
            this.$emit('goPreStep');
        },
        makeMsg(subject) {
            return `${ subject } 항목을 다시 체크해주세요!`;
        }
    }
}
</script>

<style>
    .register-form {
        border: 1px solid rgb(77, 77, 77);
        border-radius: 12px;
        width: 60%;
        min-width: 320px;
        max-width: 1250px;
        padding: 40px 18px;
    }

    .input-row {
        display: flex; 
        flex-wrap: wrap;
    }

    .concert-input {
        width: 90%;
        padding: 12px;
        background-color: rgb(44, 44, 44);
    }

    .form-btn {
        width: 45%;
        height: 40px;
        border-radius: 5px;
        transition: all 1s;
    }

    .submit {
        background-color: teal;
    }

    .cancel {
        background-color: tomato;
    }

    .form-btn:hover {
        background-color: rgb(35, 55, 73);
    }
</style>

<style scoped>
    input {
        border: 1px solid teal;
        border-radius: 5px;
        transition: all 500ms;
    }

    input:hover {
        border: 1px solid rgb(255, 121, 121);
        background-color: rgb(24, 24, 24);
    }

    .input-box {
        width: 50%;
    }

    .input-capacity {
        width: 20%;
    }

    .input-genre {
        width: 25%; 
        float: left;
    }

    .input-box-date {
        width: 25%;
        margin-left: 28px;
    }

    .input-box-date>input {
        padding-left: 15px;
    }

    .input-box-price {
        width: 18%;
        margin-left: 20px;
    }

    .buttons {
        display: flex;
        justify-content: space-around;
    }

    @media screen and (max-width: 920px) {
        .input-box {
            width: 100%;
            margin-top: 10px;
        }

        .input-box-date {
            width: 82%;
            margin-left: 20px;
        }

        .input-capacity {
            width: 100%;
            margin-top: 10px;
        }

        .input-genre {
            width: 100%;
        }

        .input-box-price {
            width: 100%;
            margin-left: 0;
        }
    }
</style>