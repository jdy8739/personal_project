<template>
    <div style="margin-top: 40px;">

        <div class="row" style="width: 101%;">

            <concert-9 v-if="acoustic && filterDate('2021-09-12')"/>
            <img v-else class="responsive-img bigImgBlured" src="@/assets/img/concert_pics/DJTipToe.jpg"
                @click="noticeFiltered"/>

            <concert-10 v-if="rock && filterDate('2021-09-12')"/>
            <img v-else class="responsive-img bigImgBlured" src="@/assets/img/concert_pics/이준형.jpeg"
            @click="noticeFiltered"/>

            <concert-11 v-if="hip_hop && filterDate('2021-09-12')"/>
            <img v-else class="responsive-img bigImgBlured" src="@/assets/img/concert_pics/Surl.jpg"
            @click="noticeFiltered"/>

            <concert-12 v-if="jazz && filterDate('2021-09-18')"/>
            <img v-else class="responsive-img bigImgBlured" src="@/assets/img/concert_pics/Net Gala.jpg"
            @click="noticeFiltered"/>
            
        </div>

        <hr>
        <information-box v-show="showInfoBar" v-bind:concert="concert"/>
        <!-- <p v-else-if="!concert">공연 정보를 불러오는데 오류가 있습니다.</p> -->

    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import { mapState } from 'vuex'

import Concert9 from '@/components/concertMainPage/concertElem/Concert9'
import Concert10 from '@/components/concertMainPage/concertElem/Concert10'
import Concert11 from '@/components/concertMainPage/concertElem/Concert11'
import Concert12 from '@/components/concertMainPage/concertElem/Concert12'

import InformationBox from '@/components/concertMainPage/InformationBox'

export default {
    name: 'ConcertRow2',
    components: {
        Concert9,
        Concert10,
        Concert11,
        Concert12,
        InformationBox
    },
    props: {
        taste: {
            type: Object
        },
        dateForFilter: {
            type: Boolean
        }
    },
    data() {
        return {
            showInfoBar: true
        }
    },
    methods: {
        noticeFiltered() {
            alert('필터처리된 공연입니다.')
        },

        filterDate(date) { //필터처리를 하는데 computed에는 안되고 왜 method에만 되는건지???

            if(this.$store.state.criteriaDateOfEnd == '' || this.dateForFilter) {
                return true
            }
            if(this.$store.state.criteriaDateOfStart <= date && this.$store.state.criteriaDateOfEnd >= date) {
                return true
            } 
            return false
        }
    },
    computed: {
        ...mapState(['concert', 'criteriaDateOfStart', 'criteriaDateOfEnd', 'isLoggedIn']),

        acoustic() {
            if(!this.taste || this.taste.chosenGenres.includes("EDM")) { //taste가 null이면 비로그인 상태라는 뜻
                return true
            }
            return false
        },
        rock() {
            if(!this.taste || this.taste.chosenGenres.includes("ROCK")) {
                return true
            }
            return false
        },
        hip_hop() {
            if(!this.taste || this.taste.chosenGenres.includes("ROCK")) {
                return true
            }
            return false
        },
        jazz() {
            if(!this.taste || this.taste.chosenGenres.includes("EDM")) {
                return true
            }
            return false
        }
    },

    created() {
        EventBus.$on('makeOtherCompBlur', (concertNum) => {
            if(concertNum < 9 || concertNum > 12) this.showInfoBar = false
            else this.showInfoBar = true
        })
    }
}
</script>
