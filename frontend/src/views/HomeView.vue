<template>
  <v-container style="margin: 0px; padding: 0px">
    <!-- page 1 -->
    <div class="page main" style="background-color: #e7f0ff; flex-direction: column">
      <div style="flex: 1" />
      <div style="flex: 6; display: flex">
        <div style="flex: 1" />
        <div style="flex: 8; display: flex; flex-direction: column">
          <div style="flex: 1" />
          <div style="flex: 2">
            <div style="position: relative">
              <div v-if="isIconStatus" class="logo" data-aos-delay="500" data-aos-easing="ease">
                <v-img src="@/assets/on.png" alt="travel on" :height="logoImgHeight" :width="logoImgWidth" />
              </div>
              <div class="logo" data-aos="flip-right" data-aos-delay="100" data-aos-easing="ease">
                <v-img src="@/assets/off2.png" alt="travel on" :height="logoImgHeight" :width="logoImgWidth" />
              </div>
            </div>
          </div>
          <div style="flex: 1" />
        </div>
        <div style="flex: 6; display: flex; flex-direction: column">
          <div style="flex: 5" />
          <div style="flex: 1">
            <div class="maintitle">
              <span style="color: #2d40a6">혼행에 만남의 </span>
              <span style="color: #fff493">빛</span>
              <span style="color: #2d40a6">을 켜다!</span>
            </div>
            <div class="comment">
              프라이빗한 시공간에서 새로운 사람을 만나는<br />여행지 위치인증기반 여행객 화상채팅 매칭 서비스
            </div>
          </div>
          <div style="flex: 4" />
        </div>
        <div style="flex: 1" />
      </div>
      <div style="flex: 1" />
      <div style="flex: 1">
        <div class="s1_arrow">
          <div class="scroll-arrow"></div>
          <div class="scroll-arrow"></div>
          <div class="scroll-arrow"></div>
        </div>
      </div>
      <div style="flex: 2" />
    </div>
    <!-- page 2 -->
    <div class="page" style="flex-direction: column">
      <div style="flex: 4; display: flex">
        <div style="flex: 1; display: flex; flex-direction: column; background-color: #6499ff">
          <div style="flex: 1" />
          <div style="flex: 2">
            <div data-aos="fade-right" class="title">
              <div style="color: #ffffff">여행지에서 새로운 사람들과</div>
              <div style="color: #ffffff">만나고 대화하세요.</div>
            </div>
          </div>
          <!-- <div style="flex: 1" /> -->
        </div>
        <div style="flex: 1; display: flex; flex-direction: column">
          <div style="flex: 1" />
          <div style="flex: 3; display: flex">
            <div style="flex: 1" />
            <div data-aos="fade-left" style="flex: 4">
              <v-img src="@/assets/screen/videochat.png" alt="travel on" />
            </div>
            <div style="flex: 1" />
          </div>
          <!-- <div style="flex: 1" /> -->
        </div>
      </div>
      <div style="flex: 2; display: flex">
        <div style="flex: 1; display: flex; flex-direction: column; background-color: #6499ff">
          <div style="flex: 1" />
          <div class="comment" style="flex: 1">지역 내 이미 만들어진 방에 참여할 수 있습니다!</div>
          <div style="flex: 2; display: flex">
            <div style="flex: 1" />
            <div style="flex: 5">
              <router-link :to="{ name: 'VideochatMatching' }">
                <v-img src="@/assets/button/btn_roomMatching.png" alt="travel on" />
              </router-link>
            </div>
            <div style="flex: 1" />
          </div>
          <div style="flex: 1" />
        </div>
        <div style="flex: 1">
          <div style="flex: 1" />
          <div class="comment" style="flex: 1">같은 지역 범위 내 사용자들과의 방을 생성해보세요!</div>

          <div style="flex: 2; display: flex">
            <div style="flex: 1" />
            <div style="flex: 5">
              <router-link :to="{ name: 'VideochatCreate' }">
                <v-img src="@/assets/button/btn_roomCreate.png" alt="travel on" />
              </router-link>
            </div>
            <div style="flex: 1" />
          </div>
          <div style="flex: 1" />
        </div>
      </div>
    </div>
    <!-- page 3 -->
    <div class="page" style="background-color: #e7ecff">
      <div style="flex: 1" />
      <div style="flex: 6; display: flex; flex-direction: column">
        <div style="flex: 1" />
        <div style="flex: 2">
          <v-img data-aos="fade-up" src="@/assets/screen/videochat.png" alt="travel on" />
        </div>
        <div style="flex: 1" />
      </div>
      <div style="flex: 1" />
      <div style="flex: 5; display: flex; flex-direction: column">
        <div style="flex: 3" />
        <div style="flex: 4">
          <div class="title">
            <div style="text-align: left; color: #4d5ef4">여행플래너</div>
          </div>
          <div class="comment" style="text-align: left">
            같은 장소를 방문했다면 더 많은 이야기를 나눌 수 있을거에요.<br />
            서로 여행장소에 대해 이야기해보면서 정보를 공유해보세요!
          </div>
        </div>
        <div style="flex: 1" />
        <div style="flex: 5; display: flex">
          <div style="flex: 1" />
          <div style="flex: 8">
            <router-link :to="{ name: 'Planner' }">
              <v-img src="@/assets/button/btn_planner.png" alt="travel on" />
            </router-link>
          </div>
          <div style="flex: 1" />
        </div>
        <div style="flex: 2" />
      </div>
      <div style="flex: 1" />
    </div>
    <!-- page 4 -->
    <div class="page" style="background-color: #8b97ff">
      <div style="flex: 1" />
      <div style="flex: 5; display: flex; flex-direction: column">
        <div style="flex: 3" />
        <div style="flex: 4">
          <div class="title">
            <div style="text-align: left; color: #ffffff">업적을 달성하고 <br />나의 칭호를 자랑해봐요!</div>
          </div>
        </div>
        <div style="flex: 1" />
        <div style="flex: 5; display: flex">
          <div style="flex: 1" />
          <div style="flex: 8">
            <v-img src="@/assets/button/btn_setAchivement.png" alt="travel on" />
          </div>
          <div style="flex: 1" />
        </div>
        <div style="flex: 2" />
      </div>
      <div style="flex: 1" />
      <div style="flex: 6; display: flex; flex-direction: column">
        <div style="flex: 1" />
        <div style="flex: 2">
          <router-link :to="{ name: 'MemberSetTitle' }">
            <v-img data-aos="fade-up" src="@/assets/achievementsMap.png" alt="travel on" />
          </router-link>
        </div>
        <div style="flex: 1" />
      </div>
      <div style="flex: 1" />
    </div>
    <!-- page 5 -->
    <div class="page" style="background-color: #97cce7; flex-direction: column">
      <div style="flex: 1" />
      <div class="title" style="flex: 2">
        <div style="color: #ffffff">화상채팅에 준비된 게임을<br />활용해보세요</div>
      </div>
      <div style="flex: 1" />
      <div style="flex: 5; display: flex">
        <div style="flex: 4" />
        <div style="flex: 3; display: flex; flex-direction: column">
          <div
            data-aos="flip-left"
            style="flex: 3; background-color: #ffffff; border-radius: 15px; display: flex; flex-direction: column"
          >
            <div style="flex: 1" />
            <div style="flex: 7">
              <v-img src="@/assets/icon_roulette.png" alt="travel on" />
            </div>
            <div style="flex: 1" />
            <div style="flex: 3; color: #50a0f0; font-size: 40px; font-weight: bold">룰렛</div>
            <div style="flex: 1" />
          </div>
          <div style="flex: 2" />
        </div>
        <div style="flex: 1" />
        <div style="flex: 3; display: flex; flex-direction: column">
          <div style="flex: 1" />
          <div style="flex: 1">
            <v-img src="@/assets/icon.png" alt="travel on" />
          </div>
        </div>
        <div style="flex: 1" />
        <div style="flex: 3; display: flex; flex-direction: column">
          <div
            data-aos="flip-right"
            style="flex: 3; background-color: #ffffff; border-radius: 15px; display: flex; flex-direction: column"
          >
            <div style="flex: 1" />
            <div style="flex: 7">
              <v-img src="@/assets/icon_liar.jpeg" alt="travel on" />
            </div>
            <div style="flex: 1" />
            <div style="flex: 3; color: #50a0f0; font-size: 40px; font-weight: bold">라이어</div>
            <div style="flex: 1" />
          </div>
          <div style="flex: 2" />
        </div>
        <div style="flex: 4" />
      </div>
      <div style="flex: 1" />
    </div>
  </v-container>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import { mapActions } from "vuex";
