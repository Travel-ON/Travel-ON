// import router from "@/router";
import axios from "axios";
import kakao from "@/api/kakao_api";

export const Locations = {
  /* eslint-disable */
  state: () => ({
    dong: "", // 동
    sigungu: "", // 구군
    sido: "", // 시도
    dongCode: "",
    latitude: 37.5666805, // 위도 (Default: 서울 시청)
    longitude: 126.9784147, // 경도 (Default: 서울 시청)
  }),
  getters: {
    isLocation: (state) => !!state.dong, // 위치 인증 여부
    dong: (state) => state.dong,
    sigungu: (state) => state.sigungu,
    sido: (state) => state.sido,
    latitude: (state) => state.latitude,
    longitude: (state) => state.longitude,
  },
  mutations: {
    SET_DONG: (state, dong) => (state.dong = dong),
    SET_SIGUNGU: (state, sigungu) => (state.sigungu = sigungu),
    SET_SIDO: (state, sido) => (state.sido = sido),
    SET_LATITUDE: (state, latitude) => (state.latitude = latitude),
    SET_LONGITUDE: (state, longitude) => (state.longitude = longitude),
  },
  actions: {
    getLocation({ commit, getters }) {
      let latitude = 37.5666805
      let longitude = 126.9784147
      navigator.geolocation.getCurrentPosition(function(pos) {
        console.log("geolocation 실행")
        latitude = pos.coords.latitude;
        console.log(latitude)
        longitude = pos.coords.longitude;
        console.log(longitude)
        commit("SET_LATITUDE", latitude); // 위도
        commit("SET_LONGITUDE", longitude); // 경도
        axios({
          url: kakao.region.locInform(),
          method: "get",
          headers: {Authorization: "KakaoAK a7cedeb35de4c99731ff3ee0bc0ade21"},
          params: {
            x: longitude,
            y: latitude,
          }
        }).then((res) => {
          console.log(res)
          const sido = res.data.documents[1].region_1depth_name;
          const sigungu = res.data.documents[1].region_2depth_name;
          const dong = res.data.documents[1].region_3depth_name;
          commit("SET_SIDO", sido);
          commit("SET_SIGUNGU", sigungu);
          commit("SET_DONG", dong);
        })
        .catch(err => {
          console.error(err)
        });
      });
    },
  },
};
