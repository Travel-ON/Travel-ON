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
        <v-col v-if="qna.answerFlag"
          ><v-card height="200px"> <v-textarea :disabled="!admin" v-model="qna.answer" /></v-card
        ></v-col>
        <v-col v-else
          ><v-card height="200px">
            <v-textarea :disabled="!admin" v-model="answer" placeholder="아직 답변이 작성되지 않았습니다." /></v-card
        ></v-col>
      </v-row>
      <v-row
        ><v-col class="d-flex justify-end mb-6">
          <div v-if="!qna.answerFlag && admin">
            <v-btn depressed color="primary" @click="registAnswer"> 답변작성 </v-btn>
          </div>
          <div v-if="qna.answerFlag">
            <v-btn depressed color="primary" @click="modifyAnswer"> 답변수정 </v-btn>
          </div>
          <div v-if="qna.answerFlag"><v-btn depressed color="primary" @click="deleteAnswer"> 답변삭제 </v-btn></div>
        </v-col></v-row
      >
    </v-container>
  </div>
</template>
<script>
import { mapState, mapGetters } from "vuex";
import Swal from "sweetalert2";

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
    console.log(this.$store.state.qna);

    this.admin = this.$store.getters.admin;
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const qnaid = pathName[pathName.length - 1];
    this.$store.dispatch("QnAs/getQna", qnaid);
    // if (this.qna.answer) {
    //   this.answer = this.qna.answer;
    // } else {
    //   this.answer = ;
    // }
  },
  methods: {
    moveToList() {
      this.$router.push({
        name: "QnaList",
      });
    },

    moveToUpdate() {
      if (this.qna.answerFlag) {
        Swal.fire({
          icon: "error",
          title: "답변이 완료된 글은 수정할 수 없습니다!",
          text: "문의 글을 삭제하시거나 관리자에게 문의 하세요",
          showConfirmButton: false,
          timer: 2000,
        });
        return;
      }
      this.$router.push({
        path: `/qna/update/${this.qna.qnaid}`,
      });
    },
    QnaDelete() {
      this.$store.dispatch("QnAs/deleteQna", this.qna.qnaid);
      Swal.fire({
        icon: "success",
        title: "문의 삭제가 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.push({ name: "QnaList" });
    },
    registAnswer() {
      const params = {
        qnaId: this.qna.qnaid,
        answer: this.answer,
      };
      this.$store.dispatch("QnAs/registQnaAnswer", params);
      Swal.fire({
        icon: "success",
        title: "답변 작성이 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });

      this.$store.dispatch("QnAs/getQna", this.qna.qnaid);
      this.$router.go();
    },
    modifyAnswer() {
      const params = {
        qnaId: this.qna.qnaid,
        answer: this.qna.answer,
      };
      this.$store.dispatch("QnAs/modifyQnaAnswer", params);
      Swal.fire({
        icon: "success",
        title: "답변 수정이 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$store.dispatch("QnAs/getQna", this.qna.qnaid);
      this.$router.go();
    },
    deleteAnswer() {
      this.$store.dispatch("QnAs/deleteQnaAnswer", this.qna.qnaid);
      Swal.fire({
        icon: "success",
        title: "답변 삭제가 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$store.dispatch("QnAs/getQna", this.qna.qnaid);
      this.$router.go();
    },
  },
};
</script>

<style scoped></style>
