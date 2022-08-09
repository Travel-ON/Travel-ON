<template>
  <div>
    <div>
      <img src="https://via.placeholder.com/1920x1080" alt="main-banner" style="width: 100%" />
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
    ...mapGetters(["isLocation"]),
  },
  methods: {
    ...mapActions(["getLocation"]),
  },
  mounted() {
    if (!this.isLocation) {
      this.getLocation();
    }
  },
});
</script>
