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
    if (this.isLoggedIn) {
      this.logout();
    } else {
      this.$router.push({ name: "home" });
    }
  },
};
</script>

<style></style>
