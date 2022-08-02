<template>
  <div>
    <div id="title">Notice</div>
    <v-container>
      <div class="d-flex justify-end mb-6">
        <v-btn color="primary" @click="write">글작성</v-btn>
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
        <v-col>{{ notice.title }}</v-col>
        <v-col>{{ notice.notice_date }}</v-col>
        <v-col>{{ notice.hits }}</v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      notices: [],
    };
  },
  created() {
    axios
      .get("https://7d0c97ac-6236-4b8f-9ba8-f257edcdfe2d.mock.pstmn.io//notice/page")
      .then((res) => {
        this.notices = res.data;
        // notice sort
        this.notices.sort((a, b) => {
          return a.fixation_flag > b.fixation_flag ? -1 : 1;
        });
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    write() {
      this.$router.push({
        path: "/notice/create",
      });
    },
  },
};
</script>

<style>
#title {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
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
