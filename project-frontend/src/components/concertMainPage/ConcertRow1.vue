<template>
    <div>
        <Concert6 v-for="(concert, i) in concerts" :key="i" :concert="concert" class="mb-5"/>
        <InformationBox v-if="isInfoBoxCalled"/>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import Concert6 from '@/components/concertMainPage/concertElem/Concert6'
import InformationBox from '@/components/concertMainPage/InformationBox'

export default {
    name: 'ConcertRow2',
    components: {
        Concert6,
        InformationBox
    },
    data() {
        return {
            isInfoBoxCalled: false
        }
    },
    props: {
        concerts: {
            type: Array,
            required: true
        }
    },
    mounted() {
        EventBus.$on('offColors', (idx) => {
            this.isInfoBoxCalled = false;

            let start = this.concerts[0].concertNo;
            let end = idx;

            const infoBoxNum = end - start;
            if(infoBoxNum > -1 && infoBoxNum < 4) {
                this.isInfoBoxCalled = true;
            }
        });
    }
}
</script>

