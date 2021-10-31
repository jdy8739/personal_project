<template>
    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;">

        <h3 class="topBar" style="margin-top: 0px; padding-top: 30px;">MEMBER DELETE</h3>

        <p class="description" style="margin-right: 20px;">정말 이 회원의 정보를 삭제할까요?
        한번 삭제한 회원정보는 복구할 수 없습니다! 신중하게 삭제해주세요.</p>

        <br/>
        <br/>

        <div style="width: 77%; float: right;">

            <v-btn class="ma-2" text @click.native="btn_delete($event)" color="error lighten-4" style="font-size: 11px;">
                삭제
            </v-btn>

            <v-btn class="ma-2" text @click.native="btn_cancel($event)" color="error lighten-2" style="font-size: 11px;">
                취소
            </v-btn>

        </div>

    </div>
</template>

<script>
//import EventBus from '@/eventBus.js'
import axios from 'axios'

import { mapState } from 'vuex'

export default {
    name: 'MemberDeletePage',
    props: {
        memberNo: { //이게 왜 안받아질까 --> router에서 components랑 props설정 때문
            type: String,
            required: true
        }
    },
    data() {
        return {
            //memberNo: 0
        }
    },
    computed: {
        ...mapState(['isLoggedIn'])
    },
    methods: {
        btn_delete() {
            console.log('삭제할 멤버 번호' + this.memberNo)

            axios.delete(`http://localhost:8888/member/delete/${ this.memberNo }`)
                .then(() => {
                    alert('삭제되었습니다!')
                    this.$router.push({
                        name: 'MemberListPage'
                    })
                })
        },
        btn_cancel() {
            this.$router.push({
                name: 'MemberListPage'
            })
        }

    },
    created() {
        // EventBus.$on('sendNum', (payload) => {
        //     this.memberNo = payload
        // })
    },
    mounted() {
        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity
        }
    }
}
</script>
