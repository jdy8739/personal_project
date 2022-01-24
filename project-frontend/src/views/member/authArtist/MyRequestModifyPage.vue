<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT REQUEST</p>
        <p class="description">
            공연 요청 등록을 수정하세요.
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
    props: {
        concertRequestNo: Number
    },
    data() {
        return {
            step: 1
        }
    },
    computed: {
        ...mapState(['concertRequest', 'userProfile', 'isLoggedIn'])
    },
    methods: {
        goNext(payload) {
            this.files = payload;
            this.step ++;
        },
        onSubmit(payload) {
            if(this.isLoggedIn) {

                const formData = new FormData();
                const date = Date.now();
                
                formData.append('id', this.userProfile.id);
                formData.append('date', date);
                formData.append('preFolderName', this.concertRequest.folderName);

                Array.from(this.files).forEach((a, i) => {
                    formData.append("concertPic", this.files[i]);
                });

                axios.post('http://localhost:8888/member/concert_register/img_upload', formData)
                    .then(() => {
             
                        const folderName = this.userProfile.id + date;
                        
                        axios.put('http://localhost:8888/member/concert_register/modify', { ...payload, folderName, concertRequestNo: this.concertRequestNo })
                            .then(() => {
                                alert('성공적으로 공연 요청이 수정되었습니다. :)');
                                this.$router.push({ name: 'MainPage' });
                            })
                            .catch(err => { 
                                console.log(err);
                                alert('잠시 후에 다시 시도해주세요!');
                            });
                    })
                    .catch(err => console.log(err));
            }
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
            if(a === 'notExist') {
                this.$router.push({ name: 'ExceptionPage' });
                
            } else if(this.userProfile.id !== a.memberId) {
                alert('접근 권한이 없습니다!');
                this.$router.push({ name: 'MainPage' });
            }
        }
    }
}
</script>