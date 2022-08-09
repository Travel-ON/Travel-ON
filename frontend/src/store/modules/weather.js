// import axios from "axios";
// import weather from "@/api/wheather_api";

export const Weather = {
  /* eslint-disable */
  state: () => ({
    temp: 20, // 기온
    icon: "", // 날씨 아이콘
  }),
  getters: {
    temp: (state) => Math.floor(state.temp)
  }
}