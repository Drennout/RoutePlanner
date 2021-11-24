<template>
  <div class="container">
    <div class="row" v-if="!successful">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card border-0 shadow rounded-3 my-5">
          <div class="card-body p-4 p-sm-5">
            <h5 class="card-title text-center mb-5 fw-light fs-5">Sign Up</h5>
            <form name="form" @submit.prevent="handleRegister">
                <div class="form-floating mb-3">
                  <input
                    v-model="user.username"
                    type="text"
                    class="form-control"
                    id="username"
                    name="username"
                    placeholder="username"
                  />
                  <label for="username">Username</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    v-model="user.email"
                    type="text"
                    class="form-control"
                    id="email"
                    name="email"
                    placeholder="emial"
                  />
                  <label for="email">Email</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    v-model="user.password"
                    type="password"
                    class="form-control"
                    id="password"
                    name="password"
                    placeholder="Password"
                  />
                  <label for="password">Password</label>
                </div>

                <div class="d-grid">
                  <button
                    class="btn btn-primary btn-block text-uppercase fw-bold"
                    :disabled="loading"
                  >
                    <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                    <span>sign up</span>
                  </button>
                </div>
                <hr class="my-4" />
            </form>
          </div>
        </div>
      </div>
    </div>
    <div>
      <router-link :to="{name: 'login'}">
        <button class="btn btn-primary btn-block text-uppercase fw-bold" :disabled="loading">
          <span v-show="loading" class="spinner-border spinner-border-sm"></span>
          <span>sign in</span>
        </button>
      </router-link>
      <div
        v-if="message"
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
      >{{message}}</div>
    </div>
  </div>
</template>

<script>
import User from "../models/user";

export default {
  name: "register",
  data() {
    return {
      user: new User("", "", ""),
      submitted: false,
      successful: false,
      message: "",
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister() {
      this.message = "";
      this.submitted = true;

      this.$store.dispatch("auth/register", this.user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
        },
        (error) => {
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          this.successful = false;
        }
      );
    },
  },
};
</script>

<style scoped>
.card {
  margin-bottom: 20px;
  border: none;
}

.box {
  width: 500px;
  padding: 40px;
  position: absolute;
  top: 50%;
  left: 50%;
  background: #191919;
  text-align: center;
  transition: 0.25s;
  margin-top: 100px;
}

.box input[type="text"],
.box input[type="password"] {
  border: 0;
  background: none;
  display: block;
  margin: 20px auto;
  text-align: center;
  border: 2px solid #3498db;
  padding: 10px 10px;
  width: 250px;
  outline: none;
  color: white;
  border-radius: 24px;
  transition: 0.25s;
}

.box h1 {
  color: white;
  text-transform: uppercase;
  font-weight: 500;
}

.box input[type="text"]:focus,
.box input[type="password"]:focus {
  width: 300px;
  border-color: #2ecc71;
}

.box input[type="submit"] {
  border: 0;
  background: none;
  display: block;
  margin: 20px auto;
  text-align: center;
  border: 2px solid #2ecc71;
  padding: 14px 40px;
  outline: none;
  color: white;
  border-radius: 24px;
  transition: 0.25s;
  cursor: pointer;
}

.box input[type="submit"]:hover {
  background: #2ecc71;
}

.forgot {
  text-decoration: underline;
}

ul.social-network {
  list-style: none;
  display: inline;
  margin-left: 0 !important;
  padding: 0;
}

ul.social-network li {
  display: inline;
  margin: 0 5px;
}

.social-network a.icoFacebook:hover {
  background-color: #3b5998;
}

.social-network a.icoTwitter:hover {
  background-color: #33ccff;
}

.social-network a.icoGoogle:hover {
  background-color: #bd3518;
}

.social-network a.icoFacebook:hover i,
.social-network a.icoTwitter:hover i,
.social-network a.icoGoogle:hover i {
  color: #fff;
}

a.socialIcon:hover,
.socialHoverClass {
  color: #44bcdd;
}

.social-circle li a {
  display: inline-block;
  position: relative;
  margin: 0 auto 0 auto;
  border-radius: 50%;
  text-align: center;
  width: 50px;
  height: 50px;
  font-size: 20px;
}

.social-circle li i {
  margin: 0;
  line-height: 50px;
  text-align: center;
}

.social-circle li a:hover i,
.triggeredHover {
  transform: rotate(360deg);
  transition: all 0.2s;
}

.social-circle i {
  color: #fff;
  transition: all 0.8s;
  transition: all 0.8s;
}
</style>