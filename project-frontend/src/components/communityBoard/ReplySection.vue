<template>
    <div>
        <v-container>
            <table dense dark>
                <tr>
                    <td class="description" style="color: gray;">작성된 댓글</td>
                </tr>
                <tr v-for="(reply, index) in replyList" :key="index">
                    <td class="description" style="color: gray;">{{ reply.id }}</td>

                    <td v-if="modifyIndex === index" class="description" style="width: 75%;">
                        <input v-model="modifiedContent" style="color: white;"
                        @keydown.enter="submitModified(reply.boardReplyNo, reply.id, index)"/>
                    </td>
                    <td v-else-if="modifyIndex !== index" class="description" style="width: 75%; color: white;">{{ reply.content }}</td>

                    <div v-if="modifyIndex == index">
                        <td width="20">
                            <v-btn class="ma-2" text icon color="red lighten-3" 
                            @click="submitModified(reply.boardReplyNo, reply.id, index)">

                                <v-icon>keyboard_arrow_up</v-icon>
                            </v-btn>
                        </td>
                        <td style="width: 5%;">
                            <v-btn class="ma-2" text icon color="red lighten-3" @click="cancel()">
                                <v-icon>cancel</v-icon>
                            </v-btn>
                        </td>
                    </div>
                    <div v-else-if="modifyIndex !== index">
                        <td>
                            <v-btn class="ma-2" text icon color="red lighten-3" @click="modifyReply(reply.content, index)">
                                <v-icon>mode_edit</v-icon>
                            </v-btn>
                        </td>
                    </div>
                    <td>
                        <v-btn class="ma-2" text icon color="red lighten-3" @click="deleteReply(reply.boardReplyNo, reply.id, index)">
                            <v-icon>delete</v-icon>
                        </v-btn>
                    </td>
                </tr>
            </table>
        </v-container>
    </div>
</template>

<script>
import axios from 'axios'
import { mapMutations, mapState } from 'vuex'

export default {
    name: 'ReplySection',
    data() {
        return {
            modifyIndex: -1,
            modifiedContent: ''
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile', 'board', 'replyList'])
    },
    methods: {
        ...mapMutations(['handleUserLogin', 'handleReplyRequest']),

        cancel() {
            this.modifyIndex = -1;
            this.modifiedContent = '';
        },
        modifyReply(content, index) {
            this.modifyIndex = index;
            this.modifiedContent = content;
        },
        submitModified(tmpBoardReplyNo, id, index) {
            
            if(this.isLoggedIn && (this.userProfile.id === id || this.userProfile.identity === 'admin')) {
                
                 axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                            
                            const boardReplyNo = tmpBoardReplyNo;
                            const content = this.modifiedContent;

                            axios.put('http://localhost:8888/board/modifyReply', { boardReplyNo, content })
                                .then(() => {

                                    const newReply = { boardReplyNo: boardReplyNo, boardNo: this.board.boardNo, id: id, content: this.modifiedContent };
                                    this.handleReplyRequest([ index, newReply ]);

                                    this.modifyIndex = -1;
                                    this.modifiedContent = '';
                                })
                                .catch(err => console.log(err));
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!')
                            this.handleUserLogin();
                        }
                    })           
            } else {
                alert('권한이 없습니다!');
            }

        },
        deleteReply(replyNo, id, index) {
            
            if(this.isLoggedIn && (this.userProfile.id === id || this.userProfile.identity === 'admin')) {
                
                 axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                            axios.delete(`http://localhost:8888/board/deleteReply/${ replyNo }`) 
                                .then(() => {
                                    this.handleReplyRequest(index);
                                })
                                .catch(() => { alert('잠시 후에 다시 시도해주세요.'); });
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                        }
                    })
            } else { alert('권한이 없습니다!'); }
        }
    }
}
</script>
