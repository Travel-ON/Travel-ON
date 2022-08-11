// axios 객체 만들기
import axios from "axios";

function createApi() {
  const token =
    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0MjEyMTIiLCJleHAiOjE2NjAxODcyMjEsImlhdCI6MTY2MDE4MzYyMX0.nxaPD-_rnB9mc73ayDEfkRKVBvZCF5-NX0eh-JIaESuV11ThwW1LES1msQUCcvcJFdsV583YtXDLPR8P2pfH4A";
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
