<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">MEMBER DELETE</p>
        <p class="description">정말 이 회원의 정보를 삭제할까요?
        한번 삭제한 회원정보는 복구할 수 없습니다! 신중하게 삭제해주세요.</p>
        <div>
            <v-btn class="ma-2" text @click.native="btn_delete($event)" color="error lighten-4">
                삭제
            </v-btn>
            <v-btn class="ma-2" text @click.native="btn_cancel($event)" color="error lighten-2">
                취소
            </v-btn>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
    name: 'MemberDeletePage',
    props: {
        memberNo: { 
            type: String,
            required: true
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userIdentity'])
    },
    methods: {
        btn_delete() {
            axios.delete(`http://localhost:8888/member/delete/${ this.memberNo }`)
                .then(() => {
                    this.$store.commit('deleteMember', this.memberNo);
                    alert('정상적으로 회원이 삭제되었습니다.');
                    this.goBack();
                })
                .catch(alert('잠시 후에 다시 시도해주세요.'));
        },
        btn_cancel() {
            this.goBack();
        },
        goBack() {
            this.$router.push({ name: 'MemberListPage' });
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    },
    watch: {
        userIdentity(a) {
            if(a !== 'manager') {
                alert('권한이 없는 페이지입니다.');
                this.goBack();
            }
        }
    }
}
</script>
