<template>
  <div id="lot-element">
    <form>
        <div id="playerInfoBanner" v-show="bidMode"> 
            <h2 id="player-title"> {{ selectedPlayer.firstName }} {{ selectedPlayer.lastName }} </h2>
            <!-- <p> Highest Bid: ${{ currentBid.bidSalary}}, {{ currentBid.bidLength }} years </p>  
            <p> Highest Bidder: {{ currentBid.bidderId }}  </p> -->
            <b-container class="current-bid-row">
                <b-row>
                    <b-col id="bid-title-row">
                        <div id="bid-title"> HIGHEST BID </div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col>
                        <div> SALARY: ${{ currentBid.bidSalary}} </div>
                    </b-col>
                    <b-col>
                        <div> {{ currentBid.bidLength }} YEARS </div>
                    </b-col>
                    <b-col>
                        <div> {{ currentOwner.ownerName }} </div>
                    </b-col>
                </b-row>
                <b-row>
                    <p>  </p>
                </b-row>
                <b-row>
                    <p>  </p>
                </b-row>
            </b-container>
        </div>
        <div id="posSelector" v-show="nomMode">
            <span>Choose a position</span><b-form-select v-model="selectedPos" class="mb-3" @change="lookupPos">
                <b-form-select-option :value="null">Please select a position</b-form-select-option>
                <b-form-select-option value="QB">QB</b-form-select-option>
                <b-form-select-option value="RB">RB</b-form-select-option>
                <b-form-select-option value="WR">WR</b-form-select-option>
                <b-form-select-option value="TE">TE</b-form-select-option>
            </b-form-select>
        </div>
        <div id="playerSelector" v-show="nomMode">
            <span>Choose a player</span>
            <b-form-select v-model="selectedPlayerId"  class="mb-3" @change="lookupPlayer">
                <b-form-select-option :value="null">Choose a player</b-form-select-option>
                <b-form-select-option v-for="playerObject in playerObjectList" :key="playerObject.playerId" :value="playerObject.playerId">
                    {{ playerObject.firstName }} {{ playerObject.lastName }}</b-form-select-option>
            </b-form-select>
        </div>
        <!-- <p> Selected Player id: {{ selectedPlayerId }} {{ selectedPlayer.playerId }} </p> -->
        <!--  <p> selected Player id: {{ selectedPlayer.playerId }} </p>
        <p> selected Player espnID: {{ selectedPlayer.espnId }} </p> -->
        <b-container fluid>
            <b-row class="salaryRow">

                    <span style="margin-right:2em"> Salary per season:  &nbsp; &nbsp;        </span>


                <input id ="salarySelector" type="number" v-model.number="salaryInput" :min="1" :max="500" />

            </b-row>
            <b-row class="lengthRow">
                <b-form-select id="lengthSelector" v-model="lengthInput" class="mb-3">
                <b-form-select-option :value="null">Select number of contract years </b-form-select-option>
                <b-form-select-option value="1">1</b-form-select-option>
                <b-form-select-option value="2">2</b-form-select-option>
                <b-form-select-option value="3">3</b-form-select-option>
                <b-form-select-option value="4">4</b-form-select-option>
                <b-form-select-option value="5">5</b-form-select-option>
                </b-form-select>    
            </b-row>
        </b-container>
        <b-container>
            <b-row id="button-row">
                    <b-button variant="outline-primary" v-bind:disabled="!nomFormIsValid" @click="submitNomination">Nominate</b-button>
                    <b-button variant="danger" v-bind:disabled="!bidFormIsValid" @click="submitBid">Bid</b-button>
                    <b-button v-bind:disabled="true">Pass</b-button>
            </b-row>
        </b-container>
    </form>
    <div id="clock" v-show="bidMode"> 
         <Counter @timeExpired="winPlayer" :updateClock="loaded" :year="bidYear" :month="bidMonth" :date="bidDate" :hour="bidHour" 
         :minute="bidMinute" :second="bidSecond" :millisecond="bidMillisecond"></Counter>
    </div>
  </div>
