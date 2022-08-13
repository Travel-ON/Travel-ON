<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container class=""
        ><v-row>
          <v-col class="d-flex mb-6">
            <v-btn depressed color="yellow" @click="moveToList"> 뒤로가기 </v-btn>
          </v-col>
        </v-row>
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
          <v-btn color="success" class="d-flex justify-end" @click="[validate(), updateNotice()]"> 수정 </v-btn>
        </div>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Swal from "sweetalert2";

export default {
  computed: {
    ...mapState("Notices", ["notice"]),
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
    moveToList() {
      this.$router.push({
        name: "NoticeList",
      });
    },
    validate() {
      this.$refs.form.validate();
    },
    updateNotice() {
      // 날짜 포맷 ( yyyy-mm-ddThh:MM:ss )
      const today = new Date();
      today.setHours(today.getHours() + 9);
      this.notice.noticeDate = today.toISOString().replace("T", " ").substring(0, 19);
      this.$store.dispatch("Notices/modifyNotice", this.notice);

      Swal.fire({
        icon: "success",
        title: "공지사항 수정이 완료되었습니다!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.push({
        name: "NoticeDetail",
        params: { noticeId: this.notice.noticeId },
      });
    },
  },
};
</script>

<style></style>
