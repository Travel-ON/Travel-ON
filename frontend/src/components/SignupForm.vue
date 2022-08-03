<template>
  <form @submit.prevent="submitForm">
    <div>
      <label for="username">
        id:
        <input id="username" type="text" v-model="username" />
      </label>
    </div>
    <div>
      <label for="password">
        pw:
        <input id="password" type="text" v-model="password" />
      </label>
    </div>
    <div>
      <label for="nickname">
        nickname:
        <input id="nickname" type="text" v-model="nickname" />
      </label>
    </div>
    <button type="submit">회원 가입</button>

    <p>{{ logMessage }}</p>
  </form>
</template>

<script>
import { registerUser } from "@/api/index";

export default {
  data() {
    return {
      username: "",
      password: "",
      nickname: "",

      logMessage: "",
    };
  },
  methods: {
    async submitForm() {
      const userData = {
        username: this.username,
        password: this.password,
        nickname: this.nickname,
      };
      const { data } = await registerUser(userData);

      this.logMessage = `${data.username} 님이 가입되었습니다.`;

      this.initForm();
    },
    initForm() {
      this.username = "";
      this.password = "";
      this.nickname = "";
    },
  },
};
</script>
