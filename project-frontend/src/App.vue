<template>
  <v-app>
    <v-main>
      <concert-tool-bar />
        <router-view :key="$route.fullPath"/>
      <bottom-tool-bar />
    </v-main>
  </v-app>
</template>

<script>
import BottomToolBar from './components/toolBars/BottomToolBar.vue';
import ConcertToolBar from './components/toolBars/ConcertToolBar.vue';

export default {
  name: 'App',
  components: {
    BottomToolBar,
    ConcertToolBar
  },

  data: () => ({
    //
  }),
};
</script>
