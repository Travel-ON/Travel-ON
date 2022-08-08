<template>
  <div id="PlannerStory">
    <h1>플래너스토리</h1>
    <ModalView v-if="isModalViewed" @close-modal="isModalViewed = false"> </ModalView>
    <button @click="isModalViewed = true">스토리보기</button>
  </div>
</template>

<script>
// import ModalView from "./components/planner/ModalView";

export default {
  name: "PlannerStory",
  components: {},
  data() {
    return {
      isModalViewed: false,
    };
  },
};
</script>
