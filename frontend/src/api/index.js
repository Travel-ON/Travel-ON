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
<<<<<<< HEAD
    baseURL: `http://localhost:8080/`,
=======
    baseURL: `http://i7b301.p.ssafy.io:3000/api`,
>>>>>>> 64b23c97df8aa894722af673e62c130e930371ae
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export { createApi };
