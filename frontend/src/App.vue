<template>
  <v-app>
    <v-main>
      <!-- main-nav는 기본적으로 모든 페이지에 포함, 제외시 하단 exceptList에 포함시킬것 -->
      <main-nav v-if="!state.exceptList.includes($route.name)" />
      <router-link to="/">Home</router-link> |
      <router-link to="/notice">Notice</router-link>
      | <router-link to="/login">로그인</router-link> |
      <router-link to="/member/register">회원가입</router-link>
      |<router-link to="/modify">회원정보수정</router-link>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { reactive } from "vue";
import MainNav from "./components/common/MainNav.vue";

export default {
  name: "App",
  setup() {
    const state = reactive({
      exceptList: ["chatRoom"], // Nav바 제외리스트
    });

    return {
      state,
    };
  },
  components: { MainNav },
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
