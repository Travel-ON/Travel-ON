<!-- eslint-disable no-alert -->
<template>
  <v-card style="padding: 80px 15%">
    <v-form ref="form" v-model="valid" lazy-validation>
      <div style="display: flex">
        <div style="width: 70%; margin-right: 20px">
          <v-text-field label="아이디" v-model="credentials.id" required :rules="nameRules"></v-text-field>
        </div>
        <div style="width: 30%">
          <v-btn
            @click="idCheck(credentials.id)"
            :prepend-icon="idChecked ? 'mdi-check-circle' : 'mdi-close-circle'"
            :color="idChecked ? '#c9deff' : 'red-lighten-4'"
            style="margin-top: 10px; left: 0"
            >중복 검사</v-btn
          >
          <div v-if="!idChecked" style="margin-top: 12px">아이디 중복을 확인해 주세요.</div>
        </div>
      </div>
      <div style="display: flex">
        <div style="width: 70%; margin-right: 20px">
          <v-text-field label="닉네임" v-model="credentials.nickname" required :rules="nicknameRules"></v-text-field>
        </div>
        <div style="width: 30%">
          <v-btn
            @click="nickCheck(credentials.nickname)"
            :prepend-icon="nickChecked ? 'mdi-check-circle' : 'mdi-close-circle'"
            :color="nickChecked ? '#c9deff' : 'red-lighten-4'"
            style="margin-top: 10px; left: 0"
            >중복 검사</v-btn
          >
          <div v-if="!nickChecked" style="margin-top: 12px">닉네임 중복을 확인해 주세요.</div>
        </div>
      </div>
      <div>
        <v-text-field
          label="비밀번호"
          v-model="credentials.password"
          required
          :rules="passwordRules"
          @click:append="passwordShow = !passwordShow"
          type="password"
        ></v-text-field>
      </div>
      <div>
        <v-text-field
          label="비밀번호 확인"
          v-model="credentials.passwordConfirm"
          required
          :rules="passwordConfirmRules"
          @click:append="passwordConfirmShow = !passwordConfirmShow"
          type="password"
        ></v-text-field>
      </div>
      <div>
        <v-text-field
          label="e-mail"
          v-model="credentials.email"
          required
          :rules="emailRules"
          @click:append="passwordConfirmShow = !passwordConfirmShow"
        ></v-text-field>
      </div>
      <div>
        <v-text-field label="주소" v-model="credentials.address" required :rules="addressRules"></v-text-field>
      </div>
      <v-btn :disabled="!valid" @click="regist(credentials)" size="x-large" color="#c9deff">회원가입</v-btn>
    </v-form>
  </v-card>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import spring from "@/api/spring_boot";

export default {
  data() {
    return {
      credentials: {
        id: "",
        password: "",
        passwordConfirm: "",
        email: "",
        nickname: "",
        address: "",
      },
      valid: true,
      passwordShow: false,
      passwordConfirmShow: false,
      idChecked: "",
      nickChecked: "",
      nameRules: [
        (v) => !!v || "아이디를 입력해주세요.",
        (v) => /^[a-zA-Z]+[0-9]+[a-z0-9A-Z]*$/.test(v) || "아이디는 영문자 + 숫자로만 생성가능합니다.",
        (v) => (v.length >= 6 && v.length <= 16) || "아이디 길이는 6자이상 16자이하로 생성해주세요.",
      ],
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
      nicknameRules: [(v) => !!v || "닉네임을 입력해주세요."],
      emailRules: [
        (v) => !!v || "E-mail을 입력해주세요.",
        (v) =>
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
            v,
          ) || "E-mail형식을 확인해주세요.",
      ],
      addressRules: [
        (v) => !!v || "주소를 입력해주세요.",
        (v) =>
          /(([가-힣A-Za-z·\d~\-.]{2,}(로|길).[\d]+)|([가-힣A-Za-z·\d~\-.]+(읍|동)))/.test(v) ||
          "유효한 주소를 입력해주세요.(읍/동까지)",
      ],
    };
  },
  watch: {
    // eslint-disable-next-line func-names
    "credentials.id": function () {
      this.idChecked = "";
    },
    // eslint-disable-next-line func-names
    "credentials.nickname": function () {
      this.nicknameChecked = "";
    },
  },
  computed: {},
  methods: {
    ...mapActions(["regist"]),
    idCheck(id) {
      /*
      id중복검사
        성공시
          idChecked 상태값 True
        실패시
          -

        idChecked True일때만 가입 진행
      */
      console.log(id);
      if (id !== "") {
        axios({
          url: spring.accounts.idCheck(),
          method: "post",
          params: { id },
        })
          .then((res) => {
            console.log(res);
            this.idChecked = id;
            alert("아이디 중복 검사 완료!");
          })
          .catch((err) => {
            alert("이미 있는 아이디 입니다!");
            console.log(err);
          });
      } else {
        alert("아무것도 입력하지 않으셨습니다.");
      }
    },
    nickCheck(nickname) {
      if (nickname !== "") {
        console.log(nickname);
        axios({
          url: spring.accounts.nickCheck(),
          method: "post",
          params: { nickname },
        })
          .then((res) => {
            console.log(res);
            this.nickChecked = nickname;
            alert("닉네임 중복 검사 완료!");
          })
          .catch((err) => {
            alert("이미 있는 닉네임 입니다!");
            console.log(err);
          });
      } else {
        alert("아무것도 입력하지 않으셨습니다.");
      }
    },
  },
};
</script>

<style scoped></style>
