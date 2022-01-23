<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT REQUEST</p>
        <p class="description">
            관리자에게 공연 정보의 등록을 요청할 수 있습니다. 회원님의 공연을 MUSIC GUETTO에서 홍보하세요!
        </p>
        <concert-pic-regi-form @toNextStep="goNext" v-if="step === 1"/>
        <concert-regist-form @submit="onSubmit" @goPreStep="step --" v-if="step === 2"/>
    </div>
</template>

<script>
import axios from 'axios';
import ConcertRegistForm from '@/components/authArtist/ConcertRegistForm';
import ConcertPicRegiForm from '@/components/authArtist/ConcertPicRegiForm';
import { mapState } from 'vuex';

export default {
    components: {
        ConcertRegistForm,
        ConcertPicRegiForm
    },
    data() {
        return {
            step: 1,
            files: '',
            imgUploadSuccess: false
        }
    },
    computed: {
        ...mapState(['isLoggedIn'])
    },
    methods: {
        goNext(payload) {
            this.files = payload;
            this.step ++;
        },
        onSubmit(payload) {
            
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/concert_register/img_upload')
                    .then(() => {
                        this.imgUploadSuccess = true;
                    })
                    .catch(err => console.log(err));

                if(this.imgUploadSuccess) {
                    axios.post('http://localhost:8888/member/concert_register/request', payload)
                        .then(() => {
                            alert('성공적으로 공연 요청이 등록되었습니다. :)');
                            this.$router.push({ name: 'MainPage' });
                        })
                        .catch(err => { 
                            console.log(err);
                            alert('잠시 후에 다시 시도해주세요!')
                        });
                }
            }
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>