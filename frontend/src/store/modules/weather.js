// import axios from "axios";
// import weather from "@/api/wheather_api";

export const Weather = {
  /* eslint-disable */
  state: () => ({
    currentTemp: 20, // 기온
    currentIcon: "", // 날씨 아이콘
  }),
  getters: {
    temp: (state) => Math.floor(state.temp),
    icon: (state) => state.icon,
  },
  mutations: {
    SET_TEMP: (state, temp) => (state.temp = temp),
    SET_ICON: (state, icon) => (state.icon = icon),
  }
}