</template>

<script>
import Counter from '@/components/Counter.vue';
export default {
    components: {
        Counter
    },
    props: ['lotId', 'ownerList']
    ,
    mounted:  function() {
        const requestOptions = {
               method: 'GET',
               headers: {
                    "Content-Type": "application/json",
                    "Authorization" : `Bearer ${this.$cookies.get('token')}`
                    }
                };
        fetch('http://localhost:8080/api/owner/persist', requestOptions)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                this.jwtUser.user = data;
            })
            .then(() => {
                if(!this.currentUser){
                    this.$router.push("/login");
                }
            })
            .then(() => {

            fetch('http://localhost:8080/api/lot/' + this.lotIdNum, {
                method: 'GET'
            })
            .then((response) => {
                    return response.json();
                })
            .then((data) => {
                    console.log(data);
                    this.bid = data;
                    this.currentBid = this.bid;
                    this.dressLot();
                })
            .then(() => {
                if(this.currentBid.bidId){
                    fetch('http://localhost:8080/api/owner/name/' + this.currentBid.bidder, {
                        method: 'GET'
                    })
                    .then((response) => {
                        return response.json();
                    })
                    .then((data) => {
                        this.owner = data;
                        this.currentOwner = this.owner;
                    })
                }
        })
        })
        
    },
  data () {
    return {
      selectedPos: '',
      playerObjectList: [],
      selectedPlayer: Object,
      selectedPlayerId: 0,
      lengthInput: 0,
      salaryInput: 0,
      bidMode: false,
      nomMode: true,
      currentBid: Object,
      currentOwner: Object,
      bidYear: 0,
      bidMonth: 0,
      bidDate: 0,
      bidHour: 0,
      bidMinute: 0,
      bidSecond: 0,
      bidMillisecond: 0,
      loaded: 0,
      localOwnerList: [],
      jwtUser: {
          user: {}
      }
      

    }
  },
  computed: {
        currentUser: function() {
            if (this.jwtUser.user){
                return this.jwtUser;
            }
            if (this.$store.state.auth.user){
                return this.$store.state.auth.user;
            } else {
                return null;
            }
        },
        nomFormIsValid: function(){
            return  this.lengthInput > 0 && this.salaryInput > 0 && this.salaryInput % 1 === 0 && 
                    this.selectedPlayerId > 0 && this.nomMode &&
                    (this.salaryInput <= this.currentUser.user.capRoom || this.salaryInput <= this.currentUser.capRoom) &&
                    (this.lengthInput <= this.currentUser.user.yearsLeft || this.lengthInput <= this.currentUser.yearsLeft);
        },
        bidFormIsValid: function(){
            return this.bidMode && this.lengthInput > 0 && this.salaryInput > 0 && this.salaryInput % 1 === 0 && 
            this.selectedPlayer.playerId > 0 && this.bidMode && this.bidIsLegal;
        },
        lotIdNum: function(){
            return parseInt(this.lotId);
        },
        getTimeStamp: function() {
            let today = new Date();
            let date = today.getFullYear()+','+(today.getMonth())+','+(today.getDate());
            let time = (today.getHours() + 8) + "," + (today.getMinutes()) + "," + today.getSeconds();
            let dateTime = date + ','+ time + ',10';
            
            return dateTime;
            //const end = new Date(2020, 6, 17, 18, 10, 10, 10)
        },
        bidIsLegal: function(){
            return (this.salaryInput + (this.lengthInput * 5)) > (this.currentBid.bidSalary + (this.currentBid.bidLength *5)) &&
             this.currentUser.user.capRoom >= this.salaryInput && this.currentUser.user.yearsLeft >= this.lengthInput && 
             this.salaryInput <= this.currentUser.user.capRoom && this.lengthInput <= this.currentUser.user.yearsLeft;
        }
  },

   methods: {
       callForTimer: function () {
           ++this.loaded;
       },
        lookupPlayerByBid: function() {
            console.log("are we getting here");
            console.log(this.lotIdNum);
           fetch('http://localhost:8080/api/players/' + this.currentBid.playerId, {
               method: 'GET'
           })
            .then(response => response.json())
            .then(data => (this.selectedPlayer = data));
        },
        dressLot: function() {
            if(this.currentBid.bidId){
                this.splitTimeString();
                this.engageBidMode();
                this.callForTimer();
                this.lookupPlayerByBid();
            }
        },
        lookupPlayer: function() {
            console.log("are we getting here");
            console.log(this.lotIdNum);
           fetch('http://localhost:8080/api/players/' + this.selectedPlayerId, {
               method: 'GET'
           })
            .then(response => response.json())
            .then(data => (this.selectedPlayer = data));
        },
        lookupPos: function() {
           fetch('http://localhost:8080/api/position/' + this.selectedPos, {
               method: 'get'
           })
            .then(response => response.json())
            .then(data => (this.playerObjectList = data));

           //call other method to fill out next dropdown with first name + last name of each player
        },
        engageBidMode: function(){
         this.bidMode = true;
         this.nomMode = false;
        },
        engageNomMode: function(){
         this.bidMode = false;
         this.nomMode = true;
        },
        submitNomination: function() {
           const requestOptions = {
               method: "POST",
               headers: {
                    "Content-Type": "application/json",
                    "Authorization" : `Bearer ${this.$cookies.get('token')}`
                    },
               body: JSON.stringify({ playerId:  this.selectedPlayerId,
                                      bidder: this.currentUser.user.ownerName,
                                      bidLength: this.lengthInput,
                                      bidSalary:  this.salaryInput,
                                      expires: this.getTimeStamp
                                    })
                };
            fetch('http://localhost:8080/api/nominate', requestOptions)
                    .then((response) => {
                        return response.json();
                    })
                    .then((data) => {
                        //data = JSON.parse(data);
                        console.log(data);
                        this.bid = data;
                        this.currentBid = this.bid;
                        console.log(this.currentBid.bidId);
                        this.splitTimeString();
                        this.callForTimer();
                        this.lotCleanup();
                    });
                    // this is to update the player with a dummy owner so that he doesn't appear in another dropdown
            const updateRequestOptions = {
               method: "PUT",
               headers: {
                    "Content-Type": "application/json",
                    "Authorization" : `Bearer ${this.$cookies.get('token')}`
                    },
               body: JSON.stringify({ playerId:  this.selectedPlayerId,
                                      ownerId: -1,
                                      firstName: this.selectedPlayer.firstName,
                                      lastName:  this.selectedPlayer.lastName,
                                      position: this.selectedPlayer.position,
                                      salary: 0,
                                      length: 0,
                                      contractValue: 0,
                                      espnId: this.selectedPlayer.espnId
                                    })
                };
            fetch('http://localhost:8080/api/nominate', updateRequestOptions)
                .then(() => {
                    this.$router.go();
                })
        },
        lotCleanup: function(){
            const lotRequestOptions = {
               method: "PUT",
               headers: {
                    "Content-Type": "application/json"
                    },
               body: JSON.stringify({ lotId: this.lotIdNum,
                                      bidId: this.currentBid.bidId
                                    })
                };
            fetch('http://localhost:8080/api/lot/', lotRequestOptions);
           this.engageBidMode();
           this.salaryInput = 0;
           this.lengthInput = "";
        },
        submitBid: function() {
           const requestOptions = {
               method: "POST",
               headers: {
                    "Content-Type": "application/json",
                    "Authorization" : `Bearer ${this.$cookies.get('token')}`
                    },
               body: JSON.stringify({ playerId:  this.selectedPlayer.playerId,
                                      bidder: this.currentUser.user.ownerName,
                                      bidLength: this.lengthInput,
                                      bidSalary:  this.salaryInput,
                                      expires: this.getTimeStamp
                                    })
                };
            fetch('http://localhost:8080/api/bid', requestOptions)
                    .then((response) => {
                        return response.json();
                    })
                    .then((data) => {
                        //data = JSON.parse(data);
                        console.log(data);
                        this.bid = data;
                        this.currentBid = this.bid;
                        this.splitTimeString();
                        this.callForTimer();
                        this.lotCleanup();
                    });
            
           this.engageBidMode();
           //this.$forceUpdate();

            this.$router.go()
        },
        winPlayer: function() {
            const requestOptions = {
               method: "PUT",
               headers: {
                    "Content-Type": "application/json"
                    },
                body: JSON.stringify({bidId: this.currentBid.bidId,
                                      playerId: this.currentBid.playerId,
                                      bidder: this.currentBid.bidder,
                                      bidLength: this.currentBid.bidLength,
                                      bidSalary: this.currentBid.bidSalary,
                                      expires: this.getTimeStamp
                                    })
                };
            fetch('http://localhost:8080/api/win/player', requestOptions)
            const secondRequestOption = {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                    },
                body: JSON.stringify({bidId:  this.currentBid.bidId,
                                      bidder: this.currentBid.bidder,
                                      playerId: this.currentBid.playerId,
                                      bidLength: this.currentBid.bidLength,
                                      bidSalary:  this.currentBid.bidSalary,
                                      expires: this.getTimeStamp
                                    })
                };
                fetch('http://localhost:8080/api/win/owner', secondRequestOption)
                    .then(() => {
                        const thirdRequestOption = {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json"
                            }
                        }
                        fetch('http://localhost:8080/api/lot/clear/' + this.lotId, thirdRequestOption)
                            .then(() => {
                                this.selectedPos = '';
                                this.selectedPlayer = '';
                                this.bidMode = false;
                                this.nomMode = true;
                                this.$emit("newWinner");
                            })
                    });
        // add player to team, put null in the lot, change player to have this owner, salary and years
        },
        splitTimeString: function (){
            //props: ['year', 'month', 'date', 'hour', 'minute', 'second', 'millisecond'],
            let timeArray = this.currentBid.expires.split(",");
            this.bidYear = Number(timeArray[0]);
            this.bidMonth = Number(timeArray[1]);
            this.bidDate = Number(timeArray[2]);
            this.bidHour = Number(timeArray[3]);
            this.bidMinute = Number(timeArray[4]);
            this.bidSecond = Number(timeArray[5]);
            ++this.loaded
        }
    }
}
</script>

