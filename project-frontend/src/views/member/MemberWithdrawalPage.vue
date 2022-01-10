<template>
    <div align="center" class="main-bg grey darken-4">
        <h3 class="topBar">WITHDRAWAL</h3>
        <p class="description mr-5">정말로 탈퇴하시나요? 탈퇴하시면 기록된 모든 정보가 사라집니다!</p>
        <div class="mt-10 mr-5">
            <v-btn class="btn-flat red-text waves-effect waves-teal" text="text" @click.native="withdraw($event)">
                탈퇴
            </v-btn>
            &emsp;
            <v-btn class="btn-flat red-text waves-effect waves-teal" text="text" @click.native="cancel($event)">
                취소
            </v-btn>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapMutations, mapState } from 'vuex'

export default {
    name: 'MemberWithdrawalPage',
    props: {
        memberNo: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            
        }
    },
    methods: {
        ...mapMutations(['handleUserLogin']),

        withdraw() {
            axios.delete(`http://localhost:8888/member/delete/${ this.userProfile.memberNo }`)
                .then(() => {
                    alert('탈퇴처리 완료되었습니다!');

                    this.$cookies.remove("CurrentUser");
                    this.handleUserLogin();
            
                    this.$router.push({
                        name: 'MainPage'
                    });
                })
                .catch(err => {
                    alert('잠시 후에 다시 시도해주세요!');
                    console.log(err);
                });
        },
        cancel() {
            this.$router.push({
                name: 'MyProfilePage'
            });
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile'])
    },
    mounted() {
        if(!this.memberNo) {
            alert('정상적인 경로가 아닙니다.');
            history.go(-1);
        }

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>
