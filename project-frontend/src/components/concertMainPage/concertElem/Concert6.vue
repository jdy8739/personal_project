<template>
    <div class="col-sm-3 concert-box" :id='`${ concert.concertNo }`' @click="focusOnThisConcert(true, false)">
        <div style="position: relative;">

            <img class="responsive-img bigColorImg"
            :src="require(`../../../../../project-backend/images/concert_pics/${ concert.concertArtist }.jpg`)">
            <div class="text-box">
                <div v-if="cntArtistName()" class="imgTitle d-lg-block">{{ concert.concertArtist }}</div>
                <div v-else class="imgTitle d-lg-block long-text">{{ concert.concertArtist }}</div>
                <div class="location d-none d-lg-block">{{ concert.concertVenue }}</div>
                <div class="date d-none d-lg-block">{{ concert.concertDate }}</div>  
            </div>
        </div>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'

export default {
    name: 'ConcertElement',
    props: {
        concert: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
           
        }
    },
    methods: {
        cntArtistName() {
            if(this.concert.concertArtist.length > 14) {
                return false;
            } else return true;
        },

        focusOnThisConcert(onChange, offBox) {

            const id = this.concert.concertNo;
            const focusedConcert = document.getElementById(id);
            const focusedImg = focusedConcert.getElementsByTagName('img')[0];
            const focusedText = focusedConcert.querySelector('.text-box');

            if(onChange && offBox) {
                focusedConcert.classList.add('concert-box');
                focusedImg.classList.add('bigColorImg');
                focusedImg.classList.remove('blurImg');
                focusedText.classList.remove('hide');
            } else if(onChange && !offBox) {
                focusedConcert.classList.remove('concert-box');
                focusedImg.classList.remove('bigColorImg');
                focusedImg.classList.remove('blurImg');
                focusedText.classList.remove('hide');
                EventBus.$emit('offColors', id)
            } else {
                focusedConcert.classList.add('concert-box');
                focusedImg.classList.add('bigColorImg');
                focusedImg.classList.add('blurImg');
                focusedText.classList.add('hide');
            }
        }
    },
    mounted() {
        EventBus.$on('offColors', (idx) => {
            const id = this.concert.concertNo;

            if(id !== idx) {
                this.focusOnThisConcert(false, false);
            }
        });

        EventBus.$on('offBox', () => {
            this.focusOnThisConcert(true, true);
        });
    }
}
</script>

<style scoped>

.hide {
    display: none;
}

.concert-box {
    padding: 3px;
}

.text-box {
    opacity: 0.75;
    width: 200px;
    transition: all 1s;
}

.long-text {
    font-size: 60px; 
    line-height: 60px;
}

.concert-box:hover .text-box {
    opacity: 0.2;
}

.bigColorImg {
    filter: grayscale(100%), blur(20px);
    opacity: 0.3;
}

.concert-box:hover .bigColorImg {
    filter: grayscale(0%), blur(0px);
    opacity: 1.0;
}

.blurImg {
    filter: grayscale(100%), blur(70px);
    opacity: 0.5;
}

@media screen and (max-width: 1200px) {
    .imgTitle, .long-text {
        font-size: 40px;
        line-height: 40px;
    }
}

@media screen and (max-width: 600px) {
    .imgTitle {
        font-size: 80px;
        line-height: 100px;
    }

    .long-text {
        font-size: 75px;
        line-height: 80px;
    }
}
</style>