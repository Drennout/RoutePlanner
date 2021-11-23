import Vue from "vue";
import Vuex from "vuex";
import {apiKey} from './api_key.module'
import {auth} from './auth.module'
Vue.use(Vuex);

let store = new Vuex.Store({
  state: {
    points: [],
    profile: '',
  },
  modules: {
    apiKey,
    auth,
  },
  mutations: {
    SET_POINTS: (state, data) => {
      state.points = data
    },
    SET_PROFILE: (state, data) => {
      state.profile = data
    }
  },
  actions: {
    GET_POINTS({commit}, data){
      commit('SET_POINTS', data)
    },
    GET_PROFILE({commit}, profile){
      commit('SET_PROFILE', profile)
    }
  },
  getters: {
    POINTS(state){
      return state.points
    },
    // ACCESSTOCKEN(state){
    //     return state.accessToken
    // },
    PROFILE(state){
      return state.profile
    },
  },
});

export default store;
