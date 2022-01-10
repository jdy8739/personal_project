<template>
    <div align="center" class="main-bg grey darken-4">
        <h3 class="topBar">SIGN UP</h3>
        <p class="description">MUSIC GHUETTO에 오신것을 환영합니다! 가입하셔서 다양한 공연 정보와 서비스를 사용해보세요. :)</p>
        <signup v-if="!signupSuccess" @submit="onSubmit" class="signup-form"/>
        <v-container v-else-if="signupSuccess">
            <p class="description">가입을 축하드려요 :) 회원님의 취향에 대한 추가 설정을 하시려면 아래 버튼을, 혹은 그렇지 않다면 다음에 할래요 버튼을 눌러주세요!</p>
            <div class="mt-10 mb-10">
                <v-btn text class="btn-flat red-text waves-effect waves-teal" @click="addSetting">
                    추가 설정
                </v-btn>
                &emsp;
                <v-btn text class="btn-flat red-text waves-effect waves-teal" @click="goToMain">
                    다음에 할래요
                </v-btn>
            </div>
        </v-container>
        <p class="description">이용약관 | 개인정보처리방침 | 책임의 한계와 법적고지 | 회원정보 고객센터<br>
        <br>MUSIC GHUETTO Copyright &copy; MUSIC GHUETTO</p>
        <br/>
        <br/>
    </div>
</template>

<script>
import axios from 'axios'
import Signup from '@/components/memberPage/Signup';

export default {
    name: 'SignupPage',
    components: {
        Signup
    },
    data() {
        return {
            signupSuccess: false
        }
    },
    methods: {
        onSubmit(payload) {
            const location = JSON.stringify(payload.location);
            const { id, password, name, birthDay, identity, phoneNo } = payload;

            axios.post('http://localhost:8888/member/signup', { id, password, name, location, birthDay, identity, phoneNo })
                .then(res => {
                    if(res.data) {
                        alert('회원가입이 완료되었습니다!');
                        this.signupSuccess = true;

                    } else if(!res.data) {
                        alert('이미 존재하는 ID입니다!' + res);
                    }
                })
                .catch(res => { alert(res.response.data.message); });
        },
        addSetting() {
            this.$router.push({
                name: 'PreferenceFillInPage',
            });
        },
        goToMain() {
            this.$router.push({
                name: 'MainPage',
            });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            alert('로그 아웃 후 진행해주세요!');
            history.goBack();
        }
    }

}
</script>

<style>

.signup-form {
    width: 30%;
    min-width: 280px;
    max-width: 400px;
}

</style>


