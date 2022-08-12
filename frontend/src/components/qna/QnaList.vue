<template>
  <div>
    <v-container>
      <div v-if="admin">
        <label for="checkbox" style="float: left"
          ><input type="checkbox" id="checkbox" v-model="checked" @click="change" />답변 대기 글만 보기</label
        >
      </div>
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

      <v-container class="">
        <v-form>
          <v-text-field v-model="keyword" label="제목검색"></v-text-field>
        </v-form>
        <v-btn color="indigo" text @click="submit">검색</v-btn>
      </v-container>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";

export default {
  data() {
    return {
      keyword: "",
      checked: false,
      theAdmin: false,
    };
  },
  computed: {
    ...mapState("QnAs", ["qnas"]),
    ...mapGetters(["admin"]),
  },
  mounted() {
    this.theAdmin = this.$store.getters.admin;
    console.log(this.theAdmin);
    if (this.theAdmin) {
      this.$store.dispatch("QnAs/getAdminQnas");
    } else {
      this.$store.dispatch("QnAs/getQnas");
    }
  },

  methods: {
    submit() {
      if (this.theAdmin) {
        this.$store.dispatch("QnAs/getAdminQnas", this.keyword);
      } else {
        this.$store.dispatch("QnAs/getQnas", this.keyword);
      }
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
    change() {
      if (!this.checked) {
        this.$store.dispatch("QnAs/getNoAnswer");
        this.checked = !this.checked;
      } else {
        this.$store.dispatch("QnAs/getAdminQnas", this.keyword);
      }
    },
  },
};
</script>

<style scoped></style>
