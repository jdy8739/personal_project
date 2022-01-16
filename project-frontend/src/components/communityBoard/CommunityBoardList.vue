<template>
    <div>
        <v-container>
            <v-select
              v-model="chosenCategory"
              :items="category"
              attach
              chips
              label="카테고리"
              dark
              style="width: 120px; float: left;"
              color="red"
            ></v-select>
            <v-btn text color="red" class="right mt-7" @click="goToWrite">
                글 쓰러가기
            </v-btn>
            <div style="clear: both;"></div>
            <v-data-table :headers="headerTitle" dark 
                        :items="filteredBoards()"
                        :items-per-page="10"
                        :search="search"
                        class="elevation-1 mt-5">
                        
                    <template v-slot:item="{ item, index }"> <!-- v-data-table에서 index뽑는 법 -->
                        <tr>
                            <td>{{ index + 1 }}</td>
                            <td @click="handleClick(item.boardNo)">{{ item.title }}</td>
                            <td>{{ item.category }}</td>
                            <td>{{ item.id }}</td>
                            <td>{{ item.regDate }}</td>
                        </tr>
                    </template>
            </v-data-table>
            <div class="search-box mt-10">
                <label>찾을 게시글의 제목 또는 작성자를 검색하세요.
                    <input type="text" placeholder="게시글 검색" v-model="search"/>
                </label>
            </div>
        </v-container>
    </div>
</template>

<script>
//import EventBus from '@/eventBus.js'

export default {
    name: 'CommunityBoard',
    props: {
        boards: {
            type: Array
        }
    },
    data() {
        return {
            headerTitle: [
                { text: 'NO', value: 'boardNo', width: '10%'},
                { text: '제목', value: 'title', width: '50%'},
                { text: '분류', value: 'category', width: '10%'},
                { text: '작성자', value: 'id', width: '20%'},
                { text: '게시일', value: 'regDate', width: '20%'}
            ],
            category: [ '후기', '음악', '공연', '건의', '전체' ],
            chosenCategory: '전체',
            search: ''
        }
    },
    computed: {
        isLoggedIn() {
            return this.$store.state.isLoggedIn;
        }
    },
    methods: {
        goToWrite() {
            if(this.isLoggedIn) {
                this.$router.push({
                    name: 'CommunityWritePage'
                });
            } else {
                alert('로그인이 필요한 서비스입니다!');
            }
        },

        handleClick(boardNo) {
            this.$router.push({
                name:'CommunityReadPage',
                params: { boardNo: boardNo.toString() }
            });
        },
        filteredBoards() {
            var filteredBoards = []
            if(this.chosenCategory == '전체') filteredBoards = this.boards
            for(var i=0; i<this.boards.length; i++) {
                if(this.boards[i].category == this.chosenCategory) filteredBoards.push(this.boards[i]);
            }
            return filteredBoards;
        }
    }
}
</script>

<style scoped>

.search-box {
    float: right;
    width: 300px;
    text-align: right;
    font-size: 12px;
}

.search-box input {
    width: 100%;
}

</style>