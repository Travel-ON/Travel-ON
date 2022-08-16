<!-- eslint-disable vuejs-accessibility/media-has-caption -->
<template>
  <video class="video" autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style>
.video {
  border-radius: 12px;
  padding: 0 8px;
  border: 4px solid #fff;
  background-color: #fff;
  width: 100%;
  height: 100%;
}
</style>
