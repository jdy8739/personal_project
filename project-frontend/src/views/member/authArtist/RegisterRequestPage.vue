<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT REQUEST</p>
        <p class="description">
            관리자에게 공연 정보의 등록을 요청할 수 있습니다. 회원님의 공연을 MUSIC GUETTO에서 홍보하세요!
        </p>
        <concert-regist-form/>
    </div>
</template>

<script>
// import axios from 'axios'
// import { mapActions, mapState } from 'vuex'
import ConcertRegistForm from '@/components/authArtist/ConcertRegistForm';

export default {
    components: {
        ConcertRegistForm,
    },
    methods: {
    },
        
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>