import AOS from "aos";

onMounted(() => {
  AOS.init();
});

export default defineComponent({
  name: "HomeView",
  components: {},
  data() {
    return {
      window: {
        width: 0,
        height: 0,
      },
      page1Height: 0,
      headerState: true,
      lastScrollTop: 0,
      isIconStatus: false,
      logoImgHeight: 300,
      logoImgWidth: 700,
    };
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
  },
  unmounted() {
    document.removeEventListener("scroll", this.scrollEvents);
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    ...mapActions(["fetchCurrentUser"]),
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
    },
  },
  mounted() {
    if (!this.isLocation && this.isLoggedIn) {
      this.getLocation(false);
    }
    this.fetchCurrentUser();
    document.addEventListener("scroll", this.scrollEvents);
    window.scroll({ top: 0, behavior: "smooth" });
    setTimeout(async () => {
      this.isIconStatus = true;
    }, 1500);
  },
  watch: {
    $route(to, form) {
      if (to.path !== form.path) {
        this.fetchCurrentUser();
      }
    },
  },
});
</script>
<style scoped>
.page {
  display: flex;
  margin: 0px;
  width: 100vw;
  height: 100vh;
}
.logo {
  position: absolute;
  left: 30px;
}
.maintitle {
  font-size: 54px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
}
.title {
  font-size: 48px;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
}
.comment {
  font-size: 20px;
  margin-top: 5px;
}
.s1_arrow {
  display: inline-block;
  position: relative;
  top: 30px;
  /* left: 50%; */
  margin-left: -12px;
  text-align: center;
  animation: arrow_down 1.5s infinite;
}
.scroll-arrow {
  width: 35px;
  height: 35px;
  opacity: 0;
  border-right: 3px solid #ffffff;
  border-bottom: 3px solid #ffffff;
  transform: rotate(45deg);
  -webkit-transform: rotate(45deg);
  -moz-transform: rotate(45deg);
  -o-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  animation: arrow-wave 1s infinite;
  animation-direction: alternate;
}
.scroll-arrow:nth-child(1) {
  animation-delay: 0s;
}
.scroll-arrow:nth-child(2) {
  top: 16px;
  animation-delay: 0.15s;
}
.scroll-arrow:nth-child(3) {
  top: 32px;
  animation-delay: 0.3s;
}
@keyframes arrow-wave {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}
@keyframes arrow_down {
  0% {
    top: 28px;
  }
  50% {
    top: 40px;
  }
  100% {
    top: 28px;
  }
}
</style>
