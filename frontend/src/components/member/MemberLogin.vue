<template>
  <div class="bg" style="display: flex">
    <div style="flex: 1" />
    <div style="flex: 2">
      <div
        class="mt-10"
        style="background-color: #ffffff; display: flex; border-radius: 15px; box-shadow: 5px 5px 5px 5px gray"
      >
        <div style="display: flex; flex: 1">
          <v-img
            src="https://user-images.githubusercontent.com/26339069/185124395-e7f5f8ad-3572-4c71-8ee0-b429049f4de1.jpeg"
            alt="여행사진"
            style=" height=100%; width=100%;border-radius: 15px 0px 0px 15px"
          />
        </div>
        <div style="flex: 1">
          <v-form ref="form" lazy-validation @submit.prevent="login(credentials)">
            <div class="mt-15" style="font-size: x-large">Travel-ON 로그인</div>
            <div style="display: flex">
              <div style="flex: 1" />
              <div id="inputId" style="flex: 8">
                <v-text-field
                  bg-color="#F0EDED"
                  label="아이디"
                  variant="solo"
                  v-model="credentials.id"
                  required
                ></v-text-field>
              </div>
              <div style="flex: 1" />
            </div>
            <div style="display: flex">
              <div style="flex: 1" />
              <div id="inputPassword" style="flex: 8">
                <v-text-field
                  label="비밀번호"
                  bg-color="#F0EDED"
                  variant="solo"
                  :type="showPassword ? 'text' : 'password'"
                  :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  v-model="credentials.password"
                  @click:append="showPassword = !showPassword"
                  style="font-family: Georgia; font-weight: bold"
                ></v-text-field>
              </div>
              <div style="flex: 1" />
            </div>
            <div style="display: flex">
              <div style="flex: 1" />
              <div style="flex: 8">
                <v-btn style="background-color: #c9deff" block type="submit" size="large">로그인</v-btn>
              </div>
              <div style="flex: 1" />
            </div>

            <div>
              <span id="findPW" style="font-size: small">
                <router-link :to="{ name: 'MemberFindpwd' }">비밀번호를 잊으셨나요? </router-link>
              </span>
              <span style="font-size: small; margin-right: 3px; margin-left: 3px"> | </span>
              <span id="findPW" style="font-size: small">
                <router-link :to="{ name: 'MemberRegister' }">계정이 없으신가요? </router-link>
              </span>
            </div>
          </v-form>
        </div>
      </div>
    </div>
    <div style="flex: 1" />
  </div>
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
.bg {
  width: 100vw;
  height: 100%;
}
#findPW:hover {
  text-decoration-line: underline;
}

#inputId {
  margin-top: 40px;
}
</style>
