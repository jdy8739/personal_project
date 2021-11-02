<template>
    <div class="grey darken-4" style="height: 100%;">
        <br>
        
        <div align="center" style="margin-top: -20px;">
            <img class="responsive-img bigImg" src="@/assets/img/wall2.jpg" width="100%" style="margin-bottom: -6px;">
        </div>
        <hr>
        <div class="row" style="margin-bottom: -20px;"> 
            <genre-dialogue style="margin-top: 12px;" class="col xs4 sm4 md4 lg4"/>    
            <artist-dialogue class="col xs4 sm4 md4 lg4"/> 
            <date-dialogue class="col xs4 sm4 md4 lg4"/>                        
        </div>

        <concert-row-2 :taste="taste" :dateForFilter="dateForFilter"/>

        <!-- <concert-row-1/>
        
        <concert-row-3/> -->

        <!-- <concerts v-if="taste" v-bind:taste="taste"/> -->
      
        <div :class="['box', 'grey darken-4', {'dragged': dragged}]"
            v-dragged="onDragged"
            style="width: 170px; color: red;">

            <div v-if="!isFilterOff" style="margin-top: -5px;">
                FILTER OFF
                <v-btn icon color="red lighten-1" @click="offFilter">
                    <v-icon>remove</v-icon>
                </v-btn>
            </div>

            <div v-if="isFilterOff" style="margin-top: -5px; color: teal;">
                FILTER ON
                <v-btn icon color="teal lighten-1" @click="onFilter">
                    <v-icon>done</v-icon>
                </v-btn>
            </div>

        </div>
        
        <main-page-footer/>
     
    </div>
</template>

<script>
import Vue from 'vue'

import cookies from 'vue-cookies'
Vue.use(cookies)

import { mapActions, mapState } from 'vuex'

import GenreDialogue from '@/components/concertMainDialogue/GenreDialogue'
import ArtistDialogue from '@/components/concertMainDialogue/ArtistDialogue'
import DateDialogue from '@/components/concertMainDialogue/DateDialogue'

// import ConcertRow1 from '@/components/concertMainPage/ConcertRow1'
import ConcertRow2 from '@/components/concertMainPage/ConcertRow2'
// import ConcertRow3 from '@/components/concertMainPage/ConcertRow3'

//import Concerts from '@/components/concertMainPage/Concerts'
import MainPageFooter from '@/components/concertMainPage/MainPageFooter'

export default {
    name: 'MainPage',
    
    components: {
        GenreDialogue,
        ArtistDialogue,
        DateDialogue,

        // ConcertRow1,
        ConcertRow2,
        // ConcertRow3,

        //Concerts,

        MainPageFooter
    },
    data() {
        return {
            nav_drawer: false,
            deltaX: 0,
            deltaY: 0,
            offsetX: 0,
            offsetY: 0,
            clientX: 0,
            clientY: 0,
            dragged: false,

            isFilterOff: false,
            tmpChosenGenres: null
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile', 'userIdentity', 'taste', 'dateForFilter'])
    },
    methods: {
        ...mapActions(['fetchTaste']),

        offFilter() {
            this.tmpChosenGenres = this.$store.state.taste
            this.$store.state.taste = null
            this.isFilterOff = true

            this.$store.state.dateForFilter = true
        },

        onFilter() {
            this.$store.state.taste = this.tmpChosenGenres
            this.isFilterOff = false

            this.$store.state.dateForFilter = false
        },

        onDragged ({ el, deltaX, deltaY, offsetX, offsetY, clientX, clientY, first, last }) {
        if (first) {
          this.dragged = true
          return
        }
        if (last) {
          this.dragged = false
          return
        }
        var l = +window.getComputedStyle(el)['left'].slice(0, -2) || 0
        var t = +window.getComputedStyle(el)['top'].slice(0, -2) || 0
        el.style.left = l + deltaX + 'px'
        el.style.top = t + deltaY + 'px'
        this.deltaX = deltaX
        this.deltaY = deltaY
        this.offsetX = offsetX
        this.offsetY = offsetY
        this.clientX = clientX
        this.clientY = clientY
      }
    },
    mounted() {
        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity

            this.fetchTaste(this.$store.state.userProfile.memberNo)
        } 
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap');


.topBar {
    font-style: italic;
    margin-right: 25px;
    color: white;
}

.inputBar {
    font-style: italic;
    font-size: 8;
    color: white;
}

.imgTitle {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 85px;
    line-height: 90px;
}

.location {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 75%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 30px;
    line-height: 120px;
}

.date {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 82%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 30px;
    line-height: 120px;
}

.imgTitleBlur {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 85px;
    line-height: 90px;
    filter: blur(5px);
    color: rgba(0, 0, 0, 0.3);
}

.locationBlur {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 75%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 30px;
    line-height: 120px;
    filter: blur(5px);
    color: rgba(0, 0, 0, 0.3);
}

.dateBlur {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 82%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
    font-size: 30px;
    line-height: 120px;
    filter: blur(5px);
    color: rgba(0, 0, 0, 0.3);
}

.bigImg {
    filter: blur(1px) grayscale(100%); 
    opacity: 0.6;
}

.box {
    width: 200px;
    height: 50px;
    box-sizing: border-box;
    padding: 15px;
    background: white;
    display: inline-block;
    position: relative;
    user-select: none;
    text-align: left;
    cursor: pointer;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    transition: box-shadow 0.3s cubic-bezier(.25, .8, .25, 1);
    line-height: 1.8;
    color: #272727;
    font-family: Consolas, 'Courier New', 'Lucida Console';
    border-radius: 4px;
}

.box.dragged {
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
}

</style>


