<template>
  <div>
    <v-container>
      <v-row class="mb-6" no-gutters>
        <v-col
          ><v-card>
            {{ notice.title }}
            <div>작성일 {{ notice.notice_date }}</div>
            <div>조회수 {{ notice.hits }}</div></v-card
          >
        </v-col>
      </v-row>
      <v-row>
        <v-col
          ><v-card class="" height="300px">{{ notice.content }}</v-card></v-col
        >
      </v-row>
      <v-row>
        <v-col class="d-flex justify-end mb-6">
          <v-btn depressed color="primary" @click="fixedToggle"> 상단고정 설정 </v-btn>
          <v-btn depressed color="red" @click="moveToUpdate"> 수정 </v-btn>
          <v-btn depressed color="blue" @click="NoticeDelete"> 삭제 </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["notice"]),
  },
  data() {
    return {
      idx: this.$route.params.id,
    };
  },
  created() {
    // actions로 noticeId값 보내기
    const pathName = new URL(document.location).pathname.split("/");
    const noticeId = pathName[pathName.length - 1];
    this.$store.dispatch("getNotice", noticeId);
  },
  methods: {
    fixedToggle() {
      this.notice.fixation_flag = !this.notice.fixation_flag;
      this.$store.dispatch("modifyNotice", this.notice);
    },
    moveToUpdate() {
      this.$router.push({
        path: `/notice/update/${this.notice.notice_id}`,
      });
    },
    NoticeDelete() {
      this.$store.dispatch("deleteNotice", this.notice.notice_id);
    },
  },
};
</script>

<style></style>
