<template>
    <div align="center" class="main-bg grey darken-4">
        <h3 class="topBar">COMMUNITY COMMENT</h3>
        <p class="description mr-5">MUSIC GHUETTO 이용자들의 공연 관람 후기입니다. 참고하세요. :)</p>
        <community-board-read v-if="board" :board="board" class="board-form"/>
        <p class="description" v-else-if="!board">해당 게시글의 정보를 불러오는 중입니다. 조금만 기다려주세요. :)</p>
        <reply-section class="board-form"/>
    </div>
</template>

<script>
//import EventBus from '@/eventBus.js'
import { mapActions, mapState } from 'vuex'
import CommunityBoardRead from '@/components/communityBoard/CommunityBoardRead'
import ReplySection from '@/components/communityBoard/ReplySection'

export default {
    name: 'CommunityReadPage',
    components: {
        CommunityBoardRead,
        ReplySection
    },
    props: {
        boardNo: {
            type: String,
            required: true
        } 
    },
    data() {
        return {
            
        }
    },
    mounted() {
        this.fetchBoard(this.boardNo);

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    },
    computed: {
        ...mapState(['userProfile', 'isLoggedIn', 'board'])
    },
    methods: {
        ...mapActions(['fetchBoard'])
    }
}
</script>

<style>

.board-form {
    min-width: 1000px;
}

</style>