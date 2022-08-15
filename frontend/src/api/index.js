// axios 객체 만들기
import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: `http://localhost:3000/api`,
    // baseURL: `https://i7b301.p.ssafy.io:3000/api`,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export { createApi };
