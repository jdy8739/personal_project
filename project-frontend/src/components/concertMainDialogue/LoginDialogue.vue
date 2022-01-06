<template>
    <v-dialog v-model="loginDialog" persistent max-width="290">
        <template v-slot:activator="{ on }">
            <p class="flat waves-effect waves-red topBarText" v-on="on" style="margin-top: -5px;">
                lOG IN
            </p>
        </template>

        <v-card class="grey darken-3">

            <v-card-title class="headLine">
                <p class="headline" style="margin-bottom: -5px;">
                    Login
                </p>
            </v-card-title>

            <v-card-text>
                <v-container grid-list-md style="width: 90%;">
                    <v-text-field label="Email" type="text" required v-model="userInfo.id" dark color="teal"/>
                    <v-text-field label="Password" type="password" required v-model="userInfo.password" dark color="red" @keydown.enter="login"/>
                </v-container>
            </v-card-text>

            <v-card-actions>

                <v-spacer></v-spacer>
                <v-btn color="error" text @click.native="login">
                    로그인
                </v-btn>
                <v-btn color="error" text @click.native="cancel">
                    취소
                </v-btn>
            </v-card-actions>

        </v-card>
    </v-dialog>
    
</template>

<script>
import cookies from 'vue-cookies'
import Vue from 'vue'

import axios from 'axios'
import { mapActions, mapMutations, mapState } from 'vuex'

Vue.use(cookies)

export default {
    name: 'LoginDialogue',
    data() {
        return {
            userInfo: { id: '', password: "" },
            loginDialog: false,
        }
    },
    computed: {
           ...mapState(['isLoggedIn', 'userIdentity', 'userProfile'])
    },
    methods: {
        ...mapActions(['fetchTaste']),
        ...mapMutations(['handleUserLogin']),

        cancel() {
            this.loginDialog = false;
            this.userInfo.id = '';
            this.userInfo.password = '';
        },

        login() {
            
            if(this.userInfo.id == '' || this.userInfo.password == '') {
                
                alert('email 또는 비밀번호를 입력해주세요!');
         
            } else  {

                const { id, password } = this.userInfo;

                axios.post('http://localhost:8888/member/login', { id, password })
                    .then(res => {
                        if(res.data.id.length > 2) {
                            alert(res.data.id + '으로 로그인되었습니다!')

                            this.$cookies.set("CurrentUser", res.data, '120m') 
                            this.handleUserLogin(res.data);
                        } else {
                            alert('로그인 실패!');
                        }
                    })
                    .catch(err => {
                        console.log(err.response.data.message + '로그인 실패!');
                    });
                this.cancel();
            } 
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');

.headline {
    font-size: 30px;
    color: white;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    margin-right: 10px;
}

</style>
