<template>
  <b-navbar toggleable type="dark" variant="dark">
    
    <b-navbar-brand href="#">Cap'n Crunch</b-navbar-brand>

    <b-navbar-toggle target="navbar-toggle-collapse">
      <template v-slot:default="{ expanded }">
        <div v-if="expanded"><font-awesome-icon icon="chevron-up" /></div>
        <div v-else><font-awesome-icon icon="chevron-down" /></div>
      </template>
    </b-navbar-toggle>

    <b-collapse id="navbar-toggle-collapse" is-nav>
      <b-navbar-nav class="ml-auto">

          <b-nav-item><router-link to="/home">Home</router-link></b-nav-item>

           <b-nav-item><router-link to="/rosters">Rosters</router-link></b-nav-item>

           <b-nav-item><router-link to="/login">Log in</router-link></b-nav-item>

          <b-nav-item><router-link to="/register">Register</router-link></b-nav-item>


      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: 'Menu',
  props: {
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN');
      }

      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_MODERATOR');
      }

      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
