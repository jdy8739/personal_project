<template>
    <div class="grey darken-4" style="height: 100%; position: relative;">
        <br>
        <div align="center" style="margin-top: -20px;">
            <img class="responsive-img bigImg" src="@/assets/img/wall2.jpg" width="100%" style="margin-bottom: -6px;">
        </div>
        <hr>
        <div class="row" > 
            <genre-dialogue style="margin-top: 12px;" class="col xs4 sm4 md4 lg4"/>    
            <artist-dialogue class="col xs4 sm4 md4 lg4"/> 
            <date-dialogue class="col xs4 sm4 md4 lg4"/>                        
        </div>
        <div>
          <concert-row-1 v-for="(concerts, i) in concertList" :key="i" :rowIndex="i" :concerts="concerts" class="concert-wrapper"/>
        </div>
        <p style="color: white;">{{ taste }}</p>
        <main-page-footer/>
    </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import GenreDialogue from '@/components/concertMainDialogue/GenreDialogue'
import ArtistDialogue from '@/components/concertMainDialogue/ArtistDialogue'
import DateDialogue from '@/components/concertMainDialogue/DateDialogue'
import ConcertRow1 from '@/components/concertMainPage/ConcertRow1'
import MainPageFooter from '@/components/concertMainPage/MainPageFooter'

export default {
    name: 'MainPage',
    components: {
        GenreDialogue,
        ArtistDialogue,
        DateDialogue,
        ConcertRow1,
        MainPageFooter
    },
    data() {
        return {
            
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile', 'userIdentity', 'taste', 'dateForFilter', 'concertList'])
    },
    methods: {
        ...mapActions(['fetchTaste', 'fetchUnlockedConcertList']),
        ...mapMutations(['handleUserLogin']),
    },
    mounted() {
        this.fetchUnlockedConcertList();

        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.handleUserLogin(userInfo);
            this.fetchTaste(this.userProfile.memberNo);
        } 
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap');

.main-bg {
    height: 100%;
    padding-top: 60px;
    color: white;
}

.topBar {
    font-style: italic;
    color: white;
}

.inputBar {
    font-style: italic;
    font-size: 8;
    color: white;
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
    line-height: 40px;
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
    line-height: 40px;
}


.bigImg {
    filter: blur(1px) grayscale(100%); 
    opacity: 0.6;
}

.box {
    position: fixed;
    bottom: 50px;
    right: 50px;

    width: 200px;
    height: 50px;
    box-sizing: border-box;
    padding: 15px;
    background: white;
    display: inline-block;
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

.concert-wrapper {
    display: flex;
    flex-wrap: wrap;
    max-width: 2200px;
    margin: auto;
}


@media screen and (max-width: 1580px) {
    .location {
        font-size: 20px;
    }
    .date {
        font-size: 20px;
    }
}
</style>


