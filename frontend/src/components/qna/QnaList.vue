<template>
  <div>
    <v-container>
      <div class="d-flex justify-end mb-6">
        <v-btn color="primary" @click="moveToWrite">문의하기</v-btn>
      </div>
      <!--      <v-row>-->
      <!--        <v-col>문의 리스트(회원)</v-col>-->
      <!--      </v-row>-->
      <v-row style="background-color: lightgrey">
        <v-col>글번호 </v-col>
        <v-col>제목</v-col>
        <v-col>작성날짜</v-col>
        <v-col>상태</v-col>
      </v-row>
      <v-row v-for="qna in qnas" :key="qna.qnaid">
        <v-col>{{ qna.qnaid }}</v-col>
        <v-col @click="moveToDetail(qna.qnaid)">{{ qna.title }}</v-col>
        <v-col>{{ qna.qnaDate }}</v-col>
        <v-col v-if="qna.answerFlag">답변완료</v-col>
        <v-col v-else>답변대기</v-col>
      </v-row>
      <v-row>
        <v-text-field v-model="keyword" label="제목검색"></v-text-field>
        <v-btn color="primary" text @click="submit"> 검색 </v-btn>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      keyword: "",
    };
  },
  computed: {
    ...mapState("QnAs", ["qnas"]),
  },
  mounted() {
    this.$store.dispatch("QnAs/getQnas");
  },
  methods: {
    submit() {
      this.$store.dispatch("QnAs/getQnas", this.keyword);
    },
    moveToDetail(id) {
      this.$router.push({
        path: `/qna/detail/${id}`,
      });
    },
    moveToWrite() {
      this.$router.push({
        path: "/qna/write",
      });
    },
  },
};
</script>

<style scoped></style>
