<template>
  <div class="modal">
    <button class="overlay" @click="$emit('close-modal', $planner)"></button>
    <div class="modal-card">
      <slot />
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: rgb(146, 185, 222);
}
.modalcard {
  position: relative;
  max-width: 80%;
  margin: auto;
  margin-top: 30px;
  padding: 20px;
  background-color: white;
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}
</style>
