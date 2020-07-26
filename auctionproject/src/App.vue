<template>
    <div id="app">
      <Menu id="nav"> </Menu>
        <router-view></router-view>
    </div>
</template>

<script>

import 'es6-promise/auto';
import Menu from './components/Menu';

export default({
  name: 'App',
  components: {
    'Menu': Menu

  },

  data(){
      return{
        authenticated: false
      }
  },
  methods: {
  },
  computed: {
  }
})

</script>

<style scoped>



</style>

