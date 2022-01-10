<template>
    <v-dialog v-model="checkDialog" persistent max-width="290">
        <template v-slot:activator="{ on }">
            <div v-on="on">
                <span>
                    <v-btn text color="red" @click="checkWord($event)">
                        수정
                    </v-btn>
                    &emsp;
                    <v-btn text color="red" @click="checkWord($event)">
                        탈퇴
                    </v-btn>
                </span>
            </div>
        </template>
        <v-card class="grey darken-3" >
            <v-card-title class="headLine">
                <p class="footerText">잠깐만요!</p>
                <p class="description">변경이나 탈퇴 전 본인 확인을 위해 비밀번호를 다시한번 더 입력해주세요!</p>
            </v-card-title>
            <v-card-text>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-text-field label="Password" type="password" required v-model="password" dark/>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field label="Password 재입력" type="password" required v-model="passwordCheck" dark/>
                    </v-flex>
                </v-layout>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" text @click.native="confirm">
                    확인
                </v-btn>
                <v-btn color="error" text @click.native="cancel">
                    취소
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
    name: 'CheckDialogue',
    data() {
        return {
            checkDialog: false,
            password: '',
            passwordCheck: '',
            checkedWord: ''
        }
    },
    computed: {
        ...mapState(['userProfile'])
    },
    methods: {
        checkWord(event) {
            this.checkedWord = event.target.innerText;
        },
        confirm() {
            if(this.password === this.passwordCheck) {

                const id = this.userProfile.id;
                const password = this.password;

                axios.post('http://localhost:8888/member/checkPassword', { id, password })
                    .then(res => {
                        if(res.data) {
                            this.checkDialog = false;
                            
                            if(this.checkedWord === '수정') {
                                this.$router.push({
                                    name: 'MemberModifyPage'
                                });
                            } else if(this.checkedWord === '탈퇴') {
                                this.$router.push({
                                    name: 'MemberWithdrawalPage',
                                    params: { memberNo: this.userProfile.memberNo.toString() }
                                });
                            }
                        } else alert('올바른 비밀번호가 아닙니다!');
                    })
                    .catch(err => { console.log(err); });
            } else {
                alert('비밀번호와 비밀번호 확인이 일치하지 않습니다!');
            }
        },
        cancel() {
            this.password = ''
            this.passwordCheck = ''

            this.checkDialog = false
        }
    }
}
</script>

<style scoped>
input {
    width: 90%;
}
</style>