<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">INDIE NEWS THIS WEEK</p>
        <p class="description">오늘의 최신 인디음악 관련 블로그를 확인해보세요 :)</p>
        <v-container class="mt-10">
            <table>
                <tr>
                    <th class="text-left description" width="15%;">No.</th>
                    <th class="text-left description" width="50%;">제목</th>
                    <td>
                        <v-btn class="ma-1" small color="yellow" plain @click="update($event)">
                            인디밴드
                        </v-btn>
                    </td>
                    <td>
                        <v-btn class="ma-1" small color="pink" plain @click="update($event)">
                            홍대
                        </v-btn>
                    </td>
                    <td>
                        <v-btn class="ma-1" small color="orange" plain @click="update($event)">
                            공연
                        </v-btn>
                    </td>
                    <td>
                        <v-btn class="ma-1" small color="purple" plain @click="update($event)">
                            콘서트
                        </v-btn>
                    </td>
                    <td>
                        <v-btn class="ma-1" small color="teal" plain @click="update($event)">
                            래퍼
                        </v-btn>
                    </td>
                    <td>
                        <input placeholder="키워드 입력" type="text" required dark @keydown.enter="update($event)" style="width: 180px;"/>
                    </td>
                </tr>
                <tr v-for="news of newsList" :key="news.title">
                    <td style="color: pink">{{ news.newsNo }}</td>
                    <td colspan="8" class="description"><a v-bind:href="news.address" target='_blank'>{{ news.title }}</a></td> 
                </tr>
            </table>
        </v-container>
    </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'IndieNewsCrawlerPage',
    data() {
        return {

        }
    },
    computed: {
        ...mapState({ newsList: state => state.newsList }, ['isLoggedIn'])
    },
    methods: {
        update(e) {
            const keyword = e.target.innerText || e.target.value;
            this.$store.dispatch('crawlFind', keyword);
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);
        }
    }
}
</script>
