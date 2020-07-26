<template>
  <div>
    <div>
        <label for="ownerSelector"> Select another team: </label>
        <select id="ownerSelector" v-model="selectedOwnerName" @change="showARoster">
            <option v-for="owner in ownerList" :key="owner.ownerId"> {{ owner.ownerName }} </option>
        </select>
    </div>
    <div id="table"> 

      <b-table striped hover :items="playerList" :fields="rows">
      </b-table>

    </div>
  </div>
</template>

<script>



export default {
    name: 'Rosters',
    data() {
        return {
            ownerList: [],
            playerList: [],
            selectedOwnerName: '',
            rows: [
                {
                    key: 'firstName',
                    label: 'First Name',
                    sortable: true
                },
                {
                    key: 'lastName',
                    label: 'Last Name',
                    sortable: true
                },
                {
                    key: 'position',
                    label: 'Position',
                    sortable: true
                },
                {
                    key: 'salary',
                    label: 'Annual Salary',
                    sortable: true
                },
                {
                    key: 'length',
                    label: 'Contract Length (Years)',
                    sortable: true
                },
                {
                    key: 'contractValue',
                    label: 'Contract Value',
                    sortable: true
                }
            ]
        }
    },
    components:{

    },
    methods: {
        showMyRoster(){
            fetch('http://localhost:8080/api/team/' + this.currentUser.user.ownerName, {
                method: 'GET'
            })
            .then(response => response.json())
            .then(data => (this.playerList = data));
        },
        showARoster(){
            fetch('http://localhost:8080/api/team/' + this.selectedOwnerName, {
                method: 'GET'
            })
            .then(response => response.json())
            .then(data => (this.playerList = data));
        },
        getOwnerList: function() {
           fetch('http://localhost:8080/api/owner/scoreboard/', {
               method: 'GET'
           })
            .then(response => response.json())
            .then(data => (this.ownerList = data));
        }
    },
    mounted: function() {
        this.showMyRoster();
        this.getOwnerList();
    },
    computed: {
        currentUser: function() {
            return this.$store.state.auth.user;
        }
    }

}

</script>

<style>






</style>
