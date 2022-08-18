import { createApp } from "vue";
import AOS from "aos";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";

// importing AOS css style globally
import "aos/dist/aos.css";

export const app = createApp(App);
// eslint-disable-next-line
app.AOS = new AOS.init({
  duration: 1200,
  mirror: false,
});

loadFonts();

app.use(AOS).use(router).use(store).use(vuetify).mount("#app");
