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
import MainSpacingHome from "../components/main/MainSpacingHome.vue";

export default defineComponent({
  name: "HomeView;",
  components: {
    MainSpacingHome,
  },
});
</script>
