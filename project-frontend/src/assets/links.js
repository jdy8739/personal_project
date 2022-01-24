const links = [
    {
        text: 'ABOUT US',
        icon: 'people',
        route: '/aboutUs',
    },
    {
        text: 'LOG IN',
        icon: '/people'
    },
    {
        text: 'SIGN UP',
        icon: 'people',
        route: '/signup',
    },
    {
        text: 'SUPPORT',
        icon: 'people',
        route: '/preference',
    },
    {
        text: 'COMMUNITY',
        icon: 'people',
        route: '/community',
    },
    {
        text: 'MORE',
        icon: 'people',
        route: '/indieNewsCrawler',
    }
]

const loggedInlinks = [
    {
        text: 'LOG OUT',
        icon: ''           
    },
    {
        text: 'ABOUT US',
        icon: 'people',
        route: '/aboutUs',
    },
    {
        text: 'SUPPORT',
        icon: 'people',
        route: '/preference',
    },
    {
        text: 'COMMUNITY',
        icon: 'people',
        route: '/community',
    },
    {
        text: 'MORE',
        icon: 'people',
        route: '/indieNewsCrawler',
    }
]

const managerLinks = [
    {
        text: 'LOG OUT',
        icon: '',
    },
    {
        text: 'ABOUT US',
        icon: 'people',
        route: '/aboutUs',
    },
    {
        text: 'REQUEST STORE', 
        icon: 'people',
        route: '/request/store', 
    },
    {
        text: 'COMMUNITY',
        icon: 'people',
        route: '/community',
    },
    {
        text: 'ADMIN',
        icon: 'people',
        route: '/memberList',
    }
]

const artistLinks = [
    {
        text: 'LOG OUT',
        icon: '',
    },
    {
        text: 'ABOUT US',
        icon: 'people',
        route: '/aboutUs',
    },
    {
        text: 'SUPPORT',
        icon: 'people',
        route: '/preference',
    },
    {
        text: 'COMMUNITY',
        icon: 'people',
        route: '/community',
    },
    {
        text: 'CONCERT REG',
        icon: 'people',
        route: '/register/request',
    }
]

const navLinks = [
    { text: 'Home', icon: 'home'},
    { text: 'Profile', icon: 'person_outline'},
    { text: 'Liked', icon: 'star'},
    { text: 'Booked', icon: 'done'}
]

const navLinksForArtist = [
    { text: 'Home', icon: 'home'},
    { text: 'Profile', icon: 'person_outline'},
    { text: 'Liked', icon: 'star'},
    { text: 'Booked', icon: 'done'},
    { text: 'Request', icon: 'email'}
]

export { links, loggedInlinks, managerLinks, artistLinks, navLinks, navLinksForArtist };