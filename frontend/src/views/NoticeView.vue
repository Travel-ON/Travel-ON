<template>
  <div>
    <div id="title">Notice</div>
    <v-container>
      <v-row style="background-color: lightgrey">
        <v-col>번호</v-col>
        <v-col>제목</v-col>
        <v-col>작성날짜</v-col>
        <v-col>조회수</v-col>
      </v-row>
      <v-row>
        <v-col>1</v-col>
        <v-col>시스템 업데이트 안내</v-col>
        <v-col>2022.07.14</v-col>
        <v-col>127</v-col>
      </v-row>
    </v-container>
    <v-pagination v-model="page" :length="6"></v-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      page: 1,
    };
  },
};
</script>

<style>
#title {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 128px;
  height: 48px;
  left: 899px;
  top: 181px;
  justify-content: center;
  font-family: "Inter";
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
}
</style>
