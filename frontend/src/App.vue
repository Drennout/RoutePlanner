<template>
  <div id="app">
    <main-wrapper />
  </div>
</template>

<script>
import mainWrapper from "./components/main-wrapper"
export default {
  name: 'App',
  components: {
    mainWrapper
  }
}
</script>

<style>
@import "./assets/bootstrap.min.css";
</style>
