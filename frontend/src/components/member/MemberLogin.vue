<template>
  <form @submit.prevent="login(credentials)">
    <div>
      <label for="id"
        >아이디
        <input id="id" type="text" v-model="credentials.id" />
      </label>
    </div>
    <div>
      <label for="password"
        >비밀번호
        <input id="password" type="text" v-model="credentials.password" />
      </label>
    </div>
    <button type="submit">로그인</button>
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
      // eslint-disable-next-line no-alert
      alert("로그아웃 후 진행해주세요!");
      this.$router.back();
    }
  },
  initForm() {
    this.id = "";
    this.password = "";
  },
};
</script>
