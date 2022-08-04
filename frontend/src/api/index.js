// import axios from "axios";

// function registerUser(userData) {
//   const url = "http://localhost:8081/signup";
//   return axios.post(url, userData);
// }

// 함수 export
// export { registerUser };

// axios 객체 만들기
import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: `http://i7b301.p.ssafy.io:8080/api`,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export { createApi };
