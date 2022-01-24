<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">REQUESTED LIST</p>
        <p class="description">게시 요청된 공연 목록입니다..</p>
        <ConcertRequestList :requestList="requestList"/>
    </div>
</template>

<script>
import ConcertRequestList from '@/components/authArtist/ConcertRequestList';
import { mapActions, mapState } from 'vuex';

export default {
    components: {
        ConcertRequestList
    },
    computed: {
        ...mapState(['requestList', 'userIdentity'])
    },
    methods: {
        ...mapActions(['fetchConcertRequestList'])
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }

        if(this.userIdentity === 'manager') {
            this.fetchConcertRequestList();
        } else this.$router.push({ name: 'ExceptionPage' });
    }
}
</script>

