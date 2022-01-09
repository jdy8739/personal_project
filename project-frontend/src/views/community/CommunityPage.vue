<template>
    <div align="center" class="main-bg grey darken-4">
        <h3 class="topBar">COMMUNITY COMMENT</h3>
        <p class="description mr-5">MUSIC GHUETTO 이용자들의 공연 관람 후기입니다. 참고하세요. :)</p>
        <community-board-list :boards="boards"/>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import CommunityBoardList from '@/components/communityBoard/CommunityBoardList'

export default {
    name: 'CommunityPage',
    components: {
        CommunityBoardList
    },
    methods: {
        ...mapActions(['fetchBoardList'])
    },
    computed: {
        ...mapState(['isLoggedIn', 'boards'])
    },
    mounted() {
        this.fetchBoardList()

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        } 
    }
}
</script>
     