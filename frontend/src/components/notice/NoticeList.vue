<template>
  <div>
    <v-container>
      <div class="d-flex justify-end mb-6">
        <v-btn color="primary" @click="moveToWrite">글작성</v-btn>
      </div>
      <v-row style="background-color: lightgrey">
        <v-col>글번호 </v-col>
        <v-col>제목</v-col>
        <v-col>작성날짜</v-col>
        <v-col>조회수</v-col>
      </v-row>
      <v-row v-for="notice in notices" :key="notice.notice_id">
        <v-col v-if="notice.fixation_flag">notice</v-col>
        <v-col v-else>{{ notice.notice_id }}</v-col>
        <v-col @click="moveToDetail(notice.notice_id)">{{ notice.title }}</v-col>
        <v-col>{{ notice.notice_date }}</v-col>
        <v-col>{{ notice.hits }}</v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["notices"]),
  },
  data() {
    return {};
  },
  created() {
    this.$store.dispatch("getNotices");
  },
  methods: {
    moveToDetail(id) {
      this.$router.push({
        path: `/notice/detail/${id}`,
      });
    },
    moveToWrite() {
      this.$router.push({
        path: "/notice/write",
      });
    },
  },
};
</script>

<style scoped>
#title {
  text-align: center;
  color: #2c3e50;
  justify-content: center;
  font-family: "Inter";
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
}
</style>
