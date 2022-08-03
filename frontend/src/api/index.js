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
    baseURL: `https://7d0c97ac-6236-4b8f-9ba8-f257edcdfe2d.mock.pstmn.io//`,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export { createApi };
