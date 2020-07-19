<template>
  <div>
      <b-table striped hover :items="ownerList" :fields="fields">
      </b-table>
  </div>
</template>

<script>
export default {

    data() {
        return{
            ownerList: [],
            fields: [
                {
                    key: 'ownerName',
                    label: "Owner",
                    sortable: true
                },
                {
                    key: "capRoom",
                    label: "Cap Space",
                    sortable: true
                },
                {
                    key: "yearsLeft",
                    label: "Years Left",
                    sortable: true
                }
            ]
        }
    },
    mounted: function() {
        this.showScore();
    },
    watch: {
        tableBuilder: function () {
            this.showScore();
        }
    },
    methods: {
        showScore: function() {
           fetch('http://localhost:8080/api/owner/scoreboard/', {
               method: 'GET'
           })
            .then(response => response.json())
            .then(data => (this.ownerList = data));
        }
    }
}
</script>

<style>

</style>