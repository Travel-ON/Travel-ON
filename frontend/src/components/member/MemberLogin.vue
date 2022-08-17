<template>
  <v-container>
    <div class="text-h3 font-weight-bold" style="margin: 28px auto; color: #50a0f0">로그인</div>
    <v-card id="loginCard" color="#AACCFF">
      <v-form ref="form" lazy-validation @submit.prevent="login(credentials)">
        <v-row id="inputId" style="display: flex" class="ml-16">
          <v-col style="width: 70%; margin-right: 20px; max-width: 500px">
            <v-text-field label="아이디" v-model="credentials.id" required bg-color="#c9deff"></v-text-field>
          </v-col>
        </v-row>
        <v-row id="inputPassword" style="display: flex" class="ml-16">
          <v-col style="width: 50%; margin-right: 20px; max-width: 540px">
            <v-text-field
              bg-color="#c9deff"
              label="비밀번호"
              :type="showPassword ? 'text' : 'password'"
              hint="비밀번호는 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하셔야 합니다."
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              v-model="credentials.password"
              @click:append="showPassword = !showPassword"
              style="font-family: Georgia; font-weight: bold"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row class="d-flex justify-space-between">
          <v-col>
            <v-row class="justify-start">
              <v-col id="findPW">
                <router-link :to="{ name: 'MemberFindpwd' }">비밀번호를 잊으셨나요? </router-link>
              </v-col>
            </v-row>
            <v-row class="justify-start">
              <v-col id="findPW">
                <router-link :to="{ name: 'MemberRegister' }">계정이 없으신가요? </router-link>
              </v-col>
            </v-row>
          </v-col>
          <v-col>
            <v-btn type="submit">로그인</v-btn>
            <p>{{ logMessage }}</p>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      showPassword: false,
      credentials: {
        id: "",
        password: "",
      },
      logMessage: "",
    };
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  methods: {
    ...mapActions(["login"]),
  },
  created() {
    if (this.isLoggedIn) {
      Swal.fire({
        icon: "error",
        title: "로그아웃 후 진행해주세요!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.back();
    }
  },
  initForm() {
    this.id = "";
    this.password = "";
  },
};
</script>
<style scoped>
#findPW:hover {
  text-decoration-line: underline;
}

#inputId {
  margin-top: 40px;
}
</style>
