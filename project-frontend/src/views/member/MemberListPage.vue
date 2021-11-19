<template>
    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;">

        <h3 class="topBar" style="margin-top: 0px; padding-top: 30px;">MEMBER LIST</h3>
        <p class="description" style="margin-right: 30px;">현재 등록된 회원 정보입니다.  회원을 탈퇴시키려면 해당 회원의 id를 클릭하세요.</p>

        <member-list :members="members"/>
    </div>
</template>

<script>
import MemberList from '@/components/memberPage/MemberList'
import { mapActions, mapState } from 'vuex'

export default {
    name: 'MemberListPage',
    data() {
        return {
            testString: 'fire'
        }
    },
    components: {
        MemberList
    },
    computed: {
        ...mapState(['members', 'isLoggedIn'])
    },
    methods: {
        ...mapActions(['fetchMemberList'])
    },
    mounted() {
        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity

            this.fetchMemberList()
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');

.memberListTitle {
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 39px;
}

</style>