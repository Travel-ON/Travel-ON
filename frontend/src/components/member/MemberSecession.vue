<template>
  <form @submit.prevent="submitForm">
    <div>
      <label for="username"
        >아이디
        <input id="username" type="text" v-model="username" />
      </label>
    </div>
    <div>
      <label for="password"
        >비밀번호
        <input id="password" type="text" v-model="password" />
      </label>
    </div>
    <button type="submit">회원탈퇴</button>
    <p>{{ logMessage }}</p>
  </form>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      logMessage: "",
    };
  },
  initForm() {
    this.username = "";
    this.password = "";
  },
};
</script>
