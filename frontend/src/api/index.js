import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: `https://i7b301.p.ssafy.io:3000/api`,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export { createApi };
