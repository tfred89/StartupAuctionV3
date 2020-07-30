<template>
  <div>
      <b-table striped hover :items="ownerList" :fields="rows">
      </b-table>
  </div>
</template>

<script>
export default {

    data() {
        return{
            url: 'http://localhost:8080/',
            ownerList: [],
            rows: [
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
           fetch(this.url + 'api/owner/scoreboard/', {
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