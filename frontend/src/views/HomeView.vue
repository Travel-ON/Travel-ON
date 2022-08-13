<template>
  <div>
    <div>
      <img
        src="https://user-images.githubusercontent.com/97648026/184269257-39835f5d-902f-4b26-8836-baf35ff4042b.png"
        alt="main-banner"
        style="width: 100%"
      />
    </div>
    <main-spacing-home />
  </div>
</template>

<script>
import { defineComponent } from "vue";
import { mapActions, mapGetters } from "vuex";
import MainSpacingHome from "../components/main/MainSpacingHome.vue";

export default defineComponent({
  name: "HomeView;",
  components: {
    MainSpacingHome,
  },
  computed: {
    ...mapGetters(["isLocation", "isLoggedIn"]),
  },
  methods: {
    ...mapActions(["getLocation"]),
  },
  mounted() {
    if (!this.isLocation && this.isLoggedIn) {
      this.getLocation(false);
    }
  },
});
</script>
