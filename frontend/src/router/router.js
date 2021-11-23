import Vue from 'vue'
import Router from 'vue-router'
import mainPage from '../components/main-page'
import newRoute from '../components/new-route'
import baseMap from '../components/base-map'
Vue.use(Router)

let router = new Router({
    routes: [
        {
            path: '/home',
            name: 'main-page',
            component: mainPage
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