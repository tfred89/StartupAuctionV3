<template>
    <div>
        <section>
            <b-container class="timer-row">
                <b-row>
                    <b-col class="col-sm-1">
                    </b-col>
                    <b-col class="hours relative col-sm-2">
                        <div class="big-num">{{displayHours}}: </div>
                        <!-- <div class="clock-label">hours</div> -->
                    </b-col>
                    <!-- <span class="leading-snug big-num">:</span> -->
                    <b-col class="minutes relative col-sm-2">
                       <div class="big-num"> {{displayMinutes}}: </div>
                        <!-- <div class="clock-label">min</div> -->
                    </b-col>
                    <!-- <span class="leading-snug big-num">:</span> -->
                    <div class="seconds relative">
                    <b-col class="col-sm-2"> 
                        <div class="big-num"> {{displaySeconds}} </div>
                    </b-col>
                    <!-- <div class="clock-label">sec</div> -->
                    </div>
                    <b-col class="col-sm-1">
                    </b-col>
                </b-row>
            </b-container>
        </section>
    </div>
</template>

<script>

export default {
    props: ['year', 'month', 'date', 'hour', 'minute', 'second', 'millisecond', 'updateClock'],
    data: () => ({
        displayHours: 0,
        displayMinutes: 0,
        displaySeconds: 0,
        expired: false,
    }),
    computed: {
        _seconds: () => 1000,
        _minutes() {
            return this._seconds * 60
        },
        _hours() {
            return this._minutes * 60
        },
        _days() {
            return this._hours * 24
        },
        end() {
            return new Date(
                this.year,
                this.month,
                this.date,
                this.hour,
                this.minute,
                this.second,
                this.millisecond
            );
        }
    },
    watch: {
        updateClock: function() {
            this.showRemaining();
        
            
        }
        
    },
    methods: {
        showRemaining(){
            const timer = setInterval(() => {
                const now = new Date();
                //const end = new Date(2020, 6, 17, 18, 10, 10, 10);
                const distance = this.end.getTime() - now.getTime();

                if(distance < 0) {
                    clearInterval(timer);
                    this.$emit("timeExpired");
                 
                }
                const days = Math.floor(distance / this._days);
                const hours = Math.floor((distance % this._days) / this._hours);
                const minutes = Math.floor((distance % this._hours) / this._minutes);
                const seconds = Math.floor((distance % this._minutes) / this._seconds);
                this.displayMinutes = minutes < 10 ? "0" + minutes : minutes;
                this.displaySeconds = seconds < 10 ? "0" + seconds : seconds;
                this.displayHours = hours < 10 ? "0" + hours : hours;
                this.displayDays = days < 10 ? "0" + days : days;
            })
        }
    }

};
</script>

<style scoped>
.timer-row{
    width: 60%;
}
.big-num{
    font-size: 32px;
    font-weight: bold;
}
.hours{

}


.clock-label{
    font-size: 10px;
}
</style>