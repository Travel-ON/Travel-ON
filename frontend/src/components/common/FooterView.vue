<template>
  <div>
    <v-divider></v-divider>
    <v-footer padless>
      <v-card flat tile class="lighten-1 text-center" width="100vw">
        <v-card-text> {{ new Date().getFullYear() }} — <strong>SSAFY</strong> - 대전 3반 1팀</v-card-text>
      </v-card>
    </v-footer>
  </div>
</template>

<script>
export default {
  name: "FooterView",
};
</script>

<style></style>
