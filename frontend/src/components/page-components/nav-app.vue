<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Route Planner</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarColor01"
        aria-controls="navbarColor01"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav me-auto">
          <div v-for="(page, index) in pages" :key="index">
            <li class="nav-item">
              <a class="nav-link" :href="'#/' + page.name">{{page.title}}</a>
            </li>
          </div>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "nav-app",
  data: () => {
    return {
      pages: [
        {
          name: "home",
          title: "Home",
          isActive: true,
        },
        {
          name: "new-route",
          title: "New Route",
          isActive: false,
        },
        {
          name: "about",
          title: "About",
          isActive: false,
        },
        {
          name: "login",
          title: "Login",
          isActive: false,
        },
      ],
    };
  },
};
</script>

<style scoped>
nav {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 50;
}
</style>
