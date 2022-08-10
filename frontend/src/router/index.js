import { createRouter, createWebHistory } from "vue-router";
import Member from "../views/Member.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberRegisterView from "../views/MemberRegisterView.vue";
// import MemberModify1 from "../components/member/MemberModify1.vue";
import MemberModify2 from "../components/member/MemberModify2.vue";
import MemberSecession from "../components/member/MemberSecession.vue";
import MemberLogout from "../components/member/MemberLogout.vue";
import MemberTitle from "../components/member/MemberTitle.vue";
import HomeView from "../views/HomeView.vue";

import Notice from "../views/Notice.vue";
import NoticeList from "../components/notice/NoticeList.vue";
import NoticeDetail from "../components/notice/NoticeDetail.vue";
import NoticeWrite from "../components/notice/NoticeWrite.vue";
import NoticeUpdate from "../components/notice/NoticeUpdate.vue";

import Videochat from "../views/Videochat.vue";
import VideochatMatching from "../components/VideochatMatching.vue";
import VideochatCreate from "../components/VideochatCreate.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/videochat",
    component: Videochat,
    children: [
      {
        path: "",
        name: "VideochatMatching",
        component: VideochatMatching,
      },
      {
        path: "create",
        name: "VideochatCreate",
        component: VideochatCreate,
      },
    ],
  },
  {
    path: "/member",
    component: Member,
    children: [
      {
        path: "login",
        name: "MemberLogin",
        component: MemberLogin,
      },
      {
        path: "register",
        name: "MemberRegister",
        component: MemberRegisterView,
      },
      // {
      //   path: "modify",
      //   name: "MemberModify1",
      //   component: MemberModify1,
      // },
      {
        path: "modify",
        name: "MemberModify2",
        component: MemberModify2,
      },
      {
        path: "secession",
        name: "MemberSecession",
        component: MemberSecession,
      },
      {
        path: "logout",
        name: "MemberLogout",
        component: MemberLogout,
      },
      {
        path: "title",
        name: "MemberTitle",
        component: MemberTitle,
      },
    ],
  },
  {
    path: "/notice",
    component: Notice,
    children: [
      {
        path: "",
        name: "NoticeList",
        component: NoticeList,
      },
      {
        path: "detail/:noticeId",
        name: "NoticeDetail",
        component: NoticeDetail,
      },
      {
        path: "update/:id",
        name: "NoticeUpdate",
        component: NoticeUpdate,
      },
      {
        path: "write",
        name: "NoticeWrite",
        component: NoticeWrite,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
