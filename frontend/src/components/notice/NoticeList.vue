<template>
  <div>
    <v-container>
      <div class="d-flex justify-end mb-6" v-if="admin">
        <v-btn color="primary" @click="moveToWrite">글작성</v-btn>
      </div>
      <v-row style="background-color: #c9deff">
        <v-col>글번호 </v-col>
        <v-col>제목</v-col>
        <v-col>작성날짜</v-col>
        <v-col>조회수</v-col>
      </v-row>
      <v-row v-for="notice in notices" :key="notice.noticeId">
        <v-col v-if="notice.fixationFlag" style="background-color: #fffeef; margin-top: 11px">notice</v-col>
        <v-col v-else style="margin-top: 11px">{{ notice.noticeId }}</v-col>
        <v-col
          :style="notice.fixationFlag ? `background-color: #fffeef; margin-top: 11px` : `margin-top: 11px`"
          @click="moveToDetail(notice.noticeId)"
          >{{ notice.title }}</v-col
        >
        <v-col :style="notice.fixationFlag ? `background-color: #fffeef; margin-top: 11px` : `margin-top: 11px`">{{
          notice.noticeDate
        }}</v-col>
        <v-col :style="notice.fixationFlag ? `background-color: #fffeef; margin-top: 11px` : `margin-top: 11px`">{{
          notice.hits
        }}</v-col>
      </v-row>
      <div class="text-center">
        <v-container>
          <v-row justify="center">
            <v-col cols="8">
              <v-container class="max-width">
                <v-pagination v-model="page" class="my-4" :length="totalPage"></v-pagination>
              </v-container>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState("Notices", ["notices", "totalPage"]),
  },
  watch: {
    page(num) {
      this.handlePage(num);
    },
  },
  data() {
    return { page: 1 };
  },
  mounted() {
    this.$store.dispatch("Notices/getNotices");
  },
  methods: {
    handlePage() {
      this.$store.dispatch("Notices/getNotices", this.page - 1);
    },
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
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
}
</style>
