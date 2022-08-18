<template>
  <v-app :style="!state.exceptFooterList.includes($route.name) ? `overflow: 'hidden'` : ``">
    <v-app-bar height="80" v-if="!state.exceptNavList.includes($route.name)">
      <!-- main-nav는 기본적으로 모든 페이지에 포함, 제외시 하단 exceptList에 포함시킬것 -->
      <main-nav />
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
    <footer-view v-if="!state.exceptFooterList.includes($route.name)"></footer-view>
  </v-app>
</template>

<script>
import { reactive } from "vue";
import { mapActions } from "vuex";
import MainNav from "./components/common/MainNav.vue";
import FooterView from "./components/common/FooterView.vue";

export default {
  name: "App",
  setup() {
    const state = reactive({
      exceptNavList: ["VideochatMatching", "VideochatCreate", "VideochatRoom", "VideochatShare"], // Nav바 제외리스트
      exceptFooterList: ["VideochatRoom"],
    });

    return {
      state,
    };
  },
  methods: {
    ...mapActions(["fetchCurrentUser"]),
  },
  components: { MainNav, FooterView },
  mounted() {
    this.fetchCurrentUser();
  },
  watch: {
    $route(to, form) {
      if (to.path !== form.path) {
        this.fetchCurrentUser();
      }
    },
  },
};
</script>

<style>
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
#app {
  font-family: "Jeju Gothic", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #1b2d47;
}
</style>
