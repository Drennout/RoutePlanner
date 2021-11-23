import Vue from 'vue'
import Router from 'vue-router'
import newRoute from '../components/new-route'
import baseMap from '../components/base-map'
import boardUser from '../components/boardUser'
import login from '../components/login'
import home from '../components/home'
import register from '../components/register'
import profile from '../components/profile'

Vue.use(Router)

let router = new Router({
    routes: [
        {
            path: '/',
            name: 'home',
            component: home
        },
        {
            path: '/home',
            name: 'home',
            component: home
        },
        {
            path: '/login',
            name: 'login',
            component: login
        },
        {
            path: '/register',
            name: 'register',
            component: register
        },
        {
            path: '/profile',
            name: 'profile',
            component: profile
        },
        {
            path: '/user',
            name: 'user',
            component: boardUser
        },
        {
            path: '/new-route',
            name: 'new-route',
            component: newRoute
        },
        {
            path: '/map',
            name: 'map',
            component: baseMap,
            props: true,
        },
    ]
})

export default router