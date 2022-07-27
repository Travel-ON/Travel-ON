import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

createApp(App).use(store).use(router).mount("#app");

new Vue({
vuetify,
render: (h) => h(App),
}).$mount("#app");
