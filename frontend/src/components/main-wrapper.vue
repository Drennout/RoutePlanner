<template>
  <div class="main-wrapper">
    <div><nav-app/></div>
    <router-view></router-view>
  </div>
</template>

<script>
import navApp from "./page-components/nav-app";
export default {
  name: "wrapper",
   components: {
    navApp,
  },
};
</script>

<style>
  .main-wrapper{
    margin-top: 72px;
  }
</style>