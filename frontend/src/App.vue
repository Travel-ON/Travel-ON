<template>
  <v-app>
    <v-main>
      <router-link to="/">Home</router-link> | <router-link to="/about">About</router-link> |
      <router-link to="/notice">Notice</router-link>
      <router-link to="/login">로그인</router-link> |
      <router-link to="/signup">회원가입</router-link>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    //
  }),
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
