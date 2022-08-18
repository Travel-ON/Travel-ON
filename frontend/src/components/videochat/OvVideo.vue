<!-- eslint-disable vuejs-accessibility/media-has-caption -->
<template>
  <video
    class="video"
    autoplay
    :style="`box-sizing: border-box; max-height: ${480 * videoScale}px; max-width: ${
      640 * videoScale
    }px; border: 0; padding: 0`"
  />
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },
  computed: {
    ...mapGetters("MeetingStore", ["videoScale"]),
  },
  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style>
.video {
  border-radius: 10px;
  padding: 0;
  border: 4px solid #fff;
  background-color: #fff;
  width: 100%;
  height: 100%;
}
</style>
