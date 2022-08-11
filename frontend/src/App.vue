<template>
  <v-app>
    <!-- main-nav는 기본적으로 모든 페이지에 포함, 제외시 하단 exceptList에 포함시킬것 -->
    <main-nav v-if="!state.exceptList.includes($route.name)" />
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { reactive } from "vue";
import { mapActions } from "vuex";
import MainNav from "./components/common/MainNav.vue";

export default {
  name: "App",
  setup() {
    const state = reactive({
      exceptList: ["VideochatMatching", "VideochatMa", "VideochatCreate", "VideochatRoom", "VideochatShare"], // Nav바 제외리스트
    });

    return {
      state,
    };
  },
  methods: {
    ...mapActions(["fetchCurrentUser"]),
  },
  components: { MainNav },
  mounted() {
    this.fetchCurrentUser();
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;

  margin-top: 60px;
}
</style>
