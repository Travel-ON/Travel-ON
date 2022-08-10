// import router from "@/router";
import axios from "axios";
import kakao from "@/api/kakao_api";

export const Locations = {
  /* eslint-disable */
  state: () => ({
    dong: "", // 동
    gugun: "", // 구군
    sido: "", // 시도
    dongCode: "", // 동코드
    latitude: 37.5666805, // 위도 (Default: 서울 시청)
    longitude: 126.9784147, // 경도 (Default: 서울 시청)
  }),
  getters: {
    isLocation: (state) => !!state.dong, // 위치 인증 여부
    dong: (state) => state.dong,
    gugun: (state) => state.gugun,
    sido: (state) => state.sido,
    dongCode: (state) => state.dongCode,
    latitude: (state) => state.latitude,
    longitude: (state) => state.longitude,
  },
  mutations: {
    SET_DONG: (state, dong) => (state.dong = dong),
    SET_GUGUN: (state, gugun) => (state.gugun = gugun),
    SET_SIDO: (state, sido) => (state.sido = sido),
    SET_DONGCODE: (state, dongCode) => (state.dongCode = dongCode),
    SET_LATITUDE: (state, latitude) => (state.latitude = latitude),
    SET_LONGITUDE: (state, longitude) => (state.longitude = longitude),
  },
  actions: {
    getLocation({ commit, dispatch, getters }, isValid) { // 지역 정보 수집, isValid: 업적 업데이트 처리 여부
      let latitude = 37.5666805
      let longitude = 126.9784147
      navigator.geolocation.getCurrentPosition(function(pos) {
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
          const sido = res.data.documents[1].region_1depth_name;
          const gugun = res.data.documents[1].region_2depth_name;
          const dong = res.data.documents[1].region_3depth_name;
          const dongCode = res.data.documents[1].code;
          commit("SET_SIDO", sido);
          commit("SET_GUGUN", gugun);
          commit("SET_DONG", dong);
          commit("SET_DONGCODE", dongCode);
          if(isValid){
            dispatch("fetchTrophy");
          };
        })
        .catch(err => {
          console.error(err)
        });
      });
    },
    removeLocation({ commit }) { // 지역정보 초기화 (ex: 로그아웃시)
      commit("SET_DONG", "");
      commit("SET_GUGUN", "");
      commit("SET_SIDO", "");
      commit("SET_DONGCODE", "");
      commit("SET_LATITUDE", 37.5666805);
      commit("SET_LONGITUDE", 126.9784147);
    }
  },
};
