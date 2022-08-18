<template>
  <div>
    <!-- <div id="title">Member</div> -->
    <div style="display: flex">
      <div style="width: 20%">
        <v-card class="mx-auto pa-2" max-width="300">
          <v-list>
            <v-list-subheader>마이페이지</v-list-subheader>
            <v-list-item v-for="(item, i) in menu" :key="i" :value="item" active-color="primary" rounded="xl">
              <v-list-item-title @click="clickPage(item)" v-text="item.title"></v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </div>
      <div style="width: 80%">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
import spring from "@/api/spring_boot";
import Swal from "sweetalert2";

export default {
  name: "MypageView",
  data: () => ({
    menu: [
      { title: "칭호 설정", name: "MemberSetTitle" },
      { title: "회원정보 수정", name: "MemberModify" },
      { title: "비밀번호 재설정", name: "MemberModifyPwd" },
      { title: "회원 탈퇴", name: "MemberDelete" },
    ],
  }),
  computed: {
    ...mapGetters({ token: "token", isLoggedIn: "isLoggedIn" }),
  },
  created() {
    if (!this.isLoggedIn) {
      this.$router.push({
        name: "home",
      });
    }
    this.$router.push({
      name: "MemberSetTitle",
    });
  },
  methods: {
    ...mapActions(["delete"]),
    async clickPage(item) {
      if (item.name === "MemberDelete") {
        const { value: password } = await Swal.fire({
          title: "🥺 탈퇴하실건가요? 🥺",
          input: "password",
          inputLabel: "Password",
          showCancelButton: true,
          confirmButtonText: "확인",
          cancelButtonText: "취소",
          html: "탈퇴 시 Travel-ON의 다양한 서비스를 이용하실 수 없습니다.<br>탈퇴하려면 비밀번호를 입력해주세요.",
          inputAttributes: {
            maxlength: 17,
            autocapitalize: "off",
            autocorrect: "off",
          },
        }).then((result) => {
          if (result.isConfirmed) {
            axios({
              url: spring.accounts.pwdCheck(),
              method: "post",
              headers: { Authorization: `Bearer ${this.token}` },
              data: { password },
            })
              .then(() => {
                this.delete();
              })
              .catch((err) => {
                console.error(err);
                Swal.fire({
                  title: "인증실패",
                  icon: "error",
                  confirmButtonText: "확인",
                });
                this.$router.push({
                  name: "home",
                });
              });
          }
        });
      } else {
        this.$router.push({ name: item.name });
      }
    },
  },
};
</script>

<style></style>
