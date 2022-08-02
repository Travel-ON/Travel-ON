import axios from "axios";

function registerUser(userData) {
  const url = "http://localhost:8081/signup";
  return axios.post(url, userData);
}

// 함수 export
export { registerUser };
