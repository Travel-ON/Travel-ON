import { createRouter, createWebHistory } from "vue-router";
import Member from "../views/Member.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberRegister from "../components/member/MemberRegister.vue";
import MemberModify1 from "../components/member/MemberModify1.vue";
import MemberModify2 from "../components/member/MemberModify2.vue";
import MemberSecession from "../components/member/MemberSecession.vue";
import HomeView from "../views/HomeView.vue";

import Notice from "../views/Notice.vue";
import NoticeList from "../components/notice/NoticeList.vue";
import NoticeDetail from "../components/notice/NoticeDetail.vue";
import NoticeWrite from "../components/notice/NoticeWrite.vue";
import NoticeUpdate from "../components/notice/NoticeUpdate.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
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
        component: MemberRegister,
      },
      {
        path: "modify",
        name: "MemberModify1",
        component: MemberModify1,
      },
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
        path: "detail/:id",
        name: "NoticeDetail",
        component: NoticeDetail,
      },
      {
        path: "update",
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
