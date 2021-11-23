import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

let store = new Vuex.Store({
  state: {
    accessToken:process.env.VUE_APP_API_KEY,
    points: [],
    profile: ''
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
    ACCESSTOCKEN(state){
        return state.accessToken
    },
    PROFILE(state){
      return state.profile
    },
  },
});

export default store;
