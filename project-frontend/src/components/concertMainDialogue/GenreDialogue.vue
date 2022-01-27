<template>
    <v-layout row justify-center>
            <v-dialog v-model="dialog" persistent max-width="550">
                <template v-slot:activator="{ on }">
                    <div class="col s4 m4 l4 xl4 btn-flat waves-effect waves-teal btn-dial" align="center" v-on="on" style="color: white;">
                        <p class="dialogTitle">Genre</p>
                    </div>
                </template>
                <v-card class="#424242 grey darken-3">
                    <v-card-title class="headline">
                        <p class="headline">Genre</p>
                        <p class="description">장르를 설정하시면 맞는 공연을 추천해드려요! :)</p>
                    </v-card-title>
                    <v-card-text>
                        <div class="row">
                            &ensp;
                            <v-checkbox class="shrink mr-2" label="HIP-HOP" v-model="chosenGenres" multiple value="HIP-HOP" dark color="teal"/>
                            <v-checkbox class="shrink mr-2" label="EDM" v-model="chosenGenres" multiple value="EDM" dark color="pink"/>
                            <v-checkbox class="shrink mr-2" label="ROCK" v-model="chosenGenres" multiple value="ROCK" dark color="orange"/>
                            <v-checkbox class="shrink mr-2" label="JAZZ" v-model="chosenGenres" multiple value="JAZZ" dark color="blue"/>
                            <v-checkbox class="shrink mr-2" label="ACOUSTIC" v-model="chosenGenres" multiple value="ACOUSTIC" dark color="yellow"/>
                        </div>
                        {{chosenGenres}}
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
    name: 'Dialogue',
    data() {
        return {
            dialog: false,
            chosenGenres: [ "ROCK", "HIP-HOP", "EDM", "JAZZ", "ACOUSTIC" ]
        }
    },
    methods: {
        ...mapMutations(['setConcertListByUserTaste']),

        apply() {
            this.setConcertListByUserTaste(this.chosenGenres);
            alert('적용되었습니다.');
            this.dialog = false;
        },
        cancel() {
            this.chosenGenres = [ "ROCK", "HIP-HOP", "EDM", "JAZZ", "ACOUSTIC" ];
            this.dialog = false;
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');

.headline {
    font-size: 30px;
    color: white;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    margin-right: 10px;
}

.description {
    font-size: 12px;
    font-style: italic;
    margin-top: 10px;
    color: white;
}

.genreText {
    font-size: 17px;
    color: white;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    margin-top: -5px;
}

.checkAll {
    font-size: 12px;
    font-style: italic;
    margin-top: -20px;
}

</style>

<style scoped>

.btn-dial {
    margin-bottom: 25px;
}
</style>