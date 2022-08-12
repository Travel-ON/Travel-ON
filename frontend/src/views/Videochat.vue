<template>
  <div>
    <div>매칭 화면</div>
    <router-view />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Swal from "sweetalert2";

export default {
  name: "VideochatView",
  computed: {
    ...mapGetters({
      isLoggedIn: "isLoggedIn",
    }),
  },
  created() {
    if (!this.isLoggedIn) {
      Swal.fire({
        title: "로그인이 필요한 서비스입니다.",
        text: "로그인 화면으로 이동할까요?",
        icon: "warning",
        showCancelButton: true,
        buttons: true,
        dangerMode: true,
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push({
            name: "MemberLogin",
          });
        } else {
          this.$router.push({
            name: "home",
          });
        }
      });
    }
  },
};
</script>

<style></style>
