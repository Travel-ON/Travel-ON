<template>
  <div>
    <div class="text-h3 font-weight-bold" style="margin: 28px auto; color: #50a0f0">회원가입</div>
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
