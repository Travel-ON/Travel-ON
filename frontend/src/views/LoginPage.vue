<template>
  <div id="title">LoginPage</div>
  <router-view />
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  /* eslint-disable */
  methods: {
    ...mapActions(["login"]),
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  created() {
    // 로그인시 로그인 상태가 아니면 처리
    if (!this.isLoggedIn) {
      this.login();
    } else {
      // 로그인 상태인데 로그인 시도시 홈으로 이동
      this.$router.push({ name: "home" });
    }
  },
};
</script>

<style></style>
