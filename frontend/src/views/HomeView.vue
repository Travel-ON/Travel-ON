<template>
<<<<<<< HEAD
  <HelloWorld />
</template>

<script>
import { defineComponent } from "vue";

// Components
import HelloWorld from "../components/HelloWorld.vue";
=======
  <MainNav />
  <div style="margin-top: 80px">
    <img src="https://via.placeholder.com/1920x1080" alt="main-banner" style="width: 100%" />
  </div>
  <MainSpacingHome />
</template>

<script>
import MainNav from "@/components/MainNav.vue";
import MainSpacingHome from "@/components/MainSpacingHome.vue";
import { defineComponent } from "vue";

// Components
>>>>>>> 64b664887aee00a4bcb6cb0ca359843c840c8b10

export default defineComponent({
  name: "HomeView",

  components: {
    MainNav,
    MainSpacingHome,
  },
});
</script>
