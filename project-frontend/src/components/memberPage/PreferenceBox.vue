<template>
  <div class="out mt-10">
    <vueper-slides class="in" :touchable="false" :bullets="false" :arrows="false" ref="first">

      <vueper-slide :key="1">
        <template v-slot:content>

            <div class="row mt-10 center">
                <v-btn v-if="btnArr[0] == 0" class="ma-1" x-large plain @click="chooseGenre('ROCK', 0)">
                    <p class="genreText" style="color: red;">Rock</p>
                </v-btn>
                <v-btn v-if="btnArr[0] == 1" class="ma-1" x-large plain disabled>
                    <p class="genreText">Rock</p>
                </v-btn>

                <v-btn v-if="btnArr[1] == 0" class="ma-1" x-large plain @click="chooseGenre('HIP-HOP', 1)">
                    <p class="genreText" style="color: teal;">HipHop</p>
                </v-btn>
                <v-btn v-if="btnArr[1] != 0" class="ma-1" x-large plain disabled>
                    <p class="genreText">HipHop</p>
                </v-btn>

                <v-btn v-if="btnArr[2] == 0" class="ma-1" x-large plain @click="chooseGenre('EDM', 2)">
                    <p class="genreText" style="color: blue;">EDM</p>
                </v-btn>
                <v-btn v-if="btnArr[2] != 0" class="ma-1" x-large plain disabled>
                    <p class="genreText">EDM</p>
                </v-btn>

                <v-btn v-if="btnArr[3] == 0" class="ma-1" x-large plain @click="chooseGenre('JAZZ', 3)">
                    <p class="genreText" style="color: orange">JAZZ</p>
                </v-btn>
                <v-btn v-if="btnArr[3] != 0" class="ma-1" x-large plain disabled>
                    <p class="genreText">JAZZ</p>
                </v-btn>

                <v-btn  v-if="btnArr[4] == 0" class="ma-1" x-large plain @click="chooseGenre('ACOUSTIC', 4)">
                    <p class="genreText" style="color: yellow">ACOUSTIC</p>
                </v-btn>
                <v-btn v-if="btnArr[4] != 0" class="ma-1" x-large plain disabled>
                    <p class="genreText">ACOUSTIC</p>
                </v-btn>
            </div>
            <br><br>
            <div>
                <p class="footerText">선택한 장르 (클릭해서 삭제)</p>
                <div>
                    <p v-for="(genre, index) in chosenGenres" :key="index" 
                    class="footerText chosen" @click="removeGenre(index, $event)">{{ genre }}</p>
                </div>
            </div>
            <v-btn :loading="loading" class="ma-1" x-large color="error" plain @click="confirm">
                NEXT
            </v-btn>
        </template>
      </vueper-slide>

      <vueper-slide :key="2">
        <template v-slot:content>
            <div>
                <div>
                    <p class="footerText mt-10">어떤 분위기의 음악을 좋아하시나요?</p>

                    <v-slider v-model="firstSlide" :tick-labels="firstLabels" :max="4" step="1" ticks="always" tick-size="4" class="footerText"
                    :color="color1" track-color="grey" dark></v-slider>

                    <p class="footerText mt-10">공연장은 어떤 분위기가 좋으세요?</p>

                    <v-slider v-model="secondSlide" :tick-labels="secondLabels" :max="3" step="1" ticks="always" tick-size="4" class="footerText"
                    :color="color2" track-color="grey" dark></v-slider>
                </div>
                <v-btn :loading="false" class="ma-1" x-large color="error" plain @click="$refs.first.previous()">
                    BACK
                </v-btn>
                <v-btn :loading="loading" class="ma-1" x-large color="error" plain @click="confirm(1)">
                    NEXT
                </v-btn>
            </div>

        </template>
      </vueper-slide>
      
      <vueper-slide :key="3">
        <template v-slot:content>

          <div align="center">
            <v-text-field label="회원님이 좋아하는 아티스트를 적어주세요!" class="footerText mt-10" outlined shaped color="teal"
            hint="꼭 인디 아티스트가 아니더라고 괜찮아요! :)" v-model="artist" @keydown.enter="pushArtist(artist)" dark/>

            <div>
                <p class="footerText">선택한 아티스트 (클릭해서 삭제)</p>
                <div>
                    <p v-for="(artist, index) in chosenArtists" :key="index" 
                    class="footerText chosen" @click="removeArtist(index)">{{ artist }}</p>
                </div>
            </div>

            <v-btn :loading="false" class="ma-1" x-large color="error" plain @click="$refs.first.previous()">
                BACK
            </v-btn>
            <v-btn :loading="loading" class="ma-1" x-large color="error" plain @click="confirm">
                NEXT
            </v-btn>
          </div>
        </template>
      </vueper-slide>

      <vueper-slide :key="4">
        <template v-slot:content>
          <div align="center">
            <p class="footerText mt-10">해당사항을 저장할까요? 이것은 회원님에게 더 좋은 추천을 해드리기위해 사용됩니다. :)</p>

            <v-btn :loading="false" class="ma-1 footerText" x-large color="error" plain @click="$refs.first.previous()">
                BACK
            </v-btn>
            <v-btn :loading="loading" class="ma-1 footerText" x-large color="error" plain @click="complete">
                완료!
            </v-btn>
          </div>
        </template>
      </vueper-slide>

    </vueper-slides>

  </div>
