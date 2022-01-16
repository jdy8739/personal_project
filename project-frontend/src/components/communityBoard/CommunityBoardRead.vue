<template>
    <div class="mt-10">
        <v-container>
            <v-select
              :items="categories"
              :value="board.category"
              readonly
              attach
              chips
              label="카테고리"
              dark
              style="width: 120px; float: left;"
              color="red"
            ></v-select>
            <table style="color: white; clear: both;">
                <tr>
                    <td class="description" style="color: grey;">글 제목</td>
                </tr>
                <tr>
                    <td>
                        <input disabled v-bind:value="board.title" style="color: white;"/>
                    </td>
                </tr>
                <tr>
                    <td class="description" style="color: grey;">본문</td>
                </tr>
                <tr>
                    <td>
                        <v-textarea disabled v-bind:value="board.content" style="color: white;" auto-grow dark outlined/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="float-right">
                            <v-btn text color="red" @click="modify">
                                수정, 삭제
                            </v-btn>
                            <v-btn text color="red" @click="goBack">
                                뒤로가기
                            </v-btn>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p class="description" @click="addReply">댓글 달기</p>
                        <span>
                            <input type="text" v-model="reply" @keydown.enter="addReply" style="width: 92%;" />
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn class="ma-2" text icon color="red lighten-1" v-bind="attrs" v-on="on" @click="deleteInput">
                                        <v-icon>clear_all</v-icon>
                                    </v-btn>
                                </template>
                                <span>입력 취소</span>   
                            </v-tooltip>
                        </span>
                    </td>
                </tr>
            </table>
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
            categories: [ '후기', '음악', '공연', '건의' ]
        }
    },
    computed: {
        ...mapState(['userProfile', 'isLoggedIn'])
    },
    methods: {
        ...mapActions(['fetchBoard']),

        deleteInput() {
            this.reply = '';
        },

        modify() {
            if(this.isLoggedIn && (this.userProfile.id == this.board.id || this.userProfile.identity == 'admin')) { 

                this.$router.push({
                    name: 'CommunityModifyPage',
                    params: this.board.boardNo
                });

            } else {
                alert('권한이 없습니다!');
            }
        },
        goBack() {
            this.$router.push({
                name: 'CommunityPage'
            });
        },
        addReply() {
            if(this.isLoggedIn) {
                
                 axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(this.reply == '') {
                            alert('내용을 입력해주세요.');
                        
                        } else if(res.data) {

                            const boardNo = this.board.boardNo;
                            const id = this.userProfile.id;
                            const content = this.reply;

                            axios.post('http://localhost:8888/board/addReply', { boardNo, id, content })
                                .then(() => {
                                    setTimeout(() => {
                                        this.fetchBoard(boardNo);
                                    }, 100);
                                    this.reply = '';
                                });
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.$store.commit('handleUserLogin');
                        } 
                    })           
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        }
    }
}
</script>
