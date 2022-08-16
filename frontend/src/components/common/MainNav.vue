<template>
  <div style="position: fixed; top: 0; left: 0; right: 0; z-index: 3000">
    <v-toolbar height="80" style="background-color: white">
      <router-link class="ml-3 mr-6" :to="{ name: 'home' }">
        <!-- <v-btn style="width: 150px"> -->
        <v-img src="@/assets/icon.png" alt="logo" width="60px" />
        <!-- </v-btn> -->
      </router-link>
      <v-btn
        @click="TransferPage('VideochatCreate')"
        style="font-weight: bold; font-size: 23px; color: #0057ff; padding-left: 5px; padding-right: 5px"
        >방만들기</v-btn
      >
      <v-btn
        @click="TransferPage('VideochatMatching')"
        style="font-weight: bold; font-size: 23px; color: #50a0f0; padding-left: 5px; padding-right: 5px"
        >방매칭하기</v-btn
      >
      <v-btn
        @click="TransferPage('VideochatShare')"
        style="font-weight: bold; font-size: 23px; color: #a1abff; padding-left: 5px; padding-right: 5px"
        >방코드입장</v-btn
      >
      <v-btn
        @click="TransferPage('Planner')"
        style="font-weight: bold; font-size: 23px; color: #3700b3; padding-left: 5px; padding-right: 5px"
        >여행플래너</v-btn
      >
      <v-menu open-on-hover style="z-index: 3500">
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props" style="font-weight: bold; font-size: 23px; padding-left: 5px; padding-right: 5px">
            커뮤니티
          </v-btn>
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
          <v-btn style="font-weight: bold; color: #50a0f0; font-size: 23px">로그인</v-btn>
        </router-link>
        <router-link :to="{ name: 'MemberRegister' }">
          <v-btn style="font-weight: bold; color: #50a0f0; font-size: 23px">회원가입</v-btn>
        </router-link>
      </div>
      <div v-else>
        <!-- <v-menu style="z-index: 3500"> -->
        <v-dialog v-model="dialog" scrollable>
          <template v-slot:activator="{ props }">
            <v-btn icon v-bind="props" @click="getAlarmList()">
              <span style="font-size: x-large">
                <v-badge v-if="alarmFlag" color="red" dot>
                  <v-icon color="blue" x-large>mdi-bell</v-icon>
                </v-badge>

                <v-icon color="blue" x-large v-else>mdi-bell</v-icon>
              </span>
            </v-btn>
          </template>
          <v-card>
            <v-card-title>알림</v-card-title>
            <v-divider></v-divider>
            <v-card-text style="height: 300px">
              <v-list-item v-for="(item, index) in alarms" :key="index" :value="index">
                <v-list-item-title @click="clickAlarm(item.content)">
                  {{ item.content }}
                </v-list-item-title>
              </v-list-item>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-btn fab x-small dark @click="clickRemoveAlarms">
                <v-icon>mdi-trash-can-outline</v-icon>
              </v-btn>
              <v-btn color="blue-darken-1" text @click="dialog = false"> Close </v-btn>
              <!-- <v-btn color="blue-darken-1" text @click="dialog = false"> Save </v-btn> -->
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-menu open-on-hover style="z-index: 3500">
          <template v-slot:activator="{ props }">
            <v-btn v-bind="props">
              <div style="font-size: 15px; margin-right: 10px; font-weight: bold">{{ `${title ? title : ""}` }}</div>
              <div style="font-size: large; font-weight: bold; color: #0057ff">{{ currentUser }}</div>
              님
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item v-for="(item, index) in items_user" :key="index" :value="index">
              <v-list-item-title @click="$router.push({ name: `${item.name}` })" style="font-weight: bold">
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
import spring from "@/api/spring_boot";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "MainNav",
  data: () => ({
    // items_메뉴
    dialogm1: "",
    dialog: false,
    items_community: [
      { title: "공지사항", name: "NoticeList" },
      { title: "FAQ", name: "faqList" },
      { title: "Q&A", name: "QnaList" },
    ],
    items_user: [
      { title: "마이페이지", name: "MypageView" },
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
        url: spring.alarm.alarmList(),
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
      this.dialog = false;
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
      this.dialog = false;
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
              url: spring.alarm.alarmList(),
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
  background-color: white;
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
