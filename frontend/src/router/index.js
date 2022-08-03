import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "../views/LoginPage.vue";
import SignupPage from "../views/SignupPage.vue";
import HomeView from "../views/HomeView.vue";
<<<<<<< HEAD
=======

import Notice from "../views/Notice.vue";
import NoticeList from "../components/notice/NoticeList.vue";
import NoticeDetail from "../components/notice/NoticeDetail.vue";
import NoticeWrite from "../components/notice/NoticeCreate.vue";
import NoticeUpdate from "../components/notice/NoticeUpdate.vue";
>>>>>>> 342795de8d72d6b24027ef6b1d5695da2dc3b911

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
<<<<<<< HEAD
    path: "/login",
    name: "login",
    component: LoginPage,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignupPage,
=======
    path: "/notice",
    component: Notice,
    children: [
      {
        path: "",
        name: "NoticeList",
        component: NoticeList,
      },
      {
        path: "detail",
        name: "NoticeDetail",
        component: NoticeDetail,
      },
      {
        path: "update",
        name: "NoticeUpdate",
        component: NoticeUpdate,
      },
      {
        path: "create",
        name: "NoticeWrite",
        component: NoticeWrite,
      },
    ],
>>>>>>> 342795de8d72d6b24027ef6b1d5695da2dc3b911
  },

  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
