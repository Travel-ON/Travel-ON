<template>
  <form @submit.prevent="userPassInit(credentials)">
    <div>
      <label for="id"
        >아이디
        <input id="id" type="text" v-model="credentials.id" />
      </label>
    </div>
    <div>
      <label for="email"
        >이메일
        <input id="email" type="text" v-model="credentials.email" />
      </label>
    </div>
    <button type="submit">비밀번호 찾기</button>
    <p>{{ logMessage }}</p>
  </form>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      credentials: {
        id: "",
        email: "",
      },
      logMessage: "",
    };
  },
  computed: {
    ...mapGetters(["isLoggedIn"]),
  },
  methods: {
    ...mapActions(["userPassInit"]),
  },
  created() {
    if (this.isLoggedIn) {
      // eslint-disable-next-line no-alert
      alert("로그아웃 후 진행해주세요!");
      this.$router.back();
    }
  },
  initForm() {
    this.id = "";
    this.email = "";
  },
};
</script>
