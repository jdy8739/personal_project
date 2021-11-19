<template>

    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;"> 
        
        <h3 class="topBar">COMMUNITY COMMENT</h3>

        <p class="description" style="margin-right: 20px;">MUSIC GHUETTO 이용자들의 공연 관람 후기입니다. 참고하세요. :)</p>

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
    created() {
        this.fetchBoardList()
    },
    mounted() {
        this.fetchBoardList()

        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity
        }
    }
}
</script>
     