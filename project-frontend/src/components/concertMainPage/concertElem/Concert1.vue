<template>
    <div style="width: 25%;">
        
        <div style="position: relative;" v-on:mouseover="turnOnColor(1)" v-on:mouseout="turnOffColor"  @click="showInfoBar(1)"> 

            <img v-if="onColor == false" class="responsive-img bigImg"
            src="@/assets/img/concert_pics/FlyingDog.jpg">

            <img v-else-if="onColor == true" class="responsive-img bigColorImg"
            src="@/assets/img/concert_pics/FlyingDog.jpg">

            <div v-if="(onColor == false) && !wideOffLetters">
                <div class="imgTitle d-none d-lg-block">Flying-Dog</div>
                <div class="location d-none d-lg-block">Club FF</div>
                <div class="date d-none d-lg-block">08-20-2021</div>                 
            </div>   

            <div v-else-if="onColor == true">
                <div class="imgTitleBlur d-none d-lg-block">Flying-Dog</div>
                <div class="locationBlur d-none d-lg-block">Club FF</div>
                <div class="dateBlur d-none d-lg-block">08-20-2021</div>                 
            </div>   

        </div>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'

import { mapActions, mapState } from 'vuex'

export default {
    name: 'ConcertRow1',
    data() {
        return {
            concertNo: 1,
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
              EventBus.$emit('onInfoBar')
            
            let concertNum = this.concertNo
            EventBus.$emit('makeOtherCompBlur', concertNum)

            this.wideColorChange = false  
            this.wideOffLetters = true

            this.fetchConcert(num)

            var memNoAndConNoArr = []

            memNoAndConNoArr.push(this.$store.state.userProfile.memberNo)
            memNoAndConNoArr.push(num)    

            let formData = new FormData()
            formData.append("likedOrNotNumArr", memNoAndConNoArr)

            this.fetchLikedOrNot(formData)

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