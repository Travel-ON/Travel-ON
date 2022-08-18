<template>
  <v-container>
    <div class="text-h2 font-weight-bold" style="margin: 28px auto">비밀번호 찾기</div>
    <v-card id="loginCard" color="#e1effd">
      <v-form ref="form" @submit.prevent="userPassInit(credentials)">
        <v-row id="inputId" style="display: flex" class="ml-16">
          <v-col style="width: 70%; margin-right: 20px; max-width: 500px">
            <v-text-field label="아이디" v-model="credentials.id" required bg-color="#c9deff"></v-text-field>
          </v-col>
        </v-row>
        <v-row id="inputEmail" style="display: flex" class="ml-16">
          <v-col style="width: 50%; margin-right: 20px; max-width: 500px">
            <v-text-field bg-color="#c9deff" label="이메일" v-model="credentials.email"></v-text-field>
          </v-col>
        </v-row>
        <v-row class="d-flex justify-space-between">
          <v-col>
            <v-btn type="submit">비밀번호 찾기</v-btn>
            <p>{{ logMessage }}</p>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      credentials: {
        id: "",
        email: "",
      },
      logMessage: "",
    };
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  methods: {
    ...mapActions(["userPassInit"]),
  },
  created() {
    if (this.isLoggedIn) {
      // eslint-disable-next-line no-alert
      this.$router.back();
    }
  },
  initForm() {
    this.id = "";
    this.email = "";
  },
};
</script>
<style scoped>
#inputId {
  margin-top: 40px;
}
</style>
