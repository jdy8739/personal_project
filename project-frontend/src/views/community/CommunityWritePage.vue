<template>
    <div align="center" class="main-bg grey darken-4">
        <h3 class="topBar">COMMUNITY COMMENT</h3>
        <p class="description mr-5">여러분들의 공연에 대한 기대나 후기를 공유해주세요. :)</p>
        <v-container>
            <v-select
              :items="categories"
              v-model="category"
              attach
              chips
              label="카테고리"
              dark
              style="width: 120px; float: left;"
              color="red"
            ></v-select>
            <div style="clear: both;"></div>
            <form @submit.prevent="onSubmit">
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
                                <v-btn text type="submit" color="red">
                                    제출
                                </v-btn>
                                &emsp;
                                <v-btn text color="red" @click="goBack">
                                    뒤로가기
                                </v-btn>
                            </span>
                        </td>
                    </tr>
                </table>
            </form>
        </v-container>
    </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapActions } from 'vuex'

export default {
    name: 'CommunityWritePage' ,
    data() {
        return {
            title: '',
            content: '',
            categories: [ '후기', '음악', '공연', '건의' ],
            category: ''
        }
    },
    methods: {
        ...mapActions(['fetchBoardList']),

        onSubmit() {
            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {

                            if(this.category == '') {
                                alert('게시글의 카테고리를 설정해주세요!');
                                return false;
                            }
                            
                            const id = this.userProfile.id;
                            const title = this.title;
                            const content = this.content;
                            const category = this.category;
                            
                            axios.post('http://localhost:8888/board/upload', { id, title, content, category })
                                .then(() => {
                                    alert('글이 등록되었습니다 :)');
                                    this.$router.push({
                                        name: 'CommunityPage'
                                    });
                                })
                                .catch(err => { console.log(err); })
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.$store.commit('handleUserLogin');
                        }
                    });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },
        goBack() {
            this.$router.push({
                name: 'CommunityPage'
            });
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile'])
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>