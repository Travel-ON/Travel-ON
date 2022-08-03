<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container class="">
        <v-row>
          <v-col
            ><v-text-field v-model="notice.title" :counter="10" :rules="titleRules" label="제목" required>
            </v-text-field
          ></v-col>
        </v-row>
        <v-row>
          <v-col
            ><v-textarea v-model="notice.content" :counter="300" label="내용" :rules="contentRules" required>
            </v-textarea
          ></v-col>
        </v-row>
        <div class="d-flex justify-end mb-6">
          <v-btn :disabled="!valid" color="success" class="d-flex justify-end" @click="[validate(), updateNotice()]">
            수정
          </v-btn>
        </div>
      </v-container>
    </v-form>
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
    validate() {
      this.$refs.form.validate();
    },
    updateNotice() {
      const newNotice = {
        no: this.notice.notice_id,
        title: this.notice.title,
        content: this.notice.content,
      };
      this.$store.dispatch("modifyNotice", newNotice);
    },
  },
};
</script>

<style></style>
