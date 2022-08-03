<template>
  <div>
    <main-nav />
    <div>
      <img src="https://via.placeholder.com/1920x1080" alt="main-banner" style="width: 100%" />
    </div>
    <main-spacing-home />
  </div>
</template>

<script>
import { defineComponent } from "vue";
import MainNav from "../components/MainNav.vue";
import MainSpacingHome from "../components/MainSpacingHome.vue";

export default defineComponent({
  name: "HomeView;",
  components: {
    MainNav,
    MainSpacingHome,
  },
});
</script>
