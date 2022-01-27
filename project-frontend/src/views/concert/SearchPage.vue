<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">SEARCH RESEARCH</p>
        
        <div class="mt-15 description">
            <p><span class="keyword">{{ keyword }}</span>에 대한 검색결과입니다.</p>
            <hr>
            <div v-for="(result, i) in searchedResults" :key="i">
                <p class="res-list" @click="toDetailPage(result.concertNo)">{{ result.concertName }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
    data() {
        return {
            keyword: ''
        }
    },
    methods: {
        ...mapActions(['fetchSearchedResults']),

        toDetailPage(concertNo) {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: parseInt(concertNo) }
            });
        }
    },
    computed: {
        ...mapState(['searchedResults'])
    },
    mounted() {
        this.keyword = this.$route.query.search;
        this.fetchSearchedResults(this.keyword);

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>

<style scoped>
.keyword {
    color: pink;
    font-weight: bold;
    font-size: 18px;
}

.res-list {
    padding: 8px;
    font-size: 13px;
    cursor: pointer;
    transition: all 0.5s;
}

.res-list:first-child {
    margin-top: 10px;
}

.res-list:hover {
    color: palevioletred;
    background-color: rgb(52, 87, 88);
}
</style>
