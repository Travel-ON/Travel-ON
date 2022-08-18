<!-- eslint-disable no-alert -->
<template>
  <div class="bg mb-5" style="display: flex">
    <div style="flex: 1" />
    <div style="flex: 3">
      <div class="mt-10" style="background-color: #ffffff; border-radius: 15px; box-shadow: 5px 5px 5px 5px gray">
        <div class="mb-5" style="font-size: x-large; padding-top: 20px">Travel-ON 회원가입</div>
        <v-form ref="form" lazy-validation v-model="valid">
          <div style="display: flex">
            <div style="display: flex; flex-direction: column; flex: 1">
              <div style="display: flex">
                <div style="flex: 1" />
                <div style="flex: 9; display: flex">
                  <div style="flex: 7">
                    <v-text-field
                      label="아이디"
                      v-model="credentials.id"
                      variant="solo"
                      bg-color="#F0EDED"
                      required
                      :rules="nameRules"
                    ></v-text-field>
                  </div>
                  <div style="flex: 3">
                    <v-btn
                      @click="idCheck(credentials.id)"
                      :disabled="idDisable"
                      :prepend-icon="idChecked ? 'mdi-check-circle' : 'mdi-close-circle'"
                      :color="idChecked ? '#c9deff' : 'red-lighten-4'"
                      style="margin-top: 10px; left: 0"
                      >중복 검사</v-btn
                    >
                    <div v-if="!idChecked" style="margin-top: 12px; font-size: x-small; color: #b00020">
                      중복 검사 필수!
                    </div>
                  </div>
                </div>
              </div>
              <div style="display: flex">
                <div style="flex: 1" />
                <div style="flex: 9; display: flex">
                  <div style="flex: 7">
                    <v-text-field
                      label="닉네임"
                      v-model="credentials.nickname"
                      variant="solo"
                      bg-color="#F0EDED"
                      required
                      :rules="nicknameRules"
                    ></v-text-field>
                  </div>
                  <div style="flex: 3">
                    <v-btn
                      @click="nickCheck(credentials.nickname)"
                      :disabled="nickDisable"
                      :prepend-icon="nickChecked ? 'mdi-check-circle' : 'mdi-close-circle'"
                      :color="nickChecked ? '#c9deff' : 'red-lighten-4'"
                      style="margin-top: 10px; left: 0"
                      >중복 검사</v-btn
                    >
                    <div v-if="!nickChecked" style="margin-top: 12px; font-size: x-small; color: #b00020">
                      중복 검사 필수!
                    </div>
                  </div>
                </div>
              </div>
              <div style="display: flex">
                <div style="flex: 1" />
                <div style="flex: 9; display: flex">
                  <v-text-field
                    label="e-mail"
                    v-model="credentials.email"
                    variant="solo"
                    bg-color="#F0EDED"
                    required
                    :rules="emailRules"
                    @click:append="passwordConfirmShow = !passwordConfirmShow"
                  ></v-text-field>
                </div>
              </div>
            </div>
            <div style="flex: 1">
              <div style="display: flex">
                <div style="flex: 1" />
                <div id="inputPassword" style="flex: 8">
                  <v-text-field
                    label="비밀번호"
                    bg-color="#F0EDED"
                    variant="solo"
                    v-model="credentials.password"
                    :rules="passwordRules"
                    required
                    :type="passwordShow ? 'text' : 'password'"
                    :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="passwordShow = !passwordShow"
                    style="font-family: Georgia; font-weight: bold"
                  ></v-text-field>
                </div>
                <div style="flex: 1" />
              </div>
              <div style="display: flex">
                <div style="flex: 1" />
                <div id="inputPassword" style="flex: 8">
                  <v-text-field
                    label="비밀번호 확인"
                    bg-color="#F0EDED"
                    variant="solo"
                    v-model="credentials.passwordConfirm"
                    :rules="passwordConfirmRules"
                    required
                    :type="passwordConfirmShow ? 'text' : 'password'"
                    :append-icon="passwordConfirmShow ? 'mdi-eye' : 'mdi-eye-off'"
                    @click:append="passwordConfirmShow = !passwordConfirmShow"
                    style="font-family: Georgia; font-weight: bold"
                  ></v-text-field>
                </div>
                <div style="flex: 1" />
              </div>

              <div style="display: flex">
                <div style="flex: 1" />
                <div style="flex: 8">
                  <v-select
                    v-model="credentials.sidoCode"
                    :items="sido"
                    item-title="name"
                    item-value="code"
                    label="거주 지역"
                    persistent-hint
                    single-line
                    bg-color="#F0EDED"
                  ></v-select>
                </div>
                <div style="flex: 1" />
              </div>
            </div>
          </div>

          <v-card
            class="overflow-y-auto"
            max-height="200"
            style="background: #eeeeee; margin-left: 40px; margin-right: 40px"
          >
            <v-card-text style="text-align: left; font-size: small">
              <div>
                제 1 조 (목적)<br />
                이 약관은 TRAVEL-ON 웹사이트 (이하 “회사”)가 제공하는 위치기반서비스와 관련하여 회사와
                개인위치정보주체와의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.<br />
                <br />
                제 2 조 (약관 외 준칙)<br />
                이 약관에 명시되지 않은 사항은 위치정보의 보호 및 이용 등에 관한 법률, 개인정보보호법, 정보통신망
                이용촉진 및 정보보호 등에 관한 법률, 전기통신기본법, 전기통신사업법 등 관계법령과 회사의 이용약관 및
                개인정보처리방침, 회사가 별도로 정한 지침 등에 의합니다.<br />
                <br />
                제 3 조 (개인위치정보주체의 권리)<br />
                ①개인위치정보주체는 개인위치정보 수집 범위 및 이용약관의 내용 중 일부 또는 개인위치정보의 이용ㆍ제공
                목적, 제공받는 자의 범위 및 위치기반서비스의 일부에 대하여 동의를 유보할 수 있습니다.<br />
                ②개인위치정보주체는 개인위치정보의 수집ㆍ이용ㆍ제공에 대한 동의의 전부 또는 일부를 철회할 수
                있습니다.<br />
                ③개인위치정보주체는 언제든지 개인위치정보의 수집ㆍ이용ㆍ제공의 일시적인 중지를 요구할 수 있습니다.<br />
                이 경우 회사는 요구를 거절하지 아니하며, 이를 위한 기술적 수단을 갖추고 있습니다.<br />
                ④개인위치정보주체는 회사에 대하여 아래 자료의 열람 또는 고지를 요구할 수 있고, 당해 자료에 오류가 있는
                경우에는 그 정정을 요구할 수 있습니다.<br />
                이 경우 회사는 정당한 이유 없이 요구를 거절하지 아니합니다.<br />
                1.개인위치정보주체에 대한 위치정보 수집ㆍ이용ㆍ제공사실 확인자료<br />
                2.개인위치정보주체의 개인위치정보가 위치정보의 보호 및 이용 등에 관한 법률 또는 다른 법령의 규정에
                의하여 제3자에게 제공된 이유 및 내용<br />
                ⑤회사는 개인위치정보주체가 동의의 전부 또는 일부를 철회한 경우에는 지체 없이 수집된 개인위치정보 및
                위치정보 수집ㆍ이용ㆍ제공사실 확인자료를 파기합니다.<br />
                단, 동의의 일부를 철회하는 경우에는 철회하는 부분의 개인위치정보 및 위치정보 수집ㆍ이용ㆍ제공사실
                확인자료에 한합니다.<br />
                ⑥개인위치정보주체는 제1항 내지 제4항의 권리행사를 위하여 이 약관 제13조의 연락처를 이용하여 회사에
                요구할 수 있습니다.<br />
                <br />
                제 4 조 (위치정보 이용ㆍ제공사실 확인자료 보유근거 및 보유기간)<br />
                회사는 위치정보의 보호 및 이용 등에 관한 법률 제16조 제2항에 근거하여 개인위치정보주체에 대한 위치정보
                수집ㆍ이용ㆍ제공사실 확인자료를 위치정보시스템에 자동으로 기록하며, 6개월 이상 보관합니다.<br />
                <br />
                제 5 조 (서비스의 변경 및 중지)<br />
                ①회사는 위치기반서비스사업자의 정책변경 등과 같이 회사의 제반 사정 또는 법률상의 장애 등으로 서비스를
                유지할 수 없는 경우, 서비스의 전부 또는 일부를 제한, 변경하거나 중지할 수 있습니다.<br />
                ②제1항에 의한 서비스 중단의 경우에는 회사는 사전에 인터넷 등에 공지하거나 개인위치정보주체에게
                통지합니다.<br />
                <br />
                제 8 조 (개인위치정보 제3자 제공 시 즉시 통보)<br />
                ①회사는 개인위치정보주체의 동의 없이 당해 개인위치정보주체의 개인위치정보를 제3자에게 제공하지 아니하며,
                제3자 제공 서비스를 제공하는 경우에는 제공받는 자 및 제공목적을 사전에 개인위치정보주체에게 고지하고
                동의를 받습니다.<br />
                ②회사는 개인위치정보를 개인위치정보주체가 지정하는 제3자에게 제공하는 경우에는 개인위치정보를 수집한
                당해 통신단말장치로 매회 개인위치정보주체에게 제공받는 자, 제공일시 및 제공목적을 즉시 통보합니다.<br />
                ③다만, 아래에 해당하는 경우에는 개인위치정보주체가 미리 특정하여 지정한 통신단말장치 또는 전자우편주소
                등으로 통보합니다.<br />
                1.개인위치정보를 수집한 당해 통신단말장치가 문자, 음성 또는 영상의 수신기능을 갖추지 아니한 경우<br />
                2.개인위치정보주체가 개인위치정보를 수집한 당해 통신단말장치 외의 통신단말장치 또는 전자우편주소 등으로
                통보할 것을 미리 요청한 경우<br />
                <br />
                제 6 조 (개인위치정보의 보유목적 및 이용기간)<br />
                ①회사는 위치기반서비스를 제공하기 위해 필요한 최소한의 기간 동안 개인위치정보를 보유 및 이용합니다.<br />
                ②회사는 대부분의 위치기반서비스에서 개인위치정보를 일회성 또는 임시로 이용 후 지체없이 파기합니다.<br />
                <br />
                제 7 조 (손해배상)<br />
                개인위치정보주체는 회사의 위치정보의 보호 및 이용 등에 관한 법률 제15조 내지 26조의 규정을 위반한 행위로
                손해를 입은 경우에 회사에 대하여 손해배상을 청구할 수 있습니다. 이 경우 회사는 고의 또는 과실이 없음을
                입증하지 아니하면 책임을 면할 수 없습니다.<br />
                <br />
                제 8 조 (분쟁의 조정)<br />
                ①회사는 위치정보와 관련된 분쟁에 대하여 개인위치정보주체와 협의가 이루어지지 아니하거나 협의를 할 수
                없는 경우에는 방송통신위원회에 재정을 신청할 수 있습니다.<br />
                ②회사 또는 개인위치정보주체는 위치정보와 관련된 분쟁에 대해 당사자간 협의가 이루어지지 아니하거나 협의를
                할 수 없는 경우에는 개인정보보호법에 따라 개인정보분쟁조정위원회에 조정을 신청할 수 있습니다.<br />
                <br />
                제 9 조 (사업자 정보 및 위치정보 관리책임자)<br />
                ①회사의 상호, 주소 및 연락처는 다음과 같습니다.<br />
                상호: TRAVEL-ON<br />
                <br />
                주소: 대전특별 광역시 서구 월평3동 하나로아파트 <br />
                <br />
                전화번호: 010-2812-0890<br />
                <br />
                ②회사는 다음과 같이 위치정보 관리책임자를 지정하여 이용자들이 서비스 이용과정에서 발생한 민원사항 처리를
                비롯하여 개인위치정보주체의 권리 보호를 위해 힘쓰고 있습니다.<br />
                위치정보 관리책임자 : 김태훈 BE (개인정보 보호책임자 겸직)<br />
                메일 : t21212@naver.com<br />
                <br />
              </div>
            </v-card-text>
          </v-card>
          <v-checkbox v-model="agreeCheck" color="deep-purple">
            <template v-slot:label>
              약관을 읽고&nbsp;
              <a href="#" style="text-decoration: underline"> 개인정보 이용 </a>
              &nbsp;동의를 체크 해주세요.(필수)
            </template>
          </v-checkbox>
        </v-form>
        <div class="mb-5" style="display: flex">
          <div style="flex: 1" />
          <div style="flex: 8">
            <v-btn
              block
              :disabled="!valid || !agreeCheck || !idChecked || idDisable || !nickChecked || nickDisable"
              @click="regist(credentials)"
              size="large"
              style="margin-bottom: 30px; background-color: #c9deff"
              >회원가입</v-btn
            >
          </div>
          <div style="flex: 1" />
        </div>
      </div>
    </div>
    <div style="flex: 1" />
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import spring from "@/api/spring_boot";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      credentials: {
        id: "",
        password: "",
        passwordConfirm: "",
        email: "",
        nickname: "",
        sidoCode: "",
      },
      expand: false,
      agreeCheck: false,
      sido: [
        { code: "4200000000", name: "강원" },
        { code: "4100000000", name: "경기" },
        { code: "4800000000", name: "경남" },
        { code: "4700000000", name: "경북" },
        { code: "2900000000", name: "광주" },
        { code: "2700000000", name: "대구" },
        { code: "3000000000", name: "대전" },
        { code: "2600000000", name: "부산" },
        { code: "1100000000", name: "서울" },
        { code: "3611000000", name: "세종특별자치시" },
        { code: "3100000000", name: "울산" },
        { code: "2800000000", name: "인천" },
        { code: "4600000000", name: "전남" },
        { code: "4500000000", name: "전북" },
        { code: "5000000000", name: "제주특별자치도" },
        { code: "4400000000", name: "충남" },
        { code: "4300000000", name: "충북" },
      ],
      valid: false,
      passwordShow: false,
      passwordConfirmShow: false,
      idDisable: true,
      nickDisable: true,
      idChecked: "",
      nickChecked: "",
      nameRules: [
        (v) => !!v || "아이디를 입력해주세요.",
        (v) => /^[a-zA-Z]+[0-9]+[a-z0-9A-Z]*$/.test(v) || "아이디는 영문자 + 숫자로만 생성가능합니다.",
        (v) => (v.length >= 6 && v.length <= 16) || "아이디 길이는 6자이상 16자이하로 생성해주세요.",
      ],
      passwordRules: [
        (v) => !!v || "비밀번호를 입력해주세요.",
        (v) =>
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/.test(v) ||
          "비밀번호는 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하셔야 합니다.",
      ],
      passwordConfirmRules: [
        (v) => !!v || "비밀번호를 다시 입력해주세요.",
        (v) => v === this.credentials.password || "비밀번호가 일치하지 않습니다.",
      ],
      nicknameRules: [
        (v) => !!v || "닉네임을 입력해주세요.",
        (v) => v.length <= 10 || "닉네임은 10자 이내로 생성해주세요.",
      ],
      emailRules: [
        (v) => !!v || "E-mail을 입력해주세요.",
        (v) =>
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
            v,
          ) || "E-mail형식을 확인해주세요.",
      ],
    };
  },
  watch: {
    /* eslint-disable */
    "credentials.id": function () {
      this.idChecked = "";
      this.idDisable = false;
      this.nameRules.forEach((element) => {
        if (typeof element(this.credentials.id) !== "boolean") {
          this.idDisable = true;
        }
      });
    },
    // eslint-disable-next-line func-names
    "credentials.nickname": function () {
      this.nicknameChecked = "";
      this.nickDisable = false;
      this.nicknameRules.forEach((element) => {
        if (typeof element(this.credentials.nickname) !== "boolean") {
          this.nickDisable = true;
        }
      });
    },
  },
  computed: {},
  mounted() {},
  methods: {
    ...mapActions(["regist"]),
    idCheck(id) {
      /*
      id중복검사
        성공시
          idChecked 상태값 True
        실패시
          -
        idChecked True일때만 가입 진행
      */
      if (id !== "") {
        axios({
          url: spring.accounts.idCheck(),
          method: "post",
          params: { id },
        })
          .then(() => {
            this.idChecked = id;
            Swal.fire({
              icon: "success",
              title: "아이디 중복 검사 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch((err) => {
            Swal.fire({
              icon: "error",
              title: "이미 있는 아이디 입니다!",
              showConfirmButton: false,
              timer: 1000,
            });
            console.log(err);
          });
      } else {
        Swal.fire({
          icon: "error",
          title: "아무것도 입력하지 않으셨습니다.",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
    nickCheck(nickname) {
      if (nickname !== "") {
        console.log(nickname);
        axios({
          url: spring.accounts.nickCheck(),
          method: "post",
          params: { nickname },
        })
          .then((res) => {
            console.log(res);
            this.nickChecked = nickname;
            Swal.fire({
              icon: "success",
              title: "닉네임 중복 검사 완료!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch((err) => {
            Swal.fire({
              icon: "error",
              title: "이미 있는 닉네임 입니다!",
              showConfirmButton: false,
              timer: 1000,
            });
            console.log(err);
          });
      } else {
        Swal.fire({
          icon: "error",
          title: "아무것도 입력하지 않으셨습니다.",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
  },
};
</script>

<style scoped>
.bg {
  width: 100vw;
  height: 100%;
}
</style>