</template>

<script>
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
    name: 'PreferenceBox',
    components: { 
        VueperSlides, 
        VueperSlide 
    },
    data() {
        return {
            loading: false,
            chosenGenres: [],
            chosenGenresForRequest: [],

            btnArr: [0, 0, 0, 0, 0],

            firstSlide: '',
            speedTaste: '',

            secondSlide: '',
            atmosphereTaste: '',

            artist: '',
            chosenArtists: [],
            chosenArtistsForRequest: [],

            firstLabels: [
                '부드럽고 잔잔한',
                '적당히 신나는',
                '보통 정도의 잔잔함과 빠른',
                '꽤 빠르고 신나는',
                '빠르고 과격한'
            ],

            secondLabels: [
                '조용한 분위기의',
                '조금 사람들이 모이는',
                '사람들이 꽤 모이는',
                '왁자지껄한',
            ]
        }
    },
    methods: {
        showAlert(msg) {
            alert(msg);
        },
        async confirm(num) { //num

            if(num == 1) {
                if(this.firstSlide == 0) {
                    this.speedTaste = this.firstLabels[0];
                } else if(this.firstSlide == 1) {
                    this.speedTaste = this.firstLabels[1];
                } else if(this.firstSlide == 2) {
                    this.speedTaste = this.firstLabels[2];
                } else if(this.firstSlide == 3) {
                    this.speedTaste = this.firstLabels[3];
                } else if(this.firstSlide == 4) {
                    this.speedTaste = this.firstLabels[4];
                }

                if(this.secondSlide == 0) {
                    this.atmosphereTaste = this.secondLabels[0];
                } else if(this.secondSlide == 1) {
                    this.atmosphereTaste = this.secondLabels[1];
                } else if(this.secondSlide == 2) {
                    this.atmosphereTaste = this.secondLabels[2];
                } else if(this.secondSlide == 3) {
                    this.atmosphereTaste = this.secondLabels[3];
                } 
                //alert(this.speedTaste + ' ' + this.atmosphereTaste)
            } 

            if(this.chosenGenres.length == 0) {
                alert('최소 하나 이상의 장르를 선택해주세요!');
            } else {
                this.loading = true;

                await new Promise(resolve => setTimeout(resolve, 1000));

                this.loading = false;

                this.$refs.first.next();
            }
        },
        chooseGenre(genre, num) {
            this.chosenGenres.push(genre + ' ');

            this.chosenGenresForRequest.push(genre); //전송 용도

            for(var i=0; i<5; i++) {
                if(i==num) {
                    this.btnArr[i] = 1;
                }
            }
        },
        removeGenre(index, $event) {
            this.chosenGenres.splice(index, 1);
            this.chosenGenresForRequest.splice(index, 1);

            if($event.target.innerHTML == 'ROCK ') {
                this.btnArr[0] = 0;
            } else if($event.target.innerHTML == 'HIP-HOP ') {
                this.btnArr[1] = 0;
            } else if($event.target.innerHTML == 'EDM ') {
                this.btnArr[2] = 0;
            } else if($event.target.innerHTML == 'JAZZ ') {
                this.btnArr[3] = 0;
            } else if($event.target.innerHTML == 'ACOUSTIC ') {
                this.btnArr[4] = 0;
            }
        },
        pushArtist(artist) {

            ///([^가-힣\x20])/i
            if(!/^[가-힣|a-z|A-Z|0-9|?'#!$|/\s/g]+$/.test(artist)) { //   /\s/g   <--스페이스바 허용
                alert('올바른 문자를 입력해주세요. :)')

            } else {
                this.chosenArtistsForRequest.push(artist) //전송 용도

                this.chosenArtists.push('#' + artist + ' ');
                this.artist = '';
            }
        },
        removeArtist(index) {
            this.chosenArtists.splice(index, 1);
            this.chosenArtistsForRequest.splice(index, 1);
        },
        complete() {
            const chosenGenres = JSON.stringify(this.chosenGenresForRequest);
            const speedTaste = this.speedTaste;
            const atmosphereTaste = this.atmosphereTaste;
            const chosenArtists = JSON.stringify(this.chosenArtistsForRequest);

            this.$emit('complete', { chosenGenres, speedTaste, atmosphereTaste, chosenArtists });
        }
    },
    computed: {
        color1() {
            if (this.firstSlide == 1) return 'teal';
            if (this.firstSlide == 2) return 'yellow';
            if (this.firstSlide == 3) return 'orange';
            if (this.firstSlide == 4) return 'red';
            return 'indigo';
        },

        color2() {
            if (this.secondSlide == 1) return 'teal';
            if (this.secondSlide == 2) return 'yellow';
            if (this.secondSlide == 3) return 'red';
            return 'indigo';
        }
    }
}
</script>


<style scoped>
.chosen {
    font-size: 25px;
    cursor: pointer;
    display: inline-block;
    margin: 8px;
}

.center {
    justify-content: center;
}
</style>
        