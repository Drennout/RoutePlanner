<template>
  <div id="app" class="container">
    <form>
      <h1>Route Addresses</h1>
      <div class="create-rotue">
        <div class="form-row">
          <div class="form-group col-md-6">
            <label>
              <h5>
                <span>City</span>
              </h5>
            </label>
            <input
              v-model="city"
              name="city"
              class="form-control"
              :placeholder="city"
              type="text"
              required
            />
          </div>
        </div>
        <h5>Points</h5>
        <div class="form-row" v-for="(point, index) in points" :key="index">
          <div class="form-group col-md-6">
            <label>Point Address</label>
            <input
              v-model="point.address"
              :name="`points[${index}][address]`"
              type="text"
              class="form-control"
              :placeholder="point.address"
              required
            />
          </div>
          <div class="col-md-6 form-check form-switch">
            <input
              class="form-check-input"
              type="checkbox"
              role="switch"
              id="flexSwitchCheckDefault"
              v-model="point.priority"
              :name="`points[${index}][priority]`"
            />
            <label class="form-check-label" for="flexSwitchCheckDefault">Concrete address</label>
          </div>
          <div class="col-md-6" v-if="checkIndx(index)">
            <button
              @click="removeAddress(index)"
              type="button"
              class="btn btn-secondary"
            >Remove Point</button>
          </div>
        </div>
        <div class="form-group">
          <button @click="addNewAddress" type="button" class="btn btn-secondary">Add Point</button>
        </div>
        <div>
          <h5>Select routing profile</h5>
          <div v-for="(profile, index) in profiles" :key="index">
            <div class="form-check">
              <input
                class="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="flexRadioDefault1"
                v-model="profile.isSelected"
                :value="profile.isSelected"
                @change="changeProfile(index)"
              />
              <label class="form-check-label" for="flexRadioDefault1">{{profile.title}}</label>
            </div>
          </div>
        </div>
      </div>

      <hr />

      <router-link :to="{name: 'map'}">
        <div class="form-group">
          <button @click="submit" type="button" class="btn btn-primary">Build Route</button>
        </div>
      </router-link>
    </form>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import UserService from '../services/user.service'
export default {
  name: "new-route",
  data: () => ({
    profiles: [
      {
        name: "driving",
        title: "Driving",
        isSelected: true,
      },
      {
        name: "cycling",
        title: "Cycling",
        isSelected: false,
      },
      {
        name: "walking",
        title: "Walking",
        isSelected: false,
      },
    ],
    coords: [],
    city: "Москва",
    points: [
      {
        address: "start",
        priority: true,
      },
      {
        address: "end",
        priority: true,
      },
    ],
  }),
  computed: {
    ...mapGetters(["POINTS", "PROFILE"]),
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    ...mapActions(["GET_POINTS", "GET_PROFILE"]),
    checkIndx(index) {
      if (index === this.points.length - 1 || index === 0) return false;
      else return true;
    },
    changeProfile(index) {
      this.profiles.forEach((el) => {
        if (el === this.profiles[index]) el.isSelected = true;
        else el.isSelected = false;
      });
    },
    addNewAddress() {
      this.points.push({
        address: "",
        priority: false,
      });
    },
    removeAddress(index) {
      this.points.splice(index, 1);
    },
    submit() {
      let url = "http://localhost:8000/api/map/geocode?request="; //1,Москва Кировоградская улица, 22;2,starbucks;2,макдональдс;1,МИРЭА пр вернадского 78;&city=Москва"
      let addresses = "";
      let addressessNoBoolInt = ""
      this.points.forEach((element) => {
        let bool_int = element.priority ? 1 : 2;
        addressessNoBoolInt += element.address + ";"
        addresses += bool_int += "," + element.address + ";";
      });
      url += addresses + "&city=" + this.city;

      async function getPoints(points, url) {
        const query = await fetch(url, { method: "GET" });
        const json = await query.json();
        json.points.forEach((el) => {
          points.push([el.lon, el.lat]);
        });
      }
      getPoints(this.coords, url);
      this.GET_POINTS(this.coords);
      console.log(this.POINTS);
      this.profiles.forEach((el) => {
        if (el.isSelected) this.GET_PROFILE(el.name);
      });

      if (this.currentUser) {
        console.log( addresses + "&city=" + this.city)
        console.log( addresses)
        
        UserService.postUserRoute(this.currentUser.id, addresses + "&city=" + this.city, addressessNoBoolInt, this.PROFILE)
      }
    },
  },
};
</script>
  
<style>
.create-rotue > div {
  margin: 20px 0;
  padding-bottom: 10px;
}
.create-rotue > div:not(:last-child) {
  border-bottom: 1px solid rgb(206, 212, 218);
}
</style>