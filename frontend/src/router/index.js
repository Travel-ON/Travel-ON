import { createRouter, createWebHistory } from "vue-router";
// import CreateMeeting from "@/components/meetingpage/CreateMeeting.vue";

import Member from "../views/Member.vue";
import MemberLogin from "../components/member/MemberLogin.vue";
import MemberRegister from "../components/member/MemberRegister.vue";
// import MemberSecession from "../components/member/MemberSecession.vue";
import MemberLogout from "../components/member/MemberLogout.vue";
import MemberFindpwd from "../components/member/MemberFindpwd.vue";

import HomeView from "../views/HomeView.vue";

import PlannerView from "../views/PlannerView.vue";
import PlanModalTest from "../views/PlanModalTest.vue";

import Notice from "../views/Notice.vue";
import NoticeList from "../components/notice/NoticeList.vue";
import NoticeDetail from "../components/notice/NoticeDetail.vue";
import NoticeWrite from "../components/notice/NoticeWrite.vue";
import NoticeUpdate from "../components/notice/NoticeUpdate.vue";

import Faq from "../views/Faq.vue";
import FaqList from "../components/faq/FaqList.vue";

import Qna from "../views/Qna.vue";
import QnaList from "../components/qna/QnaList.vue";
import QnaWrite from "../components/qna/QnaWrite.vue";
import QnaDetail from "../components/qna/QnaDetail.vue";
import QnaUpdate from "../components/qna/QnaUpdate.vue";

import Videochat from "../views/Videochat.vue";
import VideochatCreate from "../components/videochat/VideochatCreate.vue";
import VideochatRoom from "../components/videochat/VideochatRoom.vue";
import VideochatMatching from "../components/videochat/VideochatMatching.vue";
import VideochatShare from "../components/videochat/VideochatShare.vue";

import MypageView from "../views/MypageView.vue";
import MemberSetTitle from "../components/mypage/MemberSetTitle.vue";
import MemberModify from "../components/mypage/MemberModify.vue";
import MemberModifyPwd from "../components/mypage/MemberModifyPwd.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/videochat/room",
    name: "VideochatRoom",
    component: VideochatRoom,
  },
  {
    path: "/videochat",
    name: "Videochat",
    component: Videochat,
    children: [
      {
        path: "matching",
        name: "VideochatMatching",
        component: VideochatMatching,
      },
      {
        path: "create",
        name: "VideochatCreate",
        component: VideochatCreate,
      },
      {
        path: "share",
        name: "VideochatShare",
        component: VideochatShare,
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
        component: MemberRegister,
      },
      {
        path: "logout",
        name: "MemberLogout",
        component: MemberLogout,
      },
      {
        path: "findpwd",
        name: "MemberFindpwd",
        component: MemberFindpwd,
      },
    ],
  },
  {
    path: "/mypage",
    name: "MypageView",
    component: MypageView,
    children: [
      {
        path: "",
        name: "MemberSetTitle",
        component: MemberSetTitle,
      },
      {
        path: "modify",
        name: "MemberModify",
        component: MemberModify,
      },
      {
        path: "modifypwd",
        name: "MemberModifyPwd",
        component: MemberModifyPwd,
      },
    ],
  },
  {
    path: "/qna",
    component: Qna,
    name: "Qna",
    children: [
      {
        path: "",
        name: "QnaList",
        component: QnaList,
      },
      {
        path: "write",
        name: "QnaWrite",
        component: QnaWrite,
      },
      {
        path: "detail/:qnaid",
        name: "QnaDetail",
        component: QnaDetail,
      },
      {
        path: "update/:id",
        name: "QnaUpdate",
        component: QnaUpdate,
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
  {
    path: "/faq",
    component: Faq,
    children: [
      {
        path: "",
        name: "faqList",
        component: FaqList,
      },
    ],
  },

  {
    path: "/planner",
    name: "Planner",
    component: PlannerView,
  },
  {
    path: "/test",
    name: "test",
    component: PlanModalTest,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
