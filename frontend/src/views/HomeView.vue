<template>
  <div>
    <hello-world />
    <main-nav />
    <main-spacing-home />
  </div>
</template>

<script>
import HelloWorld from "../components/HelloWorld.vue";
import MainNav from "../components/MainNav.vue";
import MainSpacingHome from "../components/MainSpacingHome.vue";

export default {
  name: "HomeView",

  components: {
    HelloWorld,
    MainNav,
    MainSpacingHome,
  },
};
</script>
