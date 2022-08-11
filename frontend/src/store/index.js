import { createStore } from "vuex";
<<<<<<< HEAD
// import router from "@/router";
=======
>>>>>>> 0df7aaf59735fa498084cf9074709ad6e0efa714
import { Notices } from "@/store/modules/notices";
// import { createApi } from "@/api";
import { Accounts } from "./modules/accounts";
import { QnAs } from "./modules/qnas";
import { Locations } from "./modules/locations";
import { Weather } from "./modules/weather";

// const api = createApi();

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    Accounts,
    QnAs,
    Notices,
    Locations,
    Weather,
  },
});
