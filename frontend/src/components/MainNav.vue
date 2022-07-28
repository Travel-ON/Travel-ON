<template>
  <div>
    <v-toolbar extended extension-height="80">
      <v-toolbar-title>Title</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </v-toolbar>
  </div>
  <!-- <div class="nav">
    <div class="nav_left">
      <div>
        <a href="#">(트래블론 로고)</a>
      </div>
      <div>
        <a href="#">방만들기</a>
      </div>
      <div>
        <a href="#">방매칭하기</a>
      </div>
      <div>
        <a href="#">커뮤니티</a>
      </div>
    </div>
    <div class="nav_right" v-if="false">
      <div>
        <a href="#">로그인</a>
      </div>
      <div>
        <a href="#">회원가입</a>
      </div>
    </div>
    <div class="nav_right" v-else>
      <div>
        <a href="#">(알림 아이콘)</a>
      </div>
      <div>
        <div style="font-size: x-small">대전 풋내기</div>
        <div>
          <a href="#">김태훈</a>
        </div>
      </div>
    </div>
  </div> -->
</template>

<script>
export default {
  name: "MainNav",
};
</script>

<style>
a {
  text-decoration: none;
  color: black;
}
.nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  align-content: center;
  padding: 10px;
  width: 100%;
  height: 60px;
  background-color: #999;
  color: black;
}
.nav_left {
  display: flex;
  padding: auto;
}
.nav_left > div {
  padding: 10px;
  font-size: medium;
}
.nav_right {
  display: flex;
  padding: auto;
  margin-right: 20px;
}
.nav_right > div {
  padding: 10px;
  font-size: medium;
}
</style>
