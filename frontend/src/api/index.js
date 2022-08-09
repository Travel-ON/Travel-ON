// axios 객체 만들기
import axios from "axios";

function createApi() {
  const token =
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjE2NjAwMTE5MTQsImlhdCI6MTY2MDAwODMxNH0.5qIQk56uLCEnK53ii1mu0e_pqklK169mSZWiQ_X35E_Usf2t-AhEnbk_OYrl1CeINqzlFSyQnoYopaHzhoQhmA";
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
