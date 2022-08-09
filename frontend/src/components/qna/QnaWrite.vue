<template>
  <div>
    <v-container class="">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="title" :counter="10" :rules="titleRules" label="제목" required></v-text-field>
        <v-textarea v-model="content" :counter="300" label="내용" :rules="contentRules" required></v-textarea>
        <div class="d-flex justify-end mb-6">
          <v-btn :disabled="!valid" color="success" class="d-flex justify-end" @click="[validate(), writeQna()]">
            등록
          </v-btn>
        </div>
      </v-form>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "QnaWrite",
  data() {
    return {
      valid: true,
      title: "",
      titleRules: [
        (v) => !!v || "제목을 입력하세요",
        (v) => (v && v.length <= 10) || "제목은 10글자 이하로 입력하세요",
      ],
      content: "",
      contentRules: [(v) => !!v || "내용을 입력하세요", (v) => (v && v.length <= 300) || "내용은 최대 200글자입니다."],
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    writeQna() {
      const newQna = {
        title: this.title,
        content: this.content,
      };
      this.$store.dispatch("QnAs/writeQna", newQna);
    },
  },
};
</script>

<style scoped></style>
