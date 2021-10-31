<template>

    <div style="margin-top: -15px;">
        <v-container>

            <!-- <table>
                <tr>
                    <td class="description" width="15%;">No</td>
                    <td class="description" width="50%;">제목</td>
                    <td class="description" width="15%;">작성자</td>
                    <td class="description" width="30%;" style="text-align: right;">게시일</td>
                </tr>
                <tr v-if="!boards || boards.length == 0">
                    <td colspan="4">게시글들을 불러오는 중입니다. 조금만 기다려주세요. :)</td>
                </tr>
                <tr v-else-if="boards" v-for="(board, index) in boards" v-bind:key="index">
                    <td>{{ board.boardNo }}</td>
                    <td>
                        <p @click="sendNum(board.boardNo)" style="margin-bottom: 0px;">
                            <router-link :to="{ name: 'CommunityReadPage', params: { boardNo: board.boardNo.toString() }}"> 
                                {{ board.title }}
                            </router-link>
                        </p>
                    </td>
                    <td>{{ board.id }}</td>
                    <td style="text-align: right;">{{ board.regDate }}</td>
                </tr>
            </table> -->

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

            <v-btn text color="red" style="margin-top: 20px; font-size: 12px; float: right;" @click="goToWrite">
                글 쓰러가기
            </v-btn>

            <br/>
            <br/>
            <br/>
            <br/>

            <v-data-table :headers="headerTitle" dark 
                        :items="filteredBoards()"
                        :items-per-page="10"
                        :search="searchTitle"
                        class="elevation-1">

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
            
            <div style="float: right; width: 300px; margin-top: 20px;">
                <h5 style="font-size: 12px; float: left; color: grey;">찾을 게시글의 제목 또는 작성자를 검색하세요.</h5>

                <input v-model="searchTitle" class="description"
                style="color: white; font-size: 12px; margin-top: -10px; margin-bottom: 40px;"/>
                    
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
            searchTitle: '',

            category: [ '후기', '음악', '공연', '건의', '전체' ],
            chosenCategory: '전체'
        }
    },
    methods: {

        goToWrite() {
            if(this.$store.state.isLoggedIn) {

                this.$router.push({
                    name: 'CommunityWritePage'
                })

            } else {
                alert('로그인이 필요한 서비스입니다!')
            }
        },

        handleClick(boardNo) {
            this.$router.push({
                name:'CommunityReadPage',
                params: { boardNo: boardNo.toString() }
            })
        },

        filteredBoards() {

            var filteredBoards = []

            if(this.chosenCategory == '전체') filteredBoards = this.boards
           
            for(var i=0; i<this.boards.length; i++) {

                if(this.boards[i].category == this.chosenCategory) filteredBoards.push(this.boards[i])
            }

            return filteredBoards
        }
    }
}
</script>
