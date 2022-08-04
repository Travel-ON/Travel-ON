import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "../views/LoginPage.vue";
import SignupPage from "../views/SignupPage.vue";
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
    path: "/login",
    name: "login",
    component: LoginPage,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignupPage,
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
