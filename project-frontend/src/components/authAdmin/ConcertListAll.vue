<template>
    <div>
        <v-container>
            <v-simple-table dark dense class="description">
                <tr>
                    <td>&emsp;공연 명</td>
                    <td>아티스트</td>
                    <td>장소</td>
                    <td>공연 시간</td>
                    <td>게시 여부&emsp; &emsp;</td>
                </tr>
                <tr v-for="(concert, i) in concertListAll" :key="i">
                    <td>&emsp;{{ concert.concertName }}</td>
                    <td>{{ concert.concertArtist }}</td>
                    <td>{{ concert.concertVenue }}</td>
                    <td>{{ concert.concertDate }} {{ concert.concertTime }}</td>
                    <td>
                        <v-btn class="btn" small @click="postConcert(concert.concertNo)">{{ concert.locked ? '비공개' : '공연 게시됨' }}</v-btn>
                        <v-btn class="btn" small @click="toDetailPage(concert.concertNo)">자세히</v-btn>
                        &emsp;
                    </td>
                </tr>
            </v-simple-table>
        </v-container>
    </div>
</template>

<script>
import axios from 'axios';
import { mapMutations } from 'vuex';
export default {
    props: {
        concertListAll: Array
    },
    methods: {
        ...mapMutations(['handlePostConcert']),

        postConcert(concertNo) {
            axios.put(`http://localhost:8888/concert/postConcert/${ concertNo }`)
                .then(() => {
                    this.handlePostConcert(concertNo);
                })
                .catch(err => console.log(err));
        },
        toDetailPage(concertNo) {
            this.$router.push({ name: 'ConcertDetailPage', params: { concertNo: concertNo } });
        }
    }
}
</script>

<style scoped>
    tr {
        border-bottom: 0.2px solid rgb(65, 65, 65);
        transition: all 0.5s;
    }

    tr:hover {
        color: cadetblue;
    }

    tr:last-child {
        border-bottom: none;
    }

    tr>td:last-child {
        float: right;
    }
</style>