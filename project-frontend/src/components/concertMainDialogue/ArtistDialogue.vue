<template>
    <v-layout row justify-center>
            <v-dialog v-model="dialog" persistent max-width="400">
                <!-- dialog는 팝업창 띄워주는역할 -->
                <template v-slot:activator="{ on }">

                    <div class="col s4 m4 l4 xl4 gray btn-flat waves-effect waves-red" align="center" v-on="on" style="color: white;">
                        Artist
                    </div>   

                </template>
                <!-- 팝업창에서 선택하는 폼이라는데.. 이해가 잘안되서 패턴처럼 외우는게 나을듯 
                어쨋든 이 dialog 기능을 on시키는거라고 생각하면된다. -->
                <v-card class="#424242 grey darken-3">

                    <v-card-title class="headline">
                        <p class="headline">Artist</p>
                        <p class="description">선호하는 아티스트와 비슷한 아티스트를 추천해드려요! :)</p>
                    </v-card-title>

                    <v-card-text>
                        <input type="text" style="font-size: 12px; font-style: italic; color: white;" v-model="basicText" @click="resetText"/>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="teal darken-1" text @click.native="btn_apply($event)">
                            적용
                        </v-btn>
                        <v-btn color="teal darken-1" text @click.native="btn_cancel($event)">
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
            basicText: '선호하는 아티스트의 이름을 입력해주세요.'
        }
    },
    methods: {
        btn_apply() {
            alert('적용되었습니다.')
            this.dialog = false
        },
        btn_cancel() {
            alert('변경 취소')
            this.dialog = false
        },
        resetText() {
            this.basicText = ''
        }
    }
}
</script>

<style>

#headline {
    font-size: 30px;
    color: white;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    margin-right: 10px;
}

#description {
    font-size: 12px;
    font-style: italic;
    margin-top: 10px;
}

</style>