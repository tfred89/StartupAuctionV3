<template>
  <div>
    <b-container fluid class="table-page">
        <div>
          <b-container fluid class="team-select-row">
            <span> Select another team: &nbsp; &nbsp; </span>
            <b-form-select class="col-sm-4" id="ownerSelector" v-model="selectedOwnerName" @change="showARoster">
                <b-form-select-option v-for="owner in ownerList" :key="owner.ownerId" :value="owner.ownerName"> 
                    {{ owner.ownerName }} </b-form-select-option>
            </b-form-select>
          </b-container>
        </div>
            <div id="table"> 
            <b-table striped hover :items="playerList" :fields="rows">
            </b-table>
        </div>
    </b-container>
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

.team-select-row{
    width: 100%;
    margin: 25px;
}

.table-page{
    width: 85%;
}



</style>
