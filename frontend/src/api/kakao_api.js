const HOST = "https://dapi.kakao.com/";

/* eslint-disable */
export default {
  region: {
    locInform: () => HOST + "v2/local/geo/coord2regioncode.json",
    imageSearch: () => HOST + "/v2/search/image",
  },
}
