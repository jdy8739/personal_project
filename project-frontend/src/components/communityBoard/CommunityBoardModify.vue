<template>
    <div class="mt-10">
        <v-container>
            <v-select
              v-model="category"
              :items="categories"
              attach
              chips
              label="카테고리"
              dark
              style="width: 120px; float: left;"
              color="red"
            ></v-select>
            <table style="color: white;">
                <tr>
                    <td class="description" style="color: grey;">글 제목</td>
                </tr>
                <tr>
                    <td>
                        <input required v-model="title" style="color: white;"/>
                    </td>
                </tr>
                <tr>
                    <td class="description" style="color: grey;">본문</td>
                </tr>
                <tr>
                    <td>
                        <v-textarea required v-model="content" auto-grow dark color="red" clearable/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="float: right;">

                            <v-btn text color="red" @click="modify" style="font-size: 11px;">
                                수정
                            </v-btn>

                            <v-btn text color="red" @click="deletee" style="margin-left: 10px; font-size: 11px;">
                                삭제
                            </v-btn>
                            
                            <v-btn text color="red" style="margin-left: 10px; font-size: 11px;" @click="goBack">
                                뒤로가기
                            </v-btn>
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
    name: 'CommunityBoardModify',
    props: {
        board: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            title: this.board.title,
            content: this.board.content,

            categories: [ '후기', '음악', '공연', '건의' ],
            category: this.board.category
        }
    },
    computed: {
        ...mapState(['isLoggedIn'])
    },
    methods: {
        ...mapActions(['fetchBoardList']),

        modify() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {

                            if(this.category == '') {
                                alert('게시글의 카테고리를 설정해주세요!');
                                return false;
                            }

                            const boardNo = this.board.boardNo;
                            const title = this.title;
                            const content = this.content;
                            const category = this.category;
                            
                            axios.put('http://localhost:8888/board/modify', { boardNo, title, content, category })
                                .then(() => {
                                    alert('수정이 완료되었습니다!');

                                    this.$router.push({
                                        name: 'CommunityPage'
                                    });
                                })
                                .catch(() => {
                                    alert('게시글 수정 실패. 잠시 후에 다시 시도해주세요!');
                                });
                        
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.$store.commit('handleUserLogin');
                        }
                    })
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        deletee() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {

                            const boardNo = this.board.boardNo;
                            
                            axios.delete(`http://localhost:8888/board/delete/${ boardNo }`)
                                .then(() => {
                                    alert('게시글이 삭제되었습니다!');
                                    this.$router.push({
                                        name: 'CommunityPage'
                                    });
                                })
                                .catch(err => {
                                    console.log(err);
                                })

                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.$store.commit('handleUserLogin');
                        }
                    })
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        goBack() {
            this.$router.push({
                name: 'CommunityReadPage'
            });
        }
    }
}
</script>
