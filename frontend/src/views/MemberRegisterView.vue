<template>
  <div>
    <div class="text-h2 font-weight-bold" style="margin: 28px auto">회원가입</div>
    <div style="display: flex; justify-content: space-between">
      <div class="left-space"></div>
      <div class="main-space">
        <member-register />
      </div>
      <div class="right-space"></div>
    </div>
  </div>
</template>

<script>
import MemberRegister from "@/components/member/MemberRegister.vue";

export default {
  name: "MemberRegisterView",
  components: {
    MemberRegister,
  },
};
</script>

<style>
.main-space {
  width: 1128px;
  padding: 30px, 20px;
}
</style>
