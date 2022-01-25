<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MY REQUEST LIST</p>
        <p class="description">회원님이 요청한 공연의 리스트 정보입니다. 관리자의 공연 게시 여부를 확인하세요.</p>
        <ConcertRequestList :requestList="requestList"/>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';
import ConcertRequestList from '@/components/authArtist/ConcertRequestList';

export default {
    name: 'MyRequestListPage',
    components: {
        ConcertRequestList
    },
    methods: {
        ...mapActions(['fetchMyRequestList'])
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.fetchMyRequestList(userInfo.memberNo);
        }
    },
    computed: {
        ...mapState(['requestList', 'userProfile'])
    }
}
</script>


<style>
.request-box {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.request-card {
    width: 250px;
    padding: 12px;
    max-width: 250px;
    min-width: 250px;
    position: relative;
}

.request-card-inner {
    border: 1px solid rgb(88, 88, 88);
    border-radius: 12px;
    transition: all 500ms;
    overflow: hidden;
    cursor: pointer;
    height: 325px;
    position: relative;
}

.request-card-inner:hover {
    background-color: rgb(58, 35, 35);
}

.thumbnail {
    width: 100%;
    height: 100%;
    opacity: 0.2;
}

.thumbnail-desc {
    position: absolute;
    top: 55%;
    left: 50%;
    transform: translate(-50%, -50%);
}

@media screen and (max-width: 1200px) {
    .request-card {
        width: 33.33%;
    }
}

@media screen and (max-width: 768px) {
    .request-card {
        width: 100%;
    }
}
</style>
