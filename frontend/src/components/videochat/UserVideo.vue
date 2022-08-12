<template>
  <div class="ml-2 mr-2" v-if="streamManager" style="position: relative">
    <ov-video :stream-manager="streamManager" />
    <div class="text-center" style="position: absolute; top: 10px; right: 10px; border-radius: 15px">
      <v-menu bottom offset-x>
        <template v-slot:activator="{ props }">
          <v-btn dark icon v-bind="props">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item, i) in items" :key="i" @click="check(item)">
            <v-list-item-title v-if="isHost || item.onlyHost">{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>

    <div style="position: absolute; bottom: 10px; right: 50%">
      <div
        class="label mb-1"
        v-if="clientTitle"
        style="background-color: darkblue; color: whitesmoke; border-radius: 5px"
      >
        {{ clientTitle }}
      </div>
      <div class="label" style="background-color: paleturquoise; border-radius: 5px">
        {{ clientName }}
      </div>
    </div>
    <div style="position: absolute; top: 10px; right: 50%; border-radius: 15px">
      <div v-if="isResident === 'true'" class="label" style="background-color: #6499ff; color: whitesmoke">
        <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",
  data: () => ({
    items: [
      { title: "여행 플래너 보기", onlyHost: false },
      { title: "신고하기", onlyHost: false },
      { title: "강퇴하기", onlyHost: true },
    ],
  }),
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
    isHost() {
      const { isHost } = this.getConnectionData();
      return isHost;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    check(item) {
      alert(item.title);
    },
  },
};
</script>
<style scoped>
.label {
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 1px;
  padding-bottom: 1px;
}
</style>
