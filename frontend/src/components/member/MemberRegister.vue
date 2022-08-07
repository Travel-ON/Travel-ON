<template>
  <div class="container">
    <form @submit.prevent="regist(credentials)">
      <div>
        <label for="name"
          >아이디
          <input type="text" id="name" v-model="credentials.id" />
        </label>
        <button @click.self.prevent="idCheck(credentials.id)">중복 검사</button>
        <span v-if="!idChecked"> 아이디 중복을 확인해 주세요. </span>
      </div>
      <div>
        <label for="password"
          >비밀번호
          <input type="password" id="password" v-model="credentials.password" />
        </label>
      </div>
      <div>
        <label for="passwordConfirm"
          >비밀번호 확인
          <input type="password" id="passwordConfirm" v-model="credentials.passwordConfirm" />
        </label>
        <span v-if="!isPassEqual"> 비밀번호가 일치하지 않습니다. </span>
      </div>
      <div>
        <label for="email">
          email
          <input type="text" id="email" v-model="credentials.email" />
        </label>
        <span v-if="!emailRules"> 이메일 형식을 확인해 주세요. </span>
      </div>
      <div>
        <label for="nickname"
          >닉네임
          <input type="text" id="nickname" v-model="credentials.nickname" />
        </label>
        <button @click.self.prevent="nickCheck(credentials.nickname)">중복 검사</button>
        <span v-if="!nickChecked"> 닉네임 중복을 확인해 주세요. </span>
      </div>
      <div>
        <label for="address"
          >사는곳
          <input type="text" id="address" v-model="credentials.address" />
        </label>
      </div>
      <button type="submit" :disabled="!(idChecked && nickChecked)">회원가입</button>
    </form>
  </div>
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
      idChecked: false,
      nickChecked: false,
      emailRules: (value) =>
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
          value,
        ),
    };
  },
  computed: {
    isPassEqual: (credentials) => credentials.password === credentials.passwordConfirm,
  },
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
      axios({
        url: spring.accounts.idCheck(),
        method: "post",
        params: { id },
      })
        .then((res) => {
          console.log(res);
          this.idChecked = true;
          alert("아이디 중복 검사 완료!");
        })
        .catch((err) => {
          alert("이미 있는 아이디 입니다!");
          console.log(err);
        });
    },
    nickCheck(nickname) {
      console.log(nickname);
      axios({
        url: spring.accounts.nickCheck(),
        method: "post",
        params: { nickname },
      })
        .then((res) => {
          console.log(res);
          this.nickChecked = true;
          alert("닉네임 중복 검사 완료!");
        })
        .catch((err) => {
          alert("이미 있는 닉네임 입니다!");
          console.log(err);
        });
    },
  },
};
</script>

<style scoped></style>
