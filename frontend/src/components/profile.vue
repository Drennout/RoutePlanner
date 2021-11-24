<template>
  <div class="container">
    <h3>Your routes</h3>
    <div class="d-flex justify-content-between">
      <div class="flex-column col-md-6">
        <ul class="list-group" v-for="(route, index) in routes" :key="index">
          <li class="list-group-item d-flex justify-content-between align-items-center">
            {{route.points}}
            <span class="badge bg-primary rounded-pill">{{route.id}}</span>
          </li>
          <div class="row">
  
              <router-link :to="{name: 'map'}"  class="d-grid gap-2 col-md-6">
                <button class="btn btn-primary" type="button" @click="buildRoute(route)">Build Route</button>
              </router-link>
            <div class="d-grid gap-2 col-md-6">
              <button
                class="btn btn btn-dark"
                type="button"
                @click="deleteRoute(route.id)"
              >Delete Route</button>
            </div>
          </div>
        </ul>
      </div>
      <div>
        <div class="d-grid gap-2">
          <button class="btn btn-primary" type="button" @click="logOut">LogOut</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";
import { mapActions } from "vuex";
export default {
  name: "profile",
  data: () => {
    return {
      routes: [],
      points: [],
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    ...mapActions(["GET_POINTS", "GET_PROFILE"]),
    buildRoute(route) {
      let url =
        "http://localhost:8000/api/map/geocode?request=" + route.request;
      async function getPoints(points, url) {
        const query = await fetch(url, { method: "GET" });
        const json = await query.json();
        json.points.forEach((el) => {
          points.push([el.lon, el.lat]);
        });
      }

      getPoints(this.points, url);
      this.$store.state.points = this.points;
      console.log(this.points);
      console.log(this.$store.state.points);
      this.GET_PROFILE(route.profile);
    },
    deleteRoute(routeId) {
      let temp = [];
      this.routes.forEach((el) => {
        if (el.id != routeId) temp.push(el);
      });

      this.routes = temp;

      UserService.deleteUserRoute(routeId);
      // this.$$forceUpdate()
    },
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },

    getRoutes(id) {
      UserService.getUserRoute(id).then(
        (response) => {
          this.routes = response.data;
        },
        (error) => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }

    this.getRoutes(this.currentUser.id);
  },
};
</script>

<style>
</style>