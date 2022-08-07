<template>
  <form @submit.prevent="submitForm">
    <div>
      <label for="usernamemodify"
        >아이디 변경
        <input id="usernamemodify" type="text" v-model="usernamemodify" />
      </label>
    </div>
    <div>
      <label for="passwordmodify"
        >비밀번호 변경
        <input id="passwordmodify" type="text" v-model="passwordmodify" />
      </label>
    </div>
    <div>
      <label for="nicknamemodify"
        >닉네임 변경
        <input id="nicknamemodify" type="text" v-model="nicknamemodify" />
      </label>
    </div>
    <button type="submit">완료</button>

    <p>{{ logMessage }}</p>
  </form>
</template>

<script>
import { mapActions } from "vuex";

export default {
  created() {
    this.detail();
  },
  methods: {
    ...mapActions(["detail"]),
  },
};
</script>
