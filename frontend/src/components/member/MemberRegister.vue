<template>
  <div class="container">
    <form @submit.prevent="regist(credentials)">
      <div>
        <label for="name"
          >아이디
          <input type="text" id="name" v-model="credentials.id" />
        </label>
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
      </div>
      <div>
        <label for="email">
          email
          <input type="text" id="email" v-model="credentials.email" :rules="emailRules" />
        </label>
      </div>
      <div>
        <label for="nickname"
          >닉네임
          <input type="text" id="nickname" v-model="credentials.nickname" />
        </label>
      </div>
      <div>
        <label for="address"
          >사는곳
          <input type="text" id="address" v-model="credentials.address" />
        </label>
      </div>
      <button type="submit">회원가입</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from "vuex";

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
      emailRules: [
        (value) =>
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
            value,
          ) || "이메일 형식을 확인하세요.",
      ],
    };
  },
  methods: {
    ...mapActions(["regist"]),
  },
};
</script>

<style scoped></style>
