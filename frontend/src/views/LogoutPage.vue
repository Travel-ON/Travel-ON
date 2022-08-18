<template>
  <div>logout</div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  /* eslint-disable */
  methods: {
    ...mapActions(["logout"]),
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  created() {
    // 로그아웃시 로그인 상태일때 처리
    if (this.isLoggedIn) {
      this.logout();
    } else {
      // 로그인 상태가 아닌데 로그아웃 시도시 홈으로 이동
      this.$router.push({ name: "home" });
    }
  },
};
</script>

<style></style>
