<template>
    <v-container class="description mt-12 request-box">
            <div v-for="(request, i) in requestList" :key="i" class="request-card">
                <div class="badge" v-if="request.approvedOrNot">
                    <v-icon class="approved-sign">checked</v-icon>
                </div>
                <div class="request-card-inner" :class="{ approved: request.approvedOrNot }" @click="toDetailPage(request.concertRequestNo)">
                    <img class="thumbnail" :src="require(`../../../../project-backend/images/registered_pics/${ request.folderName }/${ request.memberId }1.jpg`)"/>
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
</template>

<script>
import { mapState } from 'vuex';
export default {
    name: 'ConcertRequestList',
    props: {
        requestList: Array
    },
    computed: {
        ...mapState(['userProfile'])
    },
    methods: {
        toDetailPage(concertRequestNo) {
            this.$router.push({
                name: 'MyRequestReadPage',
                params: { concertRequestNo: concertRequestNo }
            });
        }
    }
}
</script>

<style scoped>
.badge {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    background-color: rgb(34, 34, 34);
    border: 1px solid gray;
    position: absolute;
    top: -10px;
    right: 40px;
    z-index: 100;
}

.approved:hover {
    background-color: rgb(18, 51, 43);
}

.approved-sign {
    font-size: 55px;
    color: aliceblue;
}

</style>