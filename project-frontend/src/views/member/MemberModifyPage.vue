<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">INFO MODUFY</p>
        <p class="description">원하시는 정보를 수정하세요.</p>
        <signup class="signup-form" @submit="onSubmit"/>
        <p class="description">이용약관 | 개인정보처리방침 | 책임의 한계와 법적고지 | 회원정보 고객센터<br>
        <br>MUSIC GHUETTO Copyright &copy; MUSIC GHUETTO</p>
        <br/>
    </div>
</template>

<script>

import axios from 'axios'
import Signup from '@/components/memberPage/Signup';
import { mapState } from 'vuex';

export default {
    name: 'MemberModifyPage',
    components: {
        Signup
    },
    computed: {
        ...mapState(['userProfile'])
    },
    methods: {
        onSubmit(payload) {
            const memberNo = this.userProfile.memberNo;
            const location = JSON.stringify(payload.location);
            const { id, password, name, birthDay, identity, phoneNo } = payload;

            axios.put('http://localhost:8888/member/modify', { memberNo, id, password, name, location, birthDay, identity, phoneNo })
                .then(res => {
                    if(res.data) {
                        alert('회원 정보 수정이 완료되었습니다!');
                        this.$router.push({
                            name: 'MyProfilePage'
                        });

                    } else if(!res.data) {
                        alert('이미 존재하는 ID입니다!' + res);
                    }
                })
                .catch(res => { alert(res.response.data.message); });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);

        } else history.goBack();
    }
}
</script>
