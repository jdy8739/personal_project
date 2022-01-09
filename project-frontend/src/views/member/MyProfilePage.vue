<template>
    <div align="center" class="main-bg grey darken-4 board-form">
        <p class="topBar">MY PROFILE</p>
        <p class="description mr-5">현재 회원님의 가입 정보입니다.</p>
        <my-profile :member="member" :taste="taste"/>
        <!-- <check-dialogue/> -->
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

//import CheckDialogue from '@/components/memberPage/CheckDialogue';
import MyProfile from '@/components/memberPage/MyProfile';

export default {
    name: 'MyProfilePage',
    components: {
        //CheckDialogue,
        MyProfile
    },
    data() {
        return {
            
        }
    },
    computed: {
        ...mapState(['member', 'taste']),
    },
    
    methods: {
        ...mapActions(['fetchMember', 'fetchTaste'])
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);

            this.fetchMember(userInfo.memberNo);
            this.fetchTaste(userInfo.memberNo); //없애도 될듯
        }
    }
}
</script>
