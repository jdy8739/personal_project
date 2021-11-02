<template>
    <div style="width: 25%;">
   
        <div style="position: relative;" v-on:mouseover="turnOnColor(11)" v-on:mouseout="turnOffColor" @click="showInfoBar(11)">
            
                        <img v-if="onColor == false" class="responsive-img bigImg"
                        src="@/assets/img/Surl.jpg">

                        <img v-else-if="onColor == true" class="responsive-img bigColorImg"
                        src="@/assets/img/Surl.jpg">

                        <div v-if="(onColor == false) && !wideOffLetters">
                            <div class="imgTitle d-none d-lg-block" style="font-size: 120px;">Surl</div>
                            <div class="location d-none d-lg-block">Prism-Hall</div>
                            <div class="date d-none d-lg-block">09-12-2021</div>                 
                        </div>   

                        <div v-else-if="onColor == true">
                            <div class="imgTitleBlur d-none d-lg-block" style="font-size: 120px;">Surl</div>
                            <div class="locationBlur d-none d-lg-block">Prism-Hall</div>
                            <div class="dateBlur d-none d-lg-block">09-12-2021</div>                
                        </div>                    
   
        </div>
        <!-- <information-box v-if="concert" v-bind:infoBar="infoBar" v-bind:concert="concert" v-on:offInfoBox="removeInfoBar"/> -->

    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import { mapActions, mapState } from 'vuex'

//import InformationBox from '@/components/concertMainPage/InformationBox'

export default {
    name: 'Concert10',
    components: {
        //InformationBox
    },
    data() {
        return {
            onColor: false,
            //imgNum: 0,
            infoBar: false,
            wideColorChange: true,
            wideOffLetters: false,
            nav_drawer: false,

            memNoAndConNoArr:[]
        }
    },
    methods: {
        ...mapActions(['fetchConcert', 'fetchLikedOrNot']),

        turnOnColor() { // 임의적으로 수정을 위해 num을 없앰
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
        },
        removeInfoBar() {
            this.infoBar = false

            this.onColor = false
            this.wideColorChange = true
            this.wideOffLetters = false

            //this.imgNum = 0

            //EventBus.$emit('removeException')
        }
    },
    computed: {
        ...mapState(['concert', 'userProfile'])
    },
    updated() {
        EventBus.$on('removeInfoBarInOtherCom', () => {
            this.infoBar = false

            this.wideColorChange = false
            this.wideOffLetters = true
            this.onColor = false
        })
    },
    watch: {
        infoBar() {
            setTimeout(() => {
                EventBus.$emit('showInfoBar')
                //alert('showInfoBar')
            }, 200)
        }
    }
}
</script>