<template>
    <v-layout row justify-center>
            <v-dialog v-model="dialog" persistent max-width="400">
                <template v-slot:activator="{ on }">
                    <div class="col s4 m4 l4 xl4 btn-flat waves-effect waves-yellow" align="center" v-on="on">                    
                        <p class="dialogTitle">Date</p>
                    </div>
                </template>
                <v-card class="#424242 grey darken-3" color="white">
                    <v-card-title class="headline">
                        <p class="headline">Date</p>
                        <p class="description">입력하신 날짜에 해당하는 공연을 추천해드려요! :)</p>
                    </v-card-title>
                    <v-card-text>
                        <div style="color: white;">
                            <p>from</p>
                            <input type="date" name="date" v-model="startDate"/>
                            <p>to</p>
                            <input type="date" name="date" v-model="endDate"/>
                        </div>
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
import { mapMutations } from 'vuex';

export default {
    name: 'DateDialogue',
    data() {
        return {
            dialog: false,
            startDate: '',
            endDate: ''
        }
    },
    methods: {
        ...mapMutations(['setDateCriteria']),
        apply() {
            if(this.endDate != '') {
                alert('적용되었습니다.');
                this.setDateCriteria([ this.startDate, this.endDate ]);
                this.dialog = false ;
            } else {
                alert('기준이 될 날짜를 선택해주세요!');
            }
        },
        cancel() {
            this.startDate = '';
            this.endDate = '';
            this.dialog = false;
        }
    }
}
</script>

<style>
.dialogTitle {
    color: rgb(150, 150, 150);
    transition: all 0.5s;
}
.dialogTitle:hover {
    color: turquoise;
}
</style>