<template>
    <div class="row" style="width: 100%;">
        <concert-10 v-if="jazz"/>
        <concert-11 v-if="rock"/>
        <concert-10 v-if="jazz"/>
        <concert-11 v-if="rock"/>

        <concert-10 v-if="jazz"/>
        <concert-11 v-if="rock"/>
        <concert-10 v-if="jazz"/>
        <concert-11 v-if="rock"/>

        <information-box v-on:offInfoBox="removeInfoBar"/>

    </div>
</template>

<script>
//import { mapState } from 'vuex'
import InformationBox from '@/components/concertMainPage/InformationBox'

import Concert10 from '@/components/concertMainPage/concertElem/Concert10'
import Concert11 from '@/components/concertMainPage/concertElem/Concert11'

export default {
    name: 'Concerts',
    components: {
        Concert10,
        Concert11,
        InformationBox
    },
    props: {
        taste: {
            type: Object,
            //required: true
        }
    },
    computed: {
        //...mapState(['taste']),

        jazz() {
            if(this.taste.chosenGenres.includes("JAZZ")) {
                return true
            }
            return false
        },
        rock() {
            if(this.taste.chosenGenres.includes("ROCK")) {
                return true
            }
            return false
        },
    }
}
</script>
