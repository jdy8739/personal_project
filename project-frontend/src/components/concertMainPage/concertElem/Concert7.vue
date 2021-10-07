<template>
    <div style="width: 25%;">
        
        <div style="position: relative;" v-on:mouseover="turnOnColor(7)" v-on:mouseout="turnOffColor"  @click="showInfoBar(7)">

            <img v-if="onColor == false" class="responsive-img bigImg"
            src="@/assets/img/OceanFromTheBlue.jpeg">

            <img v-else-if="onColor == true" class="responsive-img bigColorImg"
            src="@/assets/img/OceanFromTheBlue.jpeg">

            <div v-if="(onColor == false) && !wideOffLetters">
                <div class="imgTitle d-none d-lg-block" style="font-size: 70px; line-height: 75px;">Ocean From The Blue</div>
                <div class="location d-none d-lg-block">Jammers</div>
                <div class="date d-none d-lg-block">09-10-2021</div>                 
            </div>   

            <div v-else-if="onColor == true">
                <div class="imgTitleBlur d-none d-lg-block" style="font-size: 70px; line-height: 75px;">Ocean From The Blue</div>
                <div class="locationBlur d-none d-lg-block">Jammers</div>
                <div class="dateBlur d-none d-lg-block">09-10-2021</div>                
            </div>  

        </div>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'

import { mapActions, mapState } from 'vuex'

export default {
    name: 'ConcertRow2',
    data() {
        return {
            concertNo: 7,
            onColor: false,
            //imgNum: 0,
            //infoBar: false,
            wideColorChange: true,
            wideOffLetters: false,
            nav_drawer: false,

            memNoAndConNoArr:[]
        }
    },
    methods: {
        ...mapActions(['fetchConcert', 'fetchLikedOrNot']),

        turnOnColor() {
            if(this.wideColorChange) {
                this.onColor = true
                //this.imgNum = num
            }
        },
        turnOffColor() {
            if(this.wideColorChange) { 
                this.onColor = false
                //this.imgNum = 0
            } 
        },
        showInfoBar(num) {
            //this.infoBar = true
            EventBus.$emit('onInfoBar')
            
            let concertNum = this.concertNo
            EventBus.$emit('makeOtherCompBlur', concertNum)

            this.wideColorChange = false  
            this.wideOffLetters = true

            this.fetchConcert(num)

            this.memNoAndConNoArr.push(this.$store.state.userProfile.memberNo)
            this.memNoAndConNoArr.push(num)    
            this.fetchLikedOrNot(this.memNoAndConNoArr)

            this.memNoAndConNoArr = [] //초기화 필요

            //EventBus.$emit('removeInfoBarExceptRow2')
            this.onColor = true
        }
    },
    computed: {
        ...mapState(['concert', 'userProfile'])
    },
    updated() {

        EventBus.$on('hideInfoBar', () => {
            this.wideColorChange = true
            this.wideOffLetters = false
            this.onColor = false
        })
    },
    created() {
        EventBus.$on('makeOtherCompBlur', (concertNum) => {
            if(concertNum != this.concertNo) {
                this.wideColorChange = false
                this.wideOffLetters = true
                this.onColor = false
            } else {
                this.onColor = true
            }
        })
    }
}
</script>