<style scoped>

.btn{
    margin: 5px 5px 0px;
}
.current-bid-row{
    font-family: "Franklin Gothic Medium", "Franklin Gothic", "ITC Franklin Gothic", Arial, sans-serif; 
    font-size: 18px; 
    font-style: normal; 
    font-variant: normal; 
    font-weight: 700; 
    line-height: 26.4px;
    text-align: center;
}
#bid-title{
    text-align: center;
    display: inline-block;
    display: none;
}
#bid-title-row{
    text-align: center;
}
#player-title{
    font-family: "Franklin Gothic Medium", "Franklin Gothic", "ITC Franklin Gothic", Arial, sans-serif; 
    font-size: 32px; 
    font-style: normal; 
    font-variant: normal; 
    font-weight: 700; 
    line-height: 26.4px;
}
#playerSelector, #posSelector{
    font-family: "Franklin Gothic Medium", "Franklin Gothic", "ITC Franklin Gothic", Arial, sans-serif; 
    font-size: 16px; 
    font-style: normal; 
    font-variant: normal; 
    font-weight: 700; 
    line-height: 26.4px;
}
.salaryRow{
    font-family: "Franklin Gothic Medium", "Franklin Gothic", "ITC Franklin Gothic", Arial, sans-serif; 
    font-size: 16px; 
    font-style: normal; 
    font-variant: normal; 
    font-weight: 700; 
    line-height: 26.4px;
}
#button-row{
    display: flex;
    justify-content: space-evenly;
}
#salarySelector{
    margin-bottom: 10px;
}

</style>
