<template>
  <div class="container">
    <form @submit.prevent="submitForm">
      <div>
        <label for="name"
          >이름
          <input type="text" id="name" v-model="name" />
        </label>
      </div>
      <div>
        <label for="password"
          >비밀번호
          <input type="password" id="password" v-model="password" />
        </label>
      </div>
      <div>
        <label for="passwordConfirm"
          >비밀번호 확인
          <input type="password" id="passwordConfirm" v-model="passwordConfirm" />
        </label>
      </div>
      <div>
        <label for="email">
          email
          <input type="text" id="email" v-model="email" />
        </label>
      </div>
      <div>
        <label for="nickname"
          >닉네임
          <input type="text" id="nickname" v-model="nickname" />
        </label>
      </div>
      <div>
        <label for="address"
          >사는곳
          <input type="text" id="address" v-model="address" />
        </label>
      </div>
      <button type="submit">회원가입</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      password: "",
      passwordConfirm: "",
      email: "",
      nickname: "",
      address: "",
    };
  },
  methods: {
    submitForm() {
      console.log("dd");
    },
  },
};
</script>

<style scoped></style>
