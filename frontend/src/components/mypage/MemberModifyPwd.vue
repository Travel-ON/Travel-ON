<template>
  <div>
    <div style="font-weight: bold; font-size: 40px; margin: 12px 0; width: 1080px; color: #50a0f0">비밀번호 재설정</div>
    <v-card style="padding: 80px 15%; margin-bottom: 40px" color="#e1effd">
      <v-form ref="form" v-model="valid" lazy-validation>
        <div>
          <v-text-field
            label="새 비밀번호"
            v-model="credentials.password"
            :rules="passwordRules"
            @click:append="passwordShow = !passwordShow"
            type="password"
            bg-color="#c9deff"
          ></v-text-field>
        </div>
        <div>
          <v-text-field
            label="새 비밀번호 확인"
            v-model="credentials.passwordConfirm"
            :rules="passwordConfirmRules"
            @click:append="passwordConfirmShow = !passwordConfirmShow"
            type="password"
            bg-color="#c9deff"
          ></v-text-field>
        </div>
      </v-form>
    </v-card>
    <v-btn
      :disabled="!valid"
      @click="modify(credentials)"
      size="x-large"
      color="#c9deff"
      style="margin-bottom: 40px"
      bg-color="#c9deff"
      >설정</v-btn
    >
  </div>
</template>

<script>
import axios from "axios";
import { mapActions, mapGetters } from "vuex";
import spring from "@/api/spring_boot";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      credentials: {
        id: "",
        password: "",
        passwordConfirm: "",
        email: null,
        nickname: null,
        sidoCode: null,
      },
      valid: true,
      passwordShow: false,
      passwordConfirmShow: false,
      passwordRules: [
        (v) => !!v || "비밀번호를 입력해주세요.",
        (v) =>
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/.test(v) ||
          "비밀번호는 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하셔야 합니다.",
      ],
      passwordConfirmRules: [
        (v) => !!v || "비밀번호를 다시 입력해주세요.",
        (v) => v === this.credentials.password || "비밀번호가 일치하지 않습니다.",
      ],
    };
  },
  computed: {
    ...mapGetters({ token: "token", isLoggedIn: "isLoggedIn", currentUserId: "currentUserId" }),
  },
  created() {
    if (this.isLoggedIn) {
      this.pwdCheck();
    } else {
      alert("잘못된 접근");
      this.$router.push({
        name: "home",
      });
    }
  },
  mounted() {},
  methods: {
    ...mapActions(["modify"]),
    async pwdCheck() {
      const { value: password } = await Swal.fire({
        title: "🔐 비밀번호 입력 🔐",
        input: "password",
        inputLabel: "Password",
        inputPlaceholder: "비밀번호를 입력하세요",
        inputAttributes: {
          maxlength: 17,
          autocapitalize: "off",
          autocorrect: "off",
        },
      });
      axios({
        url: spring.accounts.pwdCheck(),
        method: "post",
        headers: { Authorization: `Bearer ${this.token}` },
        data: { password },
      })
        .then(() => {
          this.credentials.id = this.currentUserId;
        })
        .catch((err) => {
          console.error(err);
          Swal.fire({
            title: "인증실패",
            icon: "error",
            confirmButtonText: "확인",
          });
          this.$router.push({
            name: "home",
          });
        });
    },
  },
};
</script>

<style scoped></style>
