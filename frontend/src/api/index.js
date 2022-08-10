// axios 객체 만들기
import axios from "axios";

function createApi() {
  const token =
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0MjEyMTIiLCJleHAiOjE2NjAwNjA4MDksImlhdCI6MTY2MDA1NzIwOX0.XqLAO6YLenO6oDDIVlVjvkZ2E-YRtWhEhEsYUKcPhqKNhopVI2NW39kM3lwxFPdxiQjgw3M3Yb8AxXl8eWrNZA";
  return axios.create({
    baseURL: `http://localhost:3000/api`,
    headers: {
      "Content-Type": "application/json",

      // Authorization: `Bearer ${accessToken}`,
      Authorization: `Bearer ${token}`,
    },
  });
}

export { createApi };
