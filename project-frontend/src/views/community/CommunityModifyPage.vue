<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">COMMUNITY COMMENT</p>
        <p class="description">글을 자유롭게 수정, 삭제하실 수 있습니다. :)</p>
        <community-board-modify v-if="board" :board="board" class="board-form"/>
        <p class="description" v-else-if="!board">해당 게시글의 정보를 불러오는 중입니다. 조금만 기다려주세요. :)</p>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import CommunityBoardModify from '@/components/communityBoard/CommunityBoardModify'

export default {
    name: 'CommunityModifyPage',
    components: {
        CommunityBoardModify
    },
    props: {
        boardNo: {
            type: String,
            required: true
        }
    },
    computed: {
        ...mapState(['board', 'userProfile', 'userIdentity'])
    },
    methods: {
        ...mapActions(['fetchBoard'])
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);

            this.fetchBoard(this.boardNo);
        } else {
            this.$router.push({ name: 'ExceptionPage' });
        }
    },
    watch: {
        board(a) {
            if(a === 'notExist') {
                this.$router.push({ name: 'ExceptionPage' });
                
            } else if(this.userProfile.id !== a.id && this.userIdentity !== 'manager') {
                alert('접근 권한이 없습니다!');
                this.$router.push({ name: 'MainPage' });
            }
        }
    }
}
</script>
