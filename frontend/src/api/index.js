// axios 객체 만들기
import axios from "axios";

function createApi() {
  const token =
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0MjEyMTIiLCJleHAiOjE2NjAxOTYxNDUsImlhdCI6MTY2MDE5MjU0NX0.jLesttJ3jywli24TLx4wdu77FChJgaaMhOLsqen1dU4Kv36I_Imaqu7D8h_IU0fCye4YbOiBVirMDWpNCLvtMA";
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
