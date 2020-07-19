<template>
 <form>
     
     <div id="dropdown_position">
        <select v-model="form.position">
             <!-- @change="getRecord('form.position')"> -->
    <option v-for="position in positions" v-bind:key="position.id">{{ position }}</option>
        </select>
     </div>
        <!--<div id="dropdown_name">
        <select v-if="players.length">
                <option v-bind:key="player.label" v-for="player in players">{{ player.name }}</option>
        </select>
     </div> -->
    <button type="submit" href="#">Bid/Nominate</button>
 </form>
</template>
<script>
export default{
    name: 'Lot',
    data() { 
        return {
            positions: ["QB", "RB", "WR", "TE"]
        }
    // methods: {
    //         getRecord: function (position) {
    //   let url = 'http://localhost:8080/api/players/' + position
    //   this.$axios.$get(url)
    //     .then((response) => {
    //       // console.log(response)
    //       // set your form data not sure of the correct form from above but same idea
    //      this.form.ctInvoiceItems = response.data;  // however the response is formatted from Laravel may differ
    //     })
    //     .catch((error) => {
    //       console.log(error.response)
    //     })
    // }
    // }
    }
}

</script>


// <!-- <div class="row lot-text">
//         <input type="text" placeholder="Enter player" name="playerName" required>
//      </div>
//     <div class="row lot-text">
//         <label for="length">Contract length in years:</label>
//             <select name="length" id="length">
//                 <option value="1">1</option>
//                 <option value="2">2</option>
//                 <option value="3">3</option>
//                 <option value="4">4</option>
//                 <option value="5">5</option>
//             </select>
//     </div>
//     <div class="row lot-text">
//         <input type="number" placeholder="Annual salary" required>
//     </div> -->