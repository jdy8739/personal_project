import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import MainPage from '../views/concert/MainPage.vue'
import ConcertDetailPage from '../views/concert/ConcertDetailPage.vue'
import LikedListPage from '../views/concert/LikedListPage.vue'
import AboutUsPage from '../views/concert/AboutUsPage.vue'

import SignupPage from '../views/member/SignupPage.vue'
import PreferenceFillInPage from '../views/member/PreferenceFillInPage.vue'
import MemberListPage from '../views/member/MemberListPage.vue'
import MemberDeletePage from '../views/member/MemberDeletePage.vue'
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
import RequestReadPage from '../views/member/authAdmin/RequestReadPage.vue'

import BookedListPage from '../views/concert/BookedListPage.vue'
import BookedAlterPage from '../views/concert/BookedAlterPage.vue'

import SearchPage from '../views/concert/SearchPage.vue'

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
    path: '/concertDetail/:concertNo',
    name: 'ConcertDetailPage',
    components: {
      default: ConcertDetailPage //default쓸거면 components --> s를 붙여줘야된다! props받는데 필요한듯
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
    path: '/community/write', //뒤에 /~~ 가 오면 화면 전환이 안되는것이다. 질문해야할듯
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
    path: '/community/modify/:boardNo', // /:boardNo을 붙이면 그 화면에서 다른 화면으로 가는 버튼이 안먹히고.. 무조건 나중에 수정하자
    name: 'CommunityModifyPage',
    components: {
      default: CommunityModifyPage
    },
    props: {
      default: true
    }
  },
  {
    path: '/aboutUs',
    name: 'AboutUsPage',
    component: AboutUsPage
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
    path: '/request/read/:concertRequestNo',
    name: 'RequestReadPage',
    components: {
      default: RequestReadPage,
    },
    props: {
      default: true
    }
  },
  {
    path: '/myRequest/list',
    name: 'MyRequestListPage',
    component: MyRequestListPage
  },
  {
    path: '/myRequest/read/:concertRequestNo',
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
    path: '/bookedAlter/:bookedConcertNo',
    name: 'BookedAlterPage',
    components: {
      default: BookedAlterPage
    },
    props: {
      default: true
    }
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
