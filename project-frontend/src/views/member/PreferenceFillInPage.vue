<template>
    <div align="center" class="main-bg grey darken-4">
        <p class="topBar">PRFERENCE</p>
        <p class="description">공연을 선택하는데 어려움이 있나요??? 고객님의 취향에 맞게 장르와 아티스트를 선택해보세요! :)</p>
        <preference-box v-if="isLoggedIn && notDecidedYet" @complete="onComplete" class="container"/>
        <p v-else-if="!isLoggedIn" class="footerText">로그인이 필요한 서비스입니다! 로그인해주세요 :)</p>
        <br>
        <div v-if="isLoggedIn && !notDecidedYet">
            <p class="footerText">이미 이전에 취향을 정하셨어요! 다시 하실건가요???</p>
            <div>
                <v-btn class="ma-1 footerText" x-large color="error" plain @click="confirm">
                    네!
                </v-btn>
                <v-btn class="ma-1 footerText" x-large color="error" plain @click="goBack">
                    BACK
                </v-btn>
            </div>
        </div>
        <div v-if="isLoggedIn && notDecidedYet" class="description">
            <p>선택하신 사항에 따라 좋아하실만한 공연을 추천해드립니다!</p>
            <p>편하게 선택해주세요~</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

import { mapActions, mapState } from 'vuex'
import PreferenceBox from '@/components/memberPage/PreferenceBox'

export default {
    name: 'PreferenceFillInPage',
    components: {
        PreferenceBox
    },
    computed: {
        ...mapState(['isLoggedIn', 'userProfile', 'notDecidedYet'])
    },
    methods: {
        ...mapActions(['fetchAlreadyDecidePrefOrNot']),

        onComplete(payload) {
            
            const memberNo = this.userProfile.memberNo;
            const { chosenGenres, speedTaste, atmosphereTaste, chosenArtists } = payload;

            axios.post('http://localhost:8888/member/taste/postTaste', { memberNo, chosenGenres, speedTaste, atmosphereTaste, chosenArtists })
                .then(() => {
                    alert('취향이 등록되었습니다! 빠른 시일 내에 반영시키도록하겠습니다. :)');

                    this.$router.push({
                        name: 'MainPage'
                    });
                })
        },
        goBack() {
            this.$router.push({
                name: 'MainPage'
            });
        },
        confirm() {
            this.$store.commit('FETCH_DECIDE_OR_NOT', true);
        }
    },
    mounted() {
        if(this.$cookies.isKey('CurrentUser')) {
            const userInfo = this.$cookies.get('CurrentUser');
            this.$store.commit('handleUserLogin', userInfo);

            this.fetchAlreadyDecidePrefOrNot(this.userProfile.memberNo);
        }
    }
}
</script>

<style>

.genreText {
    font-size: 25px;
    padding-top: 15px;
}

</style>