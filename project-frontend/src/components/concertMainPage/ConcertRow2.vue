<template>
    <div style="margin-top: 40px;">

        <div>
            <div class="row" style="width: 101%;">

                <concert-5 v-if="acoustic && filterDate('2021-09-03')"/>
                <img v-else class="responsive-img bigImgBlured" src="@/assets/img/박소은.jpeg"
                 @click="noticeFiltered"/>

                <concert-6 v-if="rock && filterDate('2021-09-03')"/>
                <img v-else class="responsive-img bigImgBlured" src="@/assets/img/RollingQuartz.jpeg"
                @click="noticeFiltered"/>

                <concert-7 v-if="hip_hop && filterDate('2021-09-10')"/>
                <img v-else class="responsive-img bigImgBlured" src="@/assets/img/OceanFromTheBlue.jpeg"
                @click="noticeFiltered"/>

                <concert-8 v-if="jazz && filterDate('2021-09-11')"/>
                <img v-else class="responsive-img bigImgBlured" src="@/assets/img/김심야1.jpg"
                @click="noticeFiltered"/>
                
            </div>
        </div>

        <hr>
        <information-box v-bind:concert="concert"/>
        <!-- <p v-else-if="!concert">공연 정보를 불러오는데 오류가 있습니다.</p> -->

    </div>
</template>

<script>
import { mapState } from 'vuex'

import Concert5 from '@/components/concertMainPage/concertElem/Concert5'
import Concert6 from '@/components/concertMainPage/concertElem/Concert6'
import Concert7 from '@/components/concertMainPage/concertElem/Concert7'
import Concert8 from '@/components/concertMainPage/concertElem/Concert8'

import InformationBox from '@/components/concertMainPage/InformationBox'

export default {
    name: 'ConcertRow2',
    components: {
        Concert5,
        Concert6,
        Concert7,
        Concert8,
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
            infoBar: true
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
            if(this.taste == null || this.taste.chosenGenres.includes("ACOUSTIC")) { //taste가 null이면 비로그인 상태라는 뜻
                return true
            }
            return false
        },
        rock() {
            if(this.taste == null || this.taste.chosenGenres.includes("ROCK")) {
                return true
            }
            return false
        },
        hip_hop() {
            if(this.taste == null || this.taste.chosenGenres.includes("HIP-HOP")) {
                return true
            }
            return false
        },
        jazz() {
            if(this.taste == null || this.taste.chosenGenres.includes("JAZZ")) {
                return true
            }
            return false
        }
    }
}
</script>

<style>

.bigImgBlured {
    filter: blur(2px) grayscale(100%); 
    opacity: 0.6;
    width: 24%;
    margin-right: 5px;
}

</style>


