<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container
        ><v-row>
          <v-col class="d-flex mb-6">
            <v-btn depressed color="yellow" @click="moveToDetail"> 뒤로가기 </v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col
            ><v-text-field v-model="qna.title" :counter="10" :rules="titleRules" label="제목" required> </v-text-field
          ></v-col>
        </v-row>
        <v-row>
          <v-col
            ><v-textarea v-model="qna.content" :counter="300" label="내용" :rules="contentRules" required> </v-textarea
          ></v-col>
        </v-row>
        <div class="d-flex justify-end mb-6">
          <v-btn color="success" class="d-flex justify-end" @click="[validate(), updateQna()]"> 수정 </v-btn>
        </div>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Swal from "sweetalert2";

export default {
  name: "QnaUpdate",
  computed: {
    ...mapState("QnAs", ["qna"]),
  },
  data() {
    return {
      valid: true,
      titleRules: [
        (v) => !!v || "제목을 입력하세요",
        (v) => (v && v.length <= 10) || "제목은 10글자 이하로 입력하세요",
      ],
      content: "",
      contentRules: [(v) => !!v || "내용을 입력하세요", (v) => (v && v.length <= 300) || "내용은 최대 200글자입니다."],
    };
  },

  methods: {
    moveToDetail() {
      this.$router.push({
        name: "QnaDetail",
        params: { qnaid: this.qna.qnaid },
      });
    },
    validate() {
      this.$refs.form.validate();
    },
    updateQna() {
      this.$store.dispatch("QnAs/modifyQnas", this.qna);
      Swal.fire({
        icon: "success",
        title: "문의 수정이 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.push({
        name: "QnaDetail",
        params: { qnaid: this.qna.qnaid },
      });
    },
  },
};
</script>

<style scoped></style>
