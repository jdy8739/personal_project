<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MY REQUEST LIST</p>
        <p class="description">회원님이 요청한 공연의 리스트 정보입니다. 관리자의 공연 게시 여부를 확인하세요.</p>
        <v-container class="description mt-12 request-box">
            <div v-for="(request, i) in myRequestList" :key="i" class="request-card">
                <div class="request-card-inner" :class="{ approved: request.approvedOrNot }" @click="toDetailPage(request.concertRequestNo)">
                    <div class="thumbnail">
                        <v-icon v-if="request.approvedOrNot" class="approved-card">checked</v-icon>
                    </div>
                    <p class="mt-5">{{ request.concertName }}</p>
                    <p>{{ request.concertArtist }}</p>
                    <p>{{ request.concertVenue }}</p>
                    <p>{{ request.concertPrice }}</p>
                    <p>{{ request.regDate }}</p>
                </div>
            </div>
        </v-container>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

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
        ...mapState(['myRequestList'])
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
    }

    .request-card-inner:hover {
        background-color: rgb(58, 35, 35);
    }

    .approved:hover {
        background-color: rgb(18, 51, 43);
    }

    .thumbnail {
        width: 100%;
        height: 125px;
        background-color: rgb(49, 49, 49);
        position: relative;
    }

    .approved-card {
        font-size: 75px;
        color: aliceblue;
        position: absolute;
        top: 50%;
        left: 85%;
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
