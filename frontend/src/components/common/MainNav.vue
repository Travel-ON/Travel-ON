<template>
  <div style="position: fixed; top: 0; left: 0; right: 0; z-index: 3000">
    <v-toolbar height="80">
      <router-link :to="{ name: 'home' }">
        <v-btn style="width: 150px">
          <v-img
            src="https://user-images.githubusercontent.com/97648026/182758932-c401e00f-c153-4a48-9186-b16491197fa1.png"
            alt="logo"
            width="150px"
          />
        </v-btn>
      </router-link>
      <v-btn @click="TransferPage('VideochatCreate')">방만들기</v-btn>
      <v-btn @click="TransferPage('VideochatMa')">방매칭하기</v-btn>
      <v-btn @click="TransferPage('VideochatShare')">방코드입장</v-btn>
      <!-- <router-link to="/videochat">
        <v-btn>방매칭하기</v-btn>
      </router-link> -->
      <v-btn @click="TransferPage('Planner')">여행플래너</v-btn>
      <v-menu open-on-hover style="z-index: 3500">
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props"> 커뮤니티 </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item, index) in items_community" :key="index" :value="index">
            <v-list-item-title @click="$router.push({ name: item.name })">
              {{ item.title }}
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-spacer></v-spacer>
      <!-- false시 로그인 상태, true시 비로그인 상태 -->
      <div v-if="!isLoggedIn">
        <router-link :to="{ name: 'MemberLogin' }">
          <v-btn>로그인</v-btn>
        </router-link>
        <router-link :to="{ name: 'MemberRegister' }">
          <v-btn>회원가입</v-btn>
        </router-link>
      </div>
      <div v-else>
        <v-menu style="z-index: 3500">
          <template v-slot:activator="{ props }">
            <v-btn icon v-bind="props" @click="getAlarmList()">
              <v-badge v-if="alarmFlag" color="red" dot>
                <v-icon>mdi-bell</v-icon>
              </v-badge>
              <v-icon v-else>mdi-bell</v-icon>
            </v-btn>
          </template>
          <v-list dense>
            <v-subheader class="ml-5">알림</v-subheader>
            <v-btn fab x-small dark class="float-right" @click="clickRemoveAlarms">
              <v-icon>mdi-trash-can-outline</v-icon>
            </v-btn>
            <v-divider class="mt-5"></v-divider>
            <v-list-item v-for="(item, index) in alarms" :key="index" :value="index">
              <v-list-item-title @click="clickAlarm(item.content)">
                {{ item.content }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-menu open-on-hover style="z-index: 3500">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props">
              <div style="font-size: x-small">{{ title }}</div>
              <div>{{ currentUser }}</div>
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, index) in items_user" :key="index" :value="index">
              <v-list-item-title @click="$router.push({ name: `${item.name}` })">
                {{ item.title }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-toolbar>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "MainNav",
  data: () => ({
    // items_메뉴
    items_community: [
      { title: "공지사항", name: "NoticeList" },
      { title: "FAQ", name: "faqList" },
      { title: "Q&A", name: "QnaList" },
    ],
    items_user: [
      { title: "마이페이지", name: "" },
      { title: "칭호설정", name: "MemberSetTitle" },
      { title: "로그아웃", name: "MemberLogout" },
    ],
    alarms: [],
  }),
  methods: {
    ...mapActions(["logout", "fetchAlarmFlag"]),
    TransferPage(pageName) {
      if (this.isLoggedIn) {
        if (pageName !== "Planner" && !this.isLocation) {
          Swal.fire({
            title: "위치인증이 필요한 서비스입니다.",
            icon: "warning",
            buttons: true,
            dangerMode: true,
          });
        } else {
          this.$router.push({
            name: pageName,
          });
        }
      } else {
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
    getAlarmList() {
      axios({
        url: "http://localhost:3000/api/alarm/",
        // url: "http://i7b301.p.ssafy.io:3000/api/alarm/",
        method: "get",
        headers: { Authorization: `Bearer ${this.token}` },
      })
        .then(({ data }) => {
          this.alarms = data;
          this.fetchAlarmFlag(false);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    clickAlarm(alarm) {
      if (alarm.includes("칭호")) {
        this.$router.push({
          name: "MemberSetTitle",
        });
      } else if (alarm.includes("Q&A")) {
        this.$router.push({
          name: "QnaList",
        });
      }
    },
    clickRemoveAlarms() {
      if (this.alarms.length > 0) {
        Swal.fire({
          title: "알림함 비우기",
          text: "알림 내역을 삭제하실건가요?",
          icon: "question",
          showCancelButton: true,
          buttons: true,
          dangerMode: true,
        }).then((result) => {
          if (result.isConfirmed) {
            axios({
              url: "http://localhost:3000/api/alarm/",
              // url: "http://i7b301.p.ssafy.io:3000/api/alarm/",
              method: "delete",
              headers: { Authorization: `Bearer ${this.token}` },
            })
              .then((res) => {
                console.log(res);
                Swal.fire({
                  icon: "success",
                  title: "알림 내역을 삭제했습니다!",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.fetchAlarmFlag(false);
              })
              .catch((err) => {
                console.log(err);
                Swal.fire({
                  icon: "error",
                  title: "잠시후 다시 시도해주세요!",
                  showConfirmButton: false,
                  timer: 1000,
                });
              });
          }
        });
      } else {
        Swal.fire({
          icon: "warning",
          title: "삭제할 알림이 없습니다!",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
  },
  computed: {
    ...mapGetters({
      isLoggedIn: "isLoggedIn",
      isLocation: "isLocation",
      currentUser: "currentUser",
      title: "title",
      token: "token",
      alarmFlag: "alarmFlag",
    }),
  },
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
