import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import MainPage from '../views/concert/MainPage.vue'
import ConcertDetailPage from '../views/concert/ConcertDetailPage.vue'
import LikedListPage from '../views/concert/LikedListPage.vue'

import SignupPage from '../views/member/SignupPage.vue'
import PreferenceFillInPage from '../views/member/PreferenceFillInPage.vue'
import MemberListPage from '../views/member/authAdmin/MemberListPage.vue'
import MemberDeletePage from '../views/member/authAdmin/MemberDeletePage.vue'
import MyProfilePage from '../views/member/MyProfilePage.vue'
import MemberModifyPage from '../views/member/MemberModifyPage.vue'
import MemberWithdrawalPage from '../views/member/MemberWithdrawalPage.vue'

import IndieNewsCrawlerPage from '../views/crawling/IndieNewsCrawlerPage.vue'

import CommunityPage from '../views/community/CommunityPage.vue'
import CommunityWritePage from '../views/community/CommunityWritePage.vue'
import CommunityReadPage from '../views/community/CommunityReadPage.vue'
import CommunityModifyPage from '../views/community/CommunityModifyPage.vue'

import RegisterRequestPage from '../views/member/authArtist/RegisterRequestPage.vue'
import MyRequestListPage from '../views/member/authArtist/MyRequestListPage.vue'
import MyRequestReadPage from '../views/member/authArtist/MyRequestReadPage.vue'
import MyRequestModifyPage from '../views/member/authArtist/MyRequestModifyPage.vue'
import RequestStorePage from '../views/member/authAdmin/RequestStorePage.vue'

import BookedListPage from '../views/concert/BookedListPage.vue'
import SearchPage from '../views/concert/SearchPage.vue'
import ExceptionPage from '../views/ExceptionPage.vue';
import ConcertManagePage from '../views/member/authAdmin/ConcertManagePage';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/main',
    name: 'MainPage',
    component: MainPage
  },
  {
    path: '/concert_detail/:concertNo',
    name: 'ConcertDetailPage',
    components: {
      default: ConcertDetailPage 
    },
    props: {
      default: true
    }
  },
  {
    path: '/likedList',
    name: 'LikedListPage',
    component: LikedListPage
  },
  {
    path: '/signup',
    name: 'SignupPage',
    component: SignupPage
  },
  {
    path: '/preference',
    name: 'PreferenceFillInPage',
    components: {
      default: PreferenceFillInPage
    }
  },
  {
    path: '/memberList',
    name: 'MemberListPage',
    component: MemberListPage
  },
  {
    path: '/memberDelete/:memberNo',
    name: 'MemberDeletePage',
    components: {
      default: MemberDeletePage
    },
    props: {
      default: true
    }
  },
  {
    path: '/myProfile',
    name: 'MyProfilePage',
    component: MyProfilePage
  },
  {
    path: '/member/modify',
    name: 'MemberModifyPage',
    component: MemberModifyPage
  },
  {
    path: '/member/withdrawal',
    name: 'MemberWithdrawalPage',
    components: {
      default: MemberWithdrawalPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/indieNewsCrawler',
    name: 'IndieNewsCrawlerPage',
    component: IndieNewsCrawlerPage
  },
  {
    path: '/community',
    name: 'CommunityPage',
    component: CommunityPage
  },
  {
    path: '/community/write',
    name: 'CommunityWritePage',
    components: {
      default: CommunityWritePage
    }
  },
  {
    path: '/community/read/:boardNo',
    name: 'CommunityReadPage',
    components: {
      default: CommunityReadPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/community/modify/:boardNo', 
    name: 'CommunityModifyPage',
    components: {
      default: CommunityModifyPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/register/request',
    name: 'RegisterRequestPage',
    component: RegisterRequestPage
  },
  {
    path: '/request/store',
    name: 'RequestStorePage',
    component: RequestStorePage
  },
  {
    path: '/my_request/list',
    name: 'MyRequestListPage',
    component: MyRequestListPage
  },
  {
    path: '/my_request/read/:concertRequestNo',
    name: 'MyRequestReadPage',
    components: {
      default: MyRequestReadPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/myRequest/modify/:concertRequestNo',
    name: 'MyRequestModifyPage',
    components: {
      default: MyRequestModifyPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/bookedList',
    name: 'BookedListPage',
    component: BookedListPage
  },
  {
    path: '/search',
    name: 'SearchPage',
    components: {
      default: SearchPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/:exception',
    name: 'ExceptionPage',
    component: ExceptionPage
  },
  {
    path: '/concert/manage',
    name: 'ConcertManagePage',
    component: ConcertManagePage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
