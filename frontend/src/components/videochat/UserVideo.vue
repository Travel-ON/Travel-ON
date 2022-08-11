<template>
  <div class="ml-2 mr-2" v-if="streamManager" style="position: relative">
    <ov-video :stream-manager="streamManager" />
    <div style="position: absolute; bottom: 10px; right: 50%">
      <div v-if="clientTitle" style="background-color: darkblue; color: whitesmoke">{{ clientTitle }}</div>
      <div style="background-color: paleturquoise">{{ clientName }}</div>
    </div>
    <div style="position: absolute; top: 10px; right: 50%">
      <div v-if="isResident === 'true'" style="background-color: darkblue; color: whitesmoke">
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
