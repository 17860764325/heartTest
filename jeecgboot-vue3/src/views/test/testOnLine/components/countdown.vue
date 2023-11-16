<template>
  <span style="font-size:25px;color: red;">还有{{ hour }}小时{{ minute }}分{{ second }}秒结束！</span>
</template>

<script>
export default {
  data () {
    return {
      hour: 0,
      minute: 0,
      second: 0,
    }
  },
  props: {
    remainTime: {    // 倒计时间总秒数
      default: '329475'
    }
  },
  mounted () {
    console.log(this.remainTime)
    if (this.remainTime > 0) {
      this.hour = Math.floor((this.remainTime / 3600) % 24)
      this.minute = Math.floor((this.remainTime / 60) % 60)
      this.second = Math.floor(this.remainTime % 60)
      this.countDowm()
    }
  },
  methods: {
    countDowm () {
      var self = this
      clearInterval(this.promiseTimer)
      this.promiseTimer = setInterval(function () {
        if (self.hour === 0) {
          if (self.minute !== 0 && self.second === 0) {
            self.second = 59
            self.minute -= 1
          } else if (self.minute === 0 && self.second === 0) {
            self.second = 0
            // 结束回调
            self.$emit("finish")
            clearInterval(self.promiseTimer)
          } else {
            self.second -= 1
          }
        } else {
          if (self.minute !== 0 && self.second === 0) {
            self.second = 59
            self.minute -= 1
          } else if (self.minute === 0 && self.second === 0) {
            self.hour -= 1
            self.minute = 59
            self.second = 59
          } else {
            self.second -= 1
          }
        }
      }, 1000)
    },
    formatNum (num) {
      return num < 10 ? '0' + num : '' + num
    }
  }
}
</script>
