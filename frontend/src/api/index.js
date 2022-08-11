// axios 객체 만들기
import axios from "axios";

function createApi() {
  // const token =
  //   "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0MjEyMTIiLCJleHAiOjE2NjAyMjMyMjksImlhdCI6MTY2MDIxOTYyOX0.XgqjQdUA2FZULZsVCcBhybOr_n_kFByMEH2t2dPupuVwCetjuz8faVLgRHpD7_oZoXmcc-89oyQGOcbvpLUJyg";
  return axios.create({
    baseURL: `http://localhost:3000/api`,
    headers: {
      "Content-Type": "application/json",
      // Authorization: `Bearer ${accessToken}`,
      // Authorization: `Bearer ${token}`,
    },
  });
}

export { createApi };
