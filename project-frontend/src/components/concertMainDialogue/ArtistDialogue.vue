<template>
    <v-layout row justify-center>
            <v-dialog v-model="dialog" persistent max-width="400">
                <template v-slot:activator="{ on }">
                    <div class="col s4 m4 l4 xl4 btn-flat waves-effect waves-red" align="center" v-on="on">
                        <p class="dialogTitle">Artist</p>
                    </div>   
                </template>
                <v-card class="grey darken-3">
                    <v-card-title class="headline">
                        <p class="headline">Artist</p>
                        <p class="description">선호하는 아티스트와 비슷한 아티스트를 추천해드려요! :)</p>
                    </v-card-title>
                    <v-card-text>
                        <p v-for="(artistName, index) in artistNameArr" :key="index" @click="removeName(index)" class="input">
                            {{ artistName }} &nbsp; &nbsp;
                        </p>
                        <input type="text" :placeholder="basicText" @keydown.enter="inputArtistName"
                        v-model="name"/>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="teal darken-1" text @click.native="apply($event)">
                            적용
                        </v-btn>
                        <v-btn color="teal darken-1" text @click.native="cancel($event)">
                            취소
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
</template>

<script>

export default {
    name: 'Dialogue',
    data() {
        return {
            dialog: false,
            basicText: '선호하는 아티스트의 이름을 입력해주세요.',
            name: '',
            artistNameArr: []
        }
    },
    methods: {
        inputArtistName() {
            if(!/^[가-힣|a-z|A-Z|0-9|?'#!$|/\s/g]+$/.test(this.name)) { //   /\s/g   <--스페이스바 허용
                alert('올바른 문자를 입력해주세요. :)');
            } else {
                this.artistNameArr.push('#' + this.name);
                this.name = '';
            }
        },
        removeName(index) {
            this.artistNameArr.splice(index, 1);
        },
        apply() {
            alert('적용되었습니다.');
            this.name = false;
            this.dialog = false;
        },
        cancel() {
            this.dialog = false;
        }
    }
}
</script>

<style scoped>
.input {
    color: white; 
    display: inline;
}

input {
    font-size: 12px; 
    font-style: italic; 
    color: white;
}
</style>