<template>
  <div id="app">
    <Pic />
  </div>
</template>

<script>
import Pic from './components/Pic.vue'
import Vue from 'vue';
import HeyUI from 'heyui';
// import Waterfall from "vue-waterfall2";

Vue.use(HeyUI);
// Vue.use(Waterfall);

export default {
  name: 'app',
  components: {
    Pic
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  position: relative;
  height: 100%;
}
</style>
