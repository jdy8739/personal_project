<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MEMBER LIST</p>
        <p class="description">현재 등록된 회원 정보입니다. 회원을 탈퇴시키려면 해당 회원의 id를 클릭하세요.</p>
        <member-list :members="members" @delMember="onDelMember"/>
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
        ...mapState(['members', 'isLoggedIn', 'userIdentity'])
    },
    methods: {
        ...mapActions(['fetchMemberList']),

        onDelMember(payload) {
            this.$router.push({ name: 'MemberDeletePage', params: { memberNo: payload } });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
            this.fetchMemberList();
        }
    },
    watch: {
        members(a) {
            if(a !== 'manager') {
                alert('권한이 없는 페이지입니다.');
                this.$router.push({ name: 'MainPage' });
            }
        }
    }
}
</script>