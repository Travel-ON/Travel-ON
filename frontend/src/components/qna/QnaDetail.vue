<template>
  <div>
    <v-container>
      <v-row>
        <v-col class="d-flex mb-6">
          <v-btn depressed color="yellow" @click="moveToList"> 뒤로가기 </v-btn>
        </v-col>
        <v-col class="d-flex justify-end mb-6">
          <v-btn depressed color="red" @click="moveToUpdate"> 수정 </v-btn>
          <v-btn depressed color="blue" @click="QnaDelete"> 삭제 </v-btn>
        </v-col>
      </v-row>
      <v-row class="mb-6">
        <v-col
          ><v-card>
            {{ qna.title }}
            <div>작성일 {{ qna.qnaDate }}</div>
            <div>작성자 {{ qna.nickname }}</div>
            <div v-if="qna.answerFlag">답변완료</div>
            <div v-else>답변대기</div>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col
          ><v-card class="" height="200px">{{ qna.content }}</v-card></v-col
        >
      </v-row>
      <v-row>
        <v-col
          ><v-card height="200px"> <v-textarea :disabled="!isNotAdmin()" v-model="answer" /></v-card
        ></v-col>
      </v-row>
      <v-row
        ><v-col class="d-flex justify-end mb-6">
          <v-btn depressed color="primary" @click="updateAnswer"> 답변작성 </v-btn>
        </v-col></v-row
      >
    </v-container>
  </div>
</template>
<script>
import { mapState, mapGetters } from "vuex";

export default {
  name: "QnaDetail",
  computed: {
    ...mapState("QnAs", ["qna"]),
    ...mapGetters(["admin", "QnAs/getQna"]),
  },
  data() {
    return {
      idx: this.$route.params.id,
      answer: "",
    };
  },
  mounted() {
    console.log(this.qna.answer);
    if (this.qna.answer) {
      this.answer = this.qna.answer;
    } else {
      this.answer = "아직 답변이 작성되지 않았습니다.";
    }
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const qnaid = pathName[pathName.length - 1];
    this.$store.dispatch("QnAs/getQna", qnaid);
  },
  methods: {
    moveToList() {
      this.$router.push({
        name: "QnaList",
      });
    },
    isNotAdmin() {
      return !this.admin;
    },
    moveToUpdate() {
      this.$router.push({
        path: `/qna/update/${this.qna.qnaid}`,
      });
    },
    QnaDelete() {
      this.$store.dispatch("QnAs/deleteQna", this.qna.qnaid);
    },
    updateAnswer() {
      const params = {
        qnaId: this.qna.qnaid,
        answer: this.answer,
      };
      this.$store.dispatch("QnAs/modifyQnaAnswer", params);
    },
  },
};
</script>

<style scoped></style>
