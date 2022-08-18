<template>
  <div>
    <div style="font-weight: bold; font-size: 40px; text-align: center; margin: 20px 0; width: 1080px; color: #50a0f0">
      회원정보 수정
    </div>
    <v-card style="padding: 80px 15%; margin-bottom: 40px" color="#e1effd">
      <v-form ref="form" v-model="valid" lazy-validation>
        <div style="display: flex">
          <div style="width: 70%; margin-right: 20px">
            <v-text-field label="아이디" v-model="credentials.id" required bg-color="#eeeee" readonly></v-text-field>
          </div>
        </div>
        <div style="display: flex">
          <div style="width: 70%; margin-right: 20px">
            <v-text-field
              label="닉네임"
              v-model="credentials.nickname"
              required
              :rules="nicknameRules"
              bg-color="#c9deff"
            ></v-text-field>
          </div>
          <div style="width: 30%">
            <v-btn
              @click="nickCheck(credentials.nickname)"
              :disabled="nickDisable"
              :prepend-icon="nickChecked ? 'mdi-check-circle' : 'mdi-close-circle'"
              :color="nickChecked ? '#c9deff' : 'red-lighten-4'"
              style="margin-top: 10px; left: 0"
              >중복 검사</v-btn
            >
            <div v-if="!nickChecked" style="margin-top: 12px; font-size: 16px; color: #b00020">
              닉네임 중복을 확인해 주세요.
            </div>
          </div>
        </div>
        <div>
          <v-text-field
            label="e-mail"
            v-model="credentials.email"
            required
            :rules="emailRules"
            @click:append="passwordConfirmShow = !passwordConfirmShow"
            bg-color="#c9deff"
          ></v-text-field>
        </div>
        <div style="display: flex">
          <v-select
            v-model="credentials.sidoCode"
            :items="sido"
            item-title="name"
            item-value="code"
            label="거주 지역"
            persistent-hint
            single-line
            bg-color="#c9deff"
          ></v-select>
        </div>
      </v-form>
    </v-card>
    <v-btn
      :disabled="!valid"
      @click="clickModify()"
      size="x-large"
      color="#c9deff"
      style="margin-bottom: 40px"
      bg-color="#c9deff"
      >수정</v-btn
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
        email: "",
        password: null,
        nickname: "",
        sidoCode: "",
      },
      info: {
        email: "",
        nickname: "",
        sidoCode: "",
      },
      sido: [
        { code: "4200000000", name: "강원" },
        { code: "4100000000", name: "경기" },
        { code: "4800000000", name: "경남" },
        { code: "4700000000", name: "경북" },
        { code: "2900000000", name: "광주" },
        { code: "2700000000", name: "대구" },
        { code: "3000000000", name: "대전" },
        { code: "2600000000", name: "부산" },
        { code: "1100000000", name: "서울" },
        { code: "3611000000", name: "세종특별자치시" },
        { code: "3100000000", name: "울산" },
        { code: "2800000000", name: "인천" },
        { code: "4600000000", name: "전남" },
        { code: "4500000000", name: "전북" },
        { code: "5000000000", name: "제주특별자치도" },
        { code: "4400000000", name: "충남" },
        { code: "4300000000", name: "충북" },
      ],
      valid: true,
      nickDisable: true,
      nickChecked: "",
      nicknameRules: [
        (v) => !!v || "닉네임을 입력해주세요.",
        (v) => v.length <= 10 || "닉네임은 10자 이내로 생성해주세요.",
      ],
      emailRules: [
        (v) => !!v || "E-mail을 입력해주세요.",
        (v) =>
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
            v,
          ) || "E-mail형식을 확인해주세요.",
      ],
    };
  },
  watch: {
    /* eslint-disable */
    "credentials.nickname": function () {
      if (this.credentials.nickname === this.info.nickname) {
        this.nickChecked = this.info.nickname;
        this.nickDisable = true;
      } else {
        this.nickChecked = "";
        this.nickDisable = false;
        this.nicknameRules.forEach((element) => {
          if (typeof element(this.credentials.nickname) !== "boolean") {
            this.nickDisable = true;
          }
        });
      }
    },
  },
  computed: {
    ...mapGetters({ token: "token", isLoggedIn: "isLoggedIn" }),
  },
  created() {
    if (this.isLoggedIn) {
      axios({
        url: spring.accounts.detail(),
        method: "get",
        headers: { Authorization: `Bearer ${this.token}` },
      })
        .then(({ data }) => {
          console.log(data);
          this.credentials.id = data.id;
          this.credentials.email = data.email;
          this.credentials.nickname = data.nickname;
          this.credentials.sidoCode = data.sidoCode;

          this.info.email = data.email;
          this.info.nickname = data.nickname;
          this.info.sidoCode = data.sidoCode;
        })
        .catch((err) => {
          console.error(err);
          Swal.fire({
            title: "조회실패",
            text: "잠시후 다시 시도해주세요!",
            icon: "error",
            confirmButtonText: "확인",
          });
          this.$router.push({
            name: "home",
          });
        });
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
    clickModify() {
      // if (this.credentials.nickname === this.info.nickname) {
      //   this.credentials.nickname = null;
      // }
      if (this.credentials.email === this.info.email) {
        this.credentials.email = null;
      }
      if (this.credentials.sidoCode === this.info.sidoCode) {
        this.credentials.sidoCode = null;
      }
      this.modify(this.credentials);
    },
  },
};
</script>

<style scoped></style>
