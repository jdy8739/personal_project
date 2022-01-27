<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">CONCERT MANAGE</p>
        <p class="description">현재 등록된 공연 상품 목록입니다.</p>
        <concert-list-all :concertListAll="concertListAll"/>
    </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex';
import ConcertListAll from '@/components/authAdmin/ConcertListAll';

export default {
    components: {
        ConcertListAll
    },
    methods: {
        ...mapMutations(['handleUserLogin']),
        ...mapActions(['fetchConcertListAll'])
    },
    computed: {
        ...mapState(['concertListAll', 'userIdentity'])
    },

    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.handleUserLogin(userInfo);
            this.fetchConcertListAll();
        }
    },
    watch: {
        userIdentity(a) {
            if(a !== 'manager') {
                alert('권한이 없는 페이지입니다!');
                this.$router.push({ name: 'ExceptionPage' });
            }
        }
    },
    beforeDestroy() {
        window.location.reload();
    }
}
</script>