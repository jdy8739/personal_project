<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MY REQUEST LIST</p>
        <p class="description">회원님이 요청한 공연의 리스트 정보입니다. 관리자의 공연 게시 여부를 확인하세요.</p>
        <v-container class="description mt-12 request-box">
            <div v-for="(request, i) in myRequestList" :key="i" class="request-card">
                <div class="request-card-inner" :class="{ approved: request.approvedOrNot }" @click="toDetailPage(request.concertRequestNo)">
                    <img class="thumbnail" :src="require(`../../../../../project-backend/images/registered_pics/${ request.folderName }/${ userProfile.id }1.jpg`)"/>
                    <v-icon class="approved-sign" v-if="request.approved_or_not">checked</v-icon>
                    <div class="thumbnail-desc">
                        <h5>{{ request.concertName }}</h5>
                        <p>{{ request.concertArtist }}</p>
                        <p>{{ request.concertVenue }}</p>
                        <p>{{ request.concertPrice }}</p>
                        <p>{{ request.regDate }}</p>
                    </div>
                </div>
            </div>
        </v-container>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
    name: 'MyRequestListPage',
    data() {
        return {
            
        }
    },
    methods: {
        ...mapActions(['fetchMyRequestList']),

        toDetailPage(concertRequestNo) {
            this.$router.push({
                name: 'MyRequestReadPage',
                params: { concertRequestNo: concertRequestNo }
            });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.fetchMyRequestList(userInfo.memberNo);
        }
    },
    computed: {
        ...mapState(['myRequestList', 'userProfile'])
    }
}
</script>


<style scoped>

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

    .approved:hover {
        background-color: rgb(18, 51, 43);
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

    .approved-sign {
        font-size: 75px;
        color: aliceblue;
        position: absolute;
        top: 15%;
        left: 85%;
        transform: translate(-50%, -50%);
        text-align: center;
        display: inline-block;
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
