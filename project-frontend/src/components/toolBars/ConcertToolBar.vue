<template>
    <div>
        <v-toolbar dense color="black">

            <v-app-bar-nav-icon @click="nav_drawer = !nav_drawer">
                <v-icon style="color: white;">dehaze</v-icon>
            </v-app-bar-nav-icon>
            <v-toolbar-title>
                <span class="font-weight-light mr-5">NAVI</span>
            </v-toolbar-title>

            <v-toolbar-items>
                <v-btn text v-for="link in filterLinks()" :key="link.name" router :to="link.route" class="btn-flat waves-effect waves-red pt-4">
                    <login-dialogue v-if="link.text === 'LOG IN'"></login-dialogue>
                    <p v-else class="topBarText" @click="logOut($event)">{{ link.text }}</p>
                </v-btn>
            </v-toolbar-items>

            <div class="flex-grow-1"></div> <!-- 간격 벌리기 -->

            <v-toolbar-items>
                <v-icon class="material-icons small teal-text" @click="searchKeyword">search</v-icon>
                <input type="text" class="search-text" placeholder="찾고자 하는 장르 또는 아티스트" @keydown.enter="searchKeyword"/>
                <p class="btn-flat topBarText pt-2">&copy; MUSIC GHUETTO</p>
            </v-toolbar-items>

        </v-toolbar>

        <v-navigation-drawer v-model="nav_drawer" app temporary width="150px" dark>
            <v-list nav>
                <v-list-item-group active-class="red--text text--accent-4">
                    <div v-if="isLoggedIn">
                        <v-list-item-avatar style="margin-left: 35%;">
                            <v-img :src="imgRequest()">
                                <label>
                                    <input type="file" id="files" ref="files" v-on:change="handleFileUpload()" class="files">
                                </label>
                            </v-img>
                        </v-list-item-avatar>

                        <v-list-item-title class="bar-text">
                            <p v-if="profilePicChanged" @click="imgUpload">프로필 사진 바꾸기</p>
                            로그인된 ID:<br/>{{ userProfile.id }}
                        </v-list-item-title>
                    </div>

                    <div v-else-if="!isLoggedIn">
                        <v-list-item-avatar style="margin-left: 35%;">
                            <v-img src="@/assets/img/background.jpg"></v-img>
                        </v-list-item-avatar>

                        <v-list-item-title class="bar-text">
                            로그인된 ID:<br/>아직 로그인되지 않았습니다.
                        </v-list-item-title>
                    </div>

                    <div> 
                        <v-list-item v-for="(link, index) in filterNavBar()" :key="index" router :to="link.route" @click="btn_needSession(index)">
                            <v-list-item-action>
                                <v-icon>{{ link. icon }}</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                {{ link.text }}
                            </v-list-item-content>
                        </v-list-item>
                    </div>
                </v-list-item-group>
            </v-list>
        </v-navigation-drawer>
    </div>
</template>

<script>
import cookies from 'vue-cookies'
import Vue from 'vue'
Vue.use(cookies)

import axios from 'axios'
import { mapMutations, mapState } from 'vuex'
import LoginDialogue from '@/components/concertMainDialogue/LoginDialogue'

import { links, loggedInlinks, managerLinks, artistLinks, navLinks, navLinksForArtist } from '../../assets/links.js'

