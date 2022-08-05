<template>
  <div>
    <v-container style="background-color: lightgrey">
      <v-row class="mb-6" no-gutters>
        <v-col><v-card>제목</v-card><v-card> 작성일 2022.07.14 조회수 127 </v-card></v-col>
      </v-row>
      <v-row>
        <v-col
          ><v-card class="" height="300px">{{ notice.content }}</v-card></v-col
        >
      </v-row>
      <v-row>
        <v-col class="d-flex justify-end mb-6">
          <v-btn depressed color="primary"> 상단고정 설정 </v-btn>
          <v-btn depressed color="red"> 수정 </v-btn>
          <v-btn depressed color="blue"> 삭제 </v-btn>
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
};
</script>

<style></style>
