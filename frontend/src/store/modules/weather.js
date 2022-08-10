// import axios from "axios";
import weather from "@/api/wheather_api";

import axios from "axios";

export const Weather = {
  /* eslint-disable */
  state: () => ({
    currentTemp: 20, // 기온
    currentIcon: "", // 날씨 아이콘
    // foreWeather: [], // 시각대별 날씨 정보
  }),
  getters: {
    temp: (state) => Math.floor(state.temp),
    icon: (state) => state.icon,
    // foreWeather: (state) => state.foreWeather,
  },
  mutations: {
    SET_TEMP: (state, temp) => (state.temp = temp),
    SET_ICON: (state, icon) => (state.icon = icon),
    // SET_FOREWEATHER: (state, foreWeather) => (state.foreWeather = foreWeather),
  },
  actions: {
    getWeather({ commit, getters }) {
      let latitude = getters.latitude;
      let longitude = getters.longitude;
      axios({
        url: weather.weather.currentWeather(),
        method: "get",
        params: {
          lat: latitude,
          lon: longitude,
          appid: "d526819885798780fb4d678251213582",
          lang: "kr",
          units: "metric",
        }
      }).then((res) => {
        const temp = res.data.main.temp;
        const icon = res.data.weather[0].icon;
        commit("SET_TEMP", temp);
        commit("SET_ICON", icon);
      })
      .catch(err => {
        console.error(err)
      });
    },
  },
}