export default {
    name: 'ConcertToolBar',
    components: {
        LoginDialogue
    },
    data() {
        return {
            nav_drawer: false,
            files: '',
            profilePicChanged: false,
            links: links,
            loggedInlinks: loggedInlinks,
            managerLinks: managerLinks,
            artistLinks: artistLinks,
            navLinks: navLinks,
            navLinksForArtist: navLinksForArtist
        }
    },
    computed: {
        ...mapState(['isLoggedIn', 'userIdentity', 'userProfile']),
    },
    methods: {
        ...mapMutations(['handleUserLogin']),

        filterLinks() {
            if(!this.isLoggedIn) return this.links;
            else if(this.userIdentity === 'individual') {
                return this.loggedInlinks;
            } else if (this.userIdentity === 'artist') {
                return this.artistLinks;
            } else if (this.userIdentity === 'manager') {
                return this.managerLinks;
            }
        },

        filterNavBar() {
            if(this.userIdentity !== 'artist' || !this.isLoggedIn) {
                return this.navLinks;
            } else if (this.userIdentity === 'artist') {
                return this.navLinksForArtist;
            } 
        },

        imgRequest() {
                try {                
                    return require(`../../../../../project-backend/images/memberPic/${this.userProfile.memberNo}_${this.userProfile.id}.jpg`)
                    
                } catch (e) {
                    return require(`@/assets/logo.png`)
                }
        },

        searchKeyword() {
            this.$router.push({
                name: 'SearchPage',
                //params: { searchText: this.searchText } 
                query: { searchText: this.searchText }
            })
        },

        handleFileUpload() {
            this.files = this.$refs.files.files; //data의 files는 input에서 받는 ref - files의 id files
            this.profilePicChanged = true;
        },

        imgUpload() {

            if(this.isLoggedIn) {
                axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {

                            if(this.files.length > 0) {
                                let formData = new FormData();
                                
                                for(var idx=0; idx < this.files.length; idx++) {
                                    formData.append("memberPic", this.files[idx]);
                                }

                                let memberNo = this.userProfile.memberNo;
                                let id = this.userProfile.id;

                                formData.append("memberNo", memberNo);
                                formData.append("id", id);

                                axios.post('http://localhost:8888/member/sendMemberPic', formData, { //멀티 컨텐츠 날릴때는 무조건 헤더를 이 형식으로 맞춰야한다.
                                    headers: {
                                        'Content-Type': 'multipart/form-data' 
                                    }
                                })
                                .then (res => {
                                    this.responser = res.data;
                                    this.profilePicChanged = false;
                                    alert('프로필 사진이 교체되었습니다!');

                                })
                                .catch (res => {
                                    this.response = res.message;
                                });
                            }
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                            this.$cookies.remove('CurrentUser');
                        }
                    });
            }
        },

        logOut($event) {
            if($event.target.innerHTML === 'LOG OUT') {
                
                this.$cookies.remove("CurrentUser");
                this.handleUserLogin();

                alert('로그아웃되었습니다.');
                this.$router.push ({
                    name: 'MainPage'
                });
            }
        },
        btn_needSession(index) {
            if(index === 0) {
                this.$router.push ({
                    name: 'MainPage'
                });
            } else if(this.isLoggedIn) {
              
                    axios.post('http://localhost:8888/member/needSession')
                    .then(res => {
                        if(res.data) {
                            switch(index) {
                                case 1:
                                    this.$router.push ({
                                        name: 'MyProfilePage'
                                    });
                                    break;
                                case 2:
                                    this.$router.push ({
                                        name: 'LikedListPage'
                                    });
                                    break;
                                case 3:
                                    this.$router.push ({
                                        name: 'BookedListPage'
                                    });
                                    break;
                                case 4:
                                    this.$router.push ({
                                        name: 'MyRequestListPage'
                                    });
                                    break;
                            }
                        } else {
                            alert('세션 정보가 만료되었습니다. 다시 로그인해주세요!');
                            this.handleUserLogin();
                            this.$cookies.remove('CurrentUser');
                        }
                    })
            } else if(index != 0) {
                alert('로그인이 필요한 서비스입니다!');
            }
        }
    },
    watch: {
        nav_drawer() {
            this.profilePicChanged = false;
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,900&display=swap');

.topBarText {
    font-style: italic;
    margin-right: 25px;
    color: rgb(179, 179, 179);
    transition: all 0.4s;
}

.topBarText:hover {
    color: turquoise;
}

.files {
    cursor: pointer;    
}

</style>

<style scoped>

span {
    color: white;
}

.search-text {
    font-size: 13px; 
    font-style: italic; 
    width: 250px; 
    color: white;
}

.bar-text {
    text-align: center; 
    font-size: 10px; 
    line-height: 20px; 
    margin-bottom: 10px;
}

.bar-text>p {
    font-size: 10px;
    cursor: pointer;
}
</style>
        

