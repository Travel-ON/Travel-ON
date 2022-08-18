<template>
  <div>NotFound</div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  created() {
    Swal.fire({
      title: "존재하지 않는 페이지",
      text: "홈화면으로 이동합니다",
      icon: "warning",
      buttons: true,
      confirmButtonText: "확인",
      allowOutsideClick: false,
    }).then((result) => {
      if (result.isConfirmed) {
        this.$router.push({
          name: "home",
        });
      }
    });
  },
};
</script>

<style></style>
