<template>
  <div>
    <div id="mapContainer" class="base-map" @load:="onMapLoaded" @:click="onMapClick"></div>
  </div>
</template>

<script>
import mapboxgl from "mapbox-gl";
import { mapGetters } from "vuex";

export default {
  name: "base-map",
  data() {
    return {
      center: [37.61778, 55.75583],
      map: null,
      route_json: null,
      query: null,
      points: null,
    };
  },
  computed: {
    ...mapGetters(["POINTS", "PROFILE"]),
  },
  methods: {
    initMap() {
      mapboxgl.accessToken = this.$store.state.apiKey.value;
      this.map = new mapboxgl.Map({
        container: "mapContainer",
        style: "mapbox://styles/mapbox/streets-v11",
        center: this.center, // starting position
        zoom: 12,
      });
      // set the bounds of the map

      this.map.on("load", () => {
        this.points = this.POINTS
        console.log(this.POINTS)
        while(this.points === undefined){
          console.log('undef')
        }
        this.map.jumpTo({ center: this.points[0] });
        this.points.forEach((el) => {
          const marker = new mapboxgl.Marker();
          marker.setLngLat([el[0], el[1]]);
          marker.addTo(this.map);
        });
        async function getRoute(start, end, i, map, profile) {
          const url = `http://localhost:8000/api/map/direction?lon1=${start[0]}&lat1=${start[1]}&lon2=${end[0]}&lat2=${end[1]}&routingProfile=${profile}`;
          const query = await fetch(url, { method: "GET" });
          const json = await query.json();
          const data = json.routes[0];
          const route = data.geometry.coordinates;
          const geojson = {
            type: "Feature",
            properties: {},
            geometry: {
              type: "LineString",
              coordinates: route,
            },
          };
          if (map.getSource("route" + i)) {
            console.log("route" + i);
            map.getSource("route" + i).setData(geojson);
          } else {
            map.addLayer({
              id: "route" + i,
              type: "line",
              source: {
                type: "geojson",
                data: geojson,
              },
              layout: {
                "line-join": "round",
                "line-cap": "round",
              },
              paint: {
                "line-color": "#3887be",
                "line-width": 5,
                "line-opacity": 0.75,
              },
            });
          }
        }
        for (var i = 0; i < this.points.length - 1; i++) {
          const start = this.points[i];
          const end = this.points[i + 1];
          getRoute(start, end, i, this.map, this.PROFILE);
        }
      });
    },
    onMapLoaded() {
      console.log("load");
    },
    onMapClick() {
      console.log("click");
    },
  },
  mounted() {
    this.initMap()
  },
};
</script>

<style scoped>
#mapContainer{
  margin-top: 0
}
.mapboxgl-map {
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>
