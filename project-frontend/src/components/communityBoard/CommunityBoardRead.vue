<template>
    <div>
        <v-container style="margin-top: 30px;">
            
            <table style="color: white; width: 600px;">
                <tr>
                    <td class="description" style="width: 100px; color: grey;">글 제목</td>
                    <input disabled v-bind:value="board.title" style="width: 500px; color: white;"/>
                </tr>
                <tr>
                    <td class="description" style="width: 100px; color: grey;">본문</td>
                    <v-textarea disabled v-bind:value="board.content" style="width: 500px; color: white;" auto-grow dark outlined></v-textarea>
                </tr>

                <tr>
                    <td class="description" style="width: 100px; color: red;" @click="addReply">댓글 달기</td>

                    <span>
                        <input style="width: 440px; color: white;" v-model="reply" @keydown.enter="addReply"/>

                        
                        <v-tooltip bottom>
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn class="ma-2" text icon color="red lighten-1" v-bind="attrs" v-on="on" @click="deleteInput">
                                    <v-icon>
                                        clear_all
                                    </v-icon>
                                </v-btn>
                            </template>
                            <span>입력 취소</span>    
                        </v-tooltip>

                    </span>
                </tr>
            </table>

            <div style="margin-top: 80px; padding-left: 400px;">
                <v-btn text color="red" @click="modify" style="font-size: 11px;">
                    수정, 삭제
                </v-btn>
                
                <v-btn text color="red" style="margin-left: 30px; font-size: 11px;" @click="goBack">
                    뒤로가기
                </v-btn>
            </div>

        </v-container>
    </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

export default {
    name: 'CommunityBoardRead',
    props: {
        board: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            reply: '',
            inputComplete: false
        }
    },
    computed: {
        ...mapState(['userProfile', 'isLoggedIn', 'replyList'])
    },
    methods: {
        ...mapActions(['fetchReplyList', 'fetchBoard']),

        deleteInput() {
            this.reply = ''
        },

        modify() {
            if(this.$store.state.isLoggedIn && (this.$store.state.userProfile.id == this.$store.state.board.id ||
            this.$store.state.userProfile.identity == 'admin')) { 

                this.$router.push({
                    name: 'CommunityModifyPage',
                    params: this.board.boardNo
                })

            } else {
                alert('권한이 없습니다!')
            }
        },
        goBack() {
            this.$router.push({
                name: 'CommunityPage'
            })
        },
        addReply() {
            if(this.isLoggedIn) {
                
                 axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(this.reply == '') {
                            alert('내용을 입력해주세요.')
                        
                        } else if(res.data == true) {

                            //this.$store.state.replyList.push({ id: this.$store.state.userProfile.id, content: this.reply })

                            const boardNo = this.board.boardNo
                            const id = this.$store.state.userProfile.id
                            const content = this.reply

                            axios.post('http://localhost:8888/board/addReply', { boardNo, id, content })
                                .then(alert('댓글이 게시되었습니다!'))

                                //this.fetchReplyList(this.board.boardNo) //이거랑..
                                this.reply = ''
                                this.inputComplete = true
            
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!')
                            this.$store.state.isLoggedIn = false
                        } 
                    })           

            } else {
                alert('로그인이 필요한 서비스입니다!')
            }
        }
    },
    updated() {
        //this.fetchReplyList(this.board.boardNo) //요걸로 댓글 업뎃 시 화면에 바로 적용 완료
    },
    watch: {
        inputComplete() {
            setTimeout(() => {
                this.inputComplete = false
                this.fetchReplyList(this.board.boardNo) //update가 아니라` 이걸로 해결???
            }, 500)
        }
    }
}
</script>
