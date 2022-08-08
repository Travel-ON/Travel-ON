<!-- eslint-disable vuejs-accessibility/media-has-caption -->
<template>
  <video autoplay />
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
