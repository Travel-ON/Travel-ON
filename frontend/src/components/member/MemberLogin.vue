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
        <input id="password" type="password" v-model="credentials.password" />
      </label>
    </div>
    <button type="submit">로그인</button>
    <p>{{ logMessage }}</p>
  </form>
  <router-link :to="{ name: 'MemberFindpwd' }">
    <v-btn>비밀번호찾기</v-btn>
  </router-link>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Swal from "sweetalert2";

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
      Swal.fire({
        icon: "error",
        title: "로그아웃 후 진행해주세요!",
        showConfirmButton: false,
        timer: 1000,
      });
      this.$router.back();
    }
  },
  initForm() {
    this.id = "";
    this.password = "";
  },
};
</script>
