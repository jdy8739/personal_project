<template>

    <div align="center" class="#424242 grey darken-4" style="height: 100%; color: white;"> 
        <h3 class="topBar" style="margin-top: 0px; padding-top: 80px;">COMMUNITY COMMENT</h3>

        <span>
            <p class="description" style="display: inline;">MUSIC GHUETTO 이용자들의 공연 관람 후기입니다. 참고하세요. :)</p>

            <v-btn text color="red" style="margin-left: 20px; font-size: 12px;" @click="goToWrite">

                글 쓰러가기

                <v-icon style="margin-left: 10px;">
                    border_color
                </v-icon>

            </v-btn>
        </span>

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
        ...mapActions(['fetchBoardList']),

        goToWrite() {
            
            if(this.isLoggedIn) {
                this.$router.push({
                    name: 'CommunityWritePage'
                })
            } else {
                alert('로그인이 필요한 서비스입니다!')
            }
        }
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
     