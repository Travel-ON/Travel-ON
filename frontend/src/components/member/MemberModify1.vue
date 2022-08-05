<template>
  <form @submit.prevent="submitForm">
    <div>
      <label for="usernamemodify"
        >이메일 입력
        <input id="usernamemodify" type="text" v-model="usernamemodify" />
      </label>
    </div>
    <button type="submit">본인인증</button>

    <p>{{ logMessage }}</p>
  </form>
</template>
