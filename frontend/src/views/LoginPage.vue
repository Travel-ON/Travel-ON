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
    if (!this.isLoggedIn) {
      this.login();
    } else {
      this.$router.push({ name: "home" });
    }
  },
};
</script>

<style></style>
