<template>
  <div style="position: fixed; top: 0; left: 0; right: 0; z-index: 3000">
    <v-toolbar height="116px">
      <v-btn>(트래블론 로고)</v-btn>
      <v-btn>방만들기</v-btn>
      <v-btn>방매칭하기</v-btn>
      <v-btn>여행플래너</v-btn>
      <v-menu open-on-hover style="z-index: 3500">
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props"> 커뮤니티 </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item, index) in items_community" :key="index" :value="index">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-spacer></v-spacer>
      <!-- false시 로그인 상태, true시 비로그인 상태 -->
      <div v-if="false">
        <v-btn> 로그인 </v-btn>
        <v-btn> 회원가입 </v-btn>
      </div>
      <div v-else>
        <v-menu open-on-hover style="z-index: 3500">
          <template v-slot:activator="{ props }">
            <v-btn icon v-bind="props">
              <v-badge color="red" dot>
                <v-icon>mdi-bell</v-icon>
              </v-badge>
            </v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, index) in items_new" :key="index" :value="index">
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-menu open-on-hover style="z-index: 3500">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props">
              <div style="font-size: x-small">대전 풋내기</div>
              <div>{{ currentUser }}</div>
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, index) in items_user" :key="index" :value="index">
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: "MainNav",
  data: () => ({
    // items_메뉴
    items_community: [{ title: "공지사항" }, { title: "FAQ" }, { title: "Q&A" }],
    items_user: [{ title: "마이페이지" }, { title: "로그아웃" }],
    items_new: [{ title: "[Q&A] 에 답변이 달렸습니다." }, { title: "[대전 마스터] 업적을 달성하셨습니다." }],
  }),
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
