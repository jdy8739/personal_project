<template>
    <div class="col-sm-3 concert-box" :id='`${ ownId }`' @click="focusOnThisConcert(true, false)">
        <div :style="{ position: `${ position }` }">
            <img class="responsive-img bigColorImg"
            :src="require(`../../../../../project-backend/images/concert_pics/${ concert.folderName }/${ concert.concertName }-1.jpg`)">
            <div v-if="isThisConcertFilteredByDate()" class="text-box">
                <div v-if="isLongTitleName()" class="img-title big-text d-lg-block">{{ concert.concertArtist }}</div>
                <div v-else class="img-title small-text d-lg-block">{{ concert.concertArtist }}</div>
                <div class="location d-none d-lg-block">{{ concert.concertVenue }}</div>
                <div class="date d-none d-lg-block">{{ concert.concertDate }}</div>
                <img class="recomended-icon" v-if="isLikedTaste()" src="../../../assets/recomended_icon.png"/>
            </div>
            <div v-else class="text-box">
                <img class="banned-icon" src="../../../assets/banned_icon.png"/>
            </div>
        </div>
    </div>
</template>

<script>
import EventBus from '@/eventBus.js'
import { mapState } from 'vuex';

export default {
    name: 'ConcertElement',
    props: {
        concert: {
            type: Object,
            required: true
        },
        rowIndex: {
            type: Number,
            required: true
        },
        colIndex: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
           position: 'relative',
           isNotFiltered: true
        }
    },
    computed: {
        ...mapState(['taste', 'concertNoFilteredByDate']),
        ownId() {
            return this.rowIndex * 4 + this.colIndex + 1;
        },
        ownGenre() {
            return this.concert.concertGenre.toString();
        }
    },
    methods: {
        isLikedTaste() {
            if(JSON.stringify(this.taste).includes(this.ownGenre)) {
                return true;
            }
            else return false;
        },
        isLongTitleName() {
            if(this.concert.concertArtist.length > 14) {
                return false;
            } else return true;
        },
        isThisConcertFilteredByDate() {
            if(this.concertNoFilteredByDate.length === 0) return true;
            for(let i=0; i<this.concertNoFilteredByDate.length; i++) {
                if(this.concertNoFilteredByDate[i] === this.ownId) return true;
            }
            this.isNotFiltered = false;
            return false;
        },
        focusOnThisConcert(onChange, offBox) {
            if(this.isNotFiltered) {
                let id = this.ownId;
                let focusedConcert = document.getElementById(id);
                let focusedImg = focusedConcert.getElementsByTagName('img')[0];
                let focusedText = focusedConcert.querySelector('.text-box');

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
                    EventBus.$emit('offColors', id);
                } else {
                    focusedConcert.classList.add('concert-box');
                    focusedImg.classList.add('bigColorImg');
                    focusedImg.classList.add('blurImg');
                    focusedText.classList.add('hide');
                }
            }
        }
    },
    mounted() {
        EventBus.$on('offColors', (idx) => {
            if(this.ownId !== idx) {
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

.recomended-icon {
    width: 85px;
    height: 75px;
    position: absolute;
    top: 15px;
    left: 15px;
}

.banned-icon {
    width: 180px;
    height: 120px;
    position: absolute;
    left: 28%;
    top: 35%;
    /* transform: translate(-50%, -50%); */
    transition: all 1s;
}

.concert-box:hover .banned-icon {
    transform: rotate(10deg);
}

.hide {
    display: none;
}

.concert-box {
    padding: 3px;
}

.concert-box>img {
    width: 100%;
}

.text-box {
    opacity: 0.75;
    width: 200px;
    transition: all 1s;
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

.img-title {
    position: absolute; 
    text-align: center; 
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
    color: #EAEAEA;
    font-family: 'Roboto', sans-serif;
    font-style: italic;
}

.small-text {
    font-size: 60px; 
    line-height: 60px;
}

.big-text {
    font-size: 85px;
    line-height: 90px;
}

@media screen and (max-width: 1200px) {
    .img-title, .long-text {
        font-size: 40px;
        line-height: 40px;
    }
}

@media screen and (max-width: 600px) {
    .img-title {
        font-size: 80px;
        line-height: 100px;
    }

    .long-text {
        font-size: 75px;
        line-height: 80px;
    }
}
</style>