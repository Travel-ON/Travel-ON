<template>
  <div style="display: flex; justify-content: space-between">
    <div style="flex: 1" />
    <div style="flex: 3" class="main-space">
      <router-view />
    </div>
    <div style="flex: 1" />
  </div>
</template>

<script>
export default {
  name: "MemberView",
};
</script>
