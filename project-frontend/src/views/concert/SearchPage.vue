<template>
    <div align="center" class="grey darken-4" style="height: 100%; padding-top: 60px;">

        <h3 class="topBar">SEARCH RESEARCH</h3>
        
        <v-container style="margin-top: 60px;">

            <div class="row" style="justify-content: left; padding-left: 15px;">
                <h5 class="footerText" style="margin-top: 8px;">{{ searchText1 }}</h5>
                <h5 class="description">에 대한 장르 검색결과입니다.</h5>
            </div>

            <table border="1px">
                <tr class="listItem" style="color: grey;">
                    <td width="90">concert</td>
                    <td width="70">artist</td>
                    <td width="70">date</td>
                    <td width="100">venue</td>
                    <td width="30">price</td>
                    <td width="50" style="text-align: right; padding-right: 30px;">details</td>
                </tr>

                <tr v-if="!searchedResults" class="listItem" style="font-size: 12px;">
                    <p style="padding-top: 15px; color: white;">검색된 결과가 없습니다.</p>
                </tr>
                <tr v-else v-for="(sr, idx) in searchedResults" :key="idx" class="listItem" style="font-size: 12px; color: white;">
                    <td>{{ sr.concertName }}</td>
                    <td>{{ sr.concertArtist }}</td>
                    <td>{{ sr.concertDate }}</td>
                    <td>{{ sr.concertVenue }}</td>
                    <td>{{ sr.concertPrice }}</td>
                    <td style="text-align: right"><v-btn class="ma-2" text color="error lighten-2"
                    @click="goToDetailPage(sr.concertNo)">detail</v-btn></td>
                </tr>
            </table>

            <div class="row" style="justify-content: left; padding-left: 15px; margin-top: 80px;">
                <h5 class="footerText" style="margin-top: 8px;">{{ searchText1 }}</h5>
                <h5 class="description">에 대한 아티스트 검색결과입니다.</h5>
            </div>

            <table border="1px">
                <tr class="listItem" style="color: grey;">
                    <td width="90">concert</td>
                    <td width="70">artist</td>
                    <td width="70">date</td>
                    <td width="100">venue</td>
                    <td width="30">price</td>
                    <td width="50" style="text-align: right; padding-right: 30px;">details</td>
                </tr>

                <tr v-if="searchedArtists == ''" class="listItem" style="font-size: 12px;">
                    <p style="padding-top: 15px; color: white;">검색된 결과가 없습니다.</p> <!-- 안나옴 -->
                </tr>
                <tr v-else v-for="(sa, idx) in searchedArtists" :key="idx" class="listItem" style="font-size: 12px; color: white;">
                    <td>{{ sa.concertName }}</td>
                    <td>{{ sa.concertArtist }}</td>
                    <td>{{ sa.concertDate }}</td>
                    <td>{{ sa.concertVenue }}</td>
                    <td>{{ sa.concertPrice }}</td>
                    <td style="text-align: right"><v-btn class="ma-2" text color="error lighten-2"
                    @click="goToDetailPage(sa.concertNo)">detail</v-btn></td>
                </tr>
            </table>
        </v-container>

    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    name: 'SearchPage',
    props: {
        // searchText: {
        //     type: String,
        //     required: true
        // }
    },
    data() {
        return {
            searchText1: ''
        }
    },
    methods: {
        ...mapActions(['fetchSearchedResults', 'fetchSearchedArtists']),

        goToDetailPage(concertNo) {
            this.$router.push({
                name: 'ConcertDetailPage',
                params: { concertNo: concertNo.toString() }
            })
        }
    },
    computed: {
        ...mapState(['searchedResults', 'searchedArtists'])
    },
    mounted() {
        this.searchText1 = this.$route.query.searchText
        this.fetchSearchedResults(this.searchText1)
        this.fetchSearchedArtists(this.searchText1)

        this.$store.state.userProfile = this.$cookies.get("currentUser")

        if(this.$store.state.userProfile.id != '') {

            this.$store.state.isLoggedIn = true
            this.$store.state.userIdentity = this.$store.state.userProfile.identity
        }
    }
}
</script>
