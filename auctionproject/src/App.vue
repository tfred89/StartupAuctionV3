<template>
  <div id="app">

    <!-- <b-col sm="6" offset="3">   -->
      <BudgetBoard @newWinner="checkScore" :showScore="wins" id="budget-board"></BudgetBoard>
      <PlayerDropdown lotId="1" class="lotStyle" id="lot1" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="2" class="lotStyle" id="lot2" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="3" class="lotStyle" id="lot3" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="4" class="lotStyle" id="lot4" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="5" class="lotStyle" id="lot5" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="6" class="lotStyle" id="lot6" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="7" class="lotStyle" id="lot7" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="8" class="lotStyle" id="lot8" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="9" class="lotStyle" id="lot9" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="10" class="lotStyle" id="lot10" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="11" class="lotStyle" id="lot11" v-bind:ownerList="this.owners"></PlayerDropdown>
      <PlayerDropdown lotId="12" class="lotStyle" id="lot12" v-bind:ownerList="this.owners"></PlayerDropdown>

    <!-- </b-col> -->
  </div>
</template>

<script>
import PlayerDropdown from './components/PlayerDropdown.vue';
import BudgetBoard from './components/BudgetBoard.vue';
export default {
  name: 'App',
  components: {
    'PlayerDropdown': PlayerDropdown,
    'BudgetBoard': BudgetBoard

  },

  data(){
      return{
        wins: 0,
        owners: []
      }
  },
  mounted: {
        showScore: function() {
           fetch('http://localhost:8080/api/owner/scoreboard/', {
               method: 'GET'
           })
            .then(response => response.json())
            .then(data => (this.owners = data));
        }
    },
  methods: {
    checkScore: function(){
      this.wins++;
    }
  },
  computed: {

  },
}

</script>

<style scoped>
  #app {
    display: grid;
    grid-template-columns: 1fr, 1fr, 1fr, 1fr;
    grid-template-areas: 
      "nav nav nav nav"
      " bb lot1 lot2 . "
      " bb lot3 lot4 . "
      " bb lot5 lot6 . "
      " bb lot7 lot8 . "
      " bb lot9 lot10 . "
      " bb lot11 lot12 . ";
    grid-gap: 35px;
  }
  #lot1{ 
    grid-area: lot1;
  }
  #lot2{ 
    grid-area: lot2;
  }
  #lot3{ 
    grid-area: lot3;
  }
  #lot4{ 
    grid-area: lot4;
  }
  #lot5{ 
    grid-area: lot5;
  }
  #lot6{ 
    grid-area: lot6;
  }
  #lot7{ 
    grid-area: lot7;
  }
  #lot8{ 
    grid-area: lot8;
  }
  #lot9{ 
    grid-area: lot9;
  }
  #lot10{ 
    grid-area: lot10;
  }
  #lot11{ 
    grid-area: lot11;
  }
  #lot12{ 
    grid-area: lot12;
  }
  #budget-board{
    grid-area: bb;
    position: sticky;
  }
.lotStyle{
    border-radius: 9px;
    padding: 20px;
    outline: 1px dashed rgb(93, 107, 93);
  
    outline-offset: -5px;
    background: lightgrey;
    margin-top: 20px;
    box-shadow: 5px 5px 5px rgb(101, 102, 102);
}

#app{
  margin-right: 35px;
}


</style>

