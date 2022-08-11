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
          ><v-card height="200px"> <v-textarea :disabled="!admin" v-model="answer" /></v-card
        ></v-col>
      </v-row>
      <v-row
        ><v-col class="d-flex justify-end mb-6">
          <div v-if="!qna.answerFlag && admin">
            <v-btn depressed color="primary" @click="registAnswer"> 답변작성 </v-btn>
          </div>
          <div v-if="qna.answerFlag"><v-btn depressed color="primary" @click="modifyAnswer"> 답변수정 </v-btn></div>
          <div v-if="qna.answerFlag"><v-btn depressed color="primary" @click="deleteAnswer"> 답변삭제 </v-btn></div>
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
    ...mapGetters(["QnAs/getQna"]),
  },
  data() {
    return {
      idx: this.$route.params.id,
      answer: "",
      admin: false,
    };
  },
  mounted() {
    if (this.qna.answer) {
      this.answer = this.qna.answer;
    } else {
      this.answer = "아직 답변이 작성되지 않았습니다.";
    }
    this.admin = this.$store.getters.admin;
    console.log(this.admin);
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

    moveToUpdate() {
      this.$router.push({
        path: `/qna/update/${this.qna.qnaid}`,
      });
    },
    QnaDelete() {
      this.$store.dispatch("QnAs/deleteQna", this.qna.qnaid);
    },
    registAnswer() {
      const params = {
        qnaId: this.qna.qnaid,
        answer: this.answer,
      };
      this.$store.dispatch("QnAs/registQnaAnswer", params);
    },
    modifyAnswer() {
      const params = {
        qnaId: this.qna.qnaid,
        answer: this.answer,
      };
      this.$store.dispatch("QnAs/modifyQnaAnswer", params);
    },
    deleteAnswer() {
      this.$store.dispatch("QnAs/deleteQnaAnswer", this.qna.qnaid);
    },
  },
};
</script>

<style scoped></style>
