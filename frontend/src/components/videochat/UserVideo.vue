<template>
  <div class="ml-2 mr-2" v-if="streamManager" style="position: relative">
    <ov-video :stream-manager="streamManager" />
    <div style="position: absolute; bottom: 10px; right: 50%">
      <div
        class="mb-1"
        v-if="clientTitle"
        style="
          background-color: darkblue;
          color: whitesmoke;
          border-radius: 5px;
          padding-left: 10px;
          padding-right: 10px;
          padding-top: 1px;
          padding-bottom: 1px;
        "
      >
        {{ clientTitle }}
      </div>
      <div
        style="
          background-color: paleturquoise;
          border-radius: 5px;
          padding-left: 10px;
          padding-right: 10px;
          padding-top: 1px;
          padding-bottom: 1px;
        "
      >
        {{ clientName }}
      </div>
    </div>
    <div style="position: absolute; top: 10px; right: 50%; border-radius: 15px">
      <div
        v-if="isResident === 'true'"
        style="
          background-color: #6499ff;
          color: whitesmoke;
          padding-left: 10px;
          padding-right: 10px;
          padding-top: 1px;
          padding-bottom: 1px;
        "
      >
        <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientName() {
      const { clientName } = this.getConnectionData();
      return clientName;
    },
    clientTitle() {
      const { clientTitle } = this.getConnectionData();
      return clientTitle;
    },
    isResident() {
      const { isResident } = this.getConnectionData();
      return isResident;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
