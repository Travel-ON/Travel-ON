<template>
  <v-container style="margin: 0px; padding: 0px">
    <v-app-bar height="80" ref="nav">
      <main-nav />
    </v-app-bar>
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
            <div class="title">
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
    <div class="page">
      <div data-aos="fade-right" style="flex: 1; display: flex; flex-direction: column; background-color: #6499ff">
        <div style="flex: 2" />
        <div style="flex: 2">
          <div class="title">
            <div style="color: #ffffff">여행지에서 새로운 사람들과</div>
            <div style="color: #ffffff">만나고 대화하세요.</div>
          </div>
        </div>
        <div style="flex: 1" />
        <div style="flex: 2; display: flex; flex-direction: column">
          <div style="flex: 2" />
          <div class="comment" style="flex: 1">지역 내 이미 만들어진 방에 참여할 수 있습니다!</div>
          <div style="flex: 3">
            <v-img src="@/assets/button/btn_roomMatching.png" alt="travel on" />
          </div>
          <div style="flex: 2" />
        </div>
        <div style="flex: 1" />
      </div>
      <div data-aos="fade-left" style="flex: 1; display: flex; flex-direction: column">
        <div style="flex: 2" />
        <div style="flex: 2">
          <v-img src="@/assets/screen/videochat.png" alt="travel on" />
        </div>
        <div style="flex: 1" />
        <div style="flex: 2; display: flex; flex-direction: column">
          <div style="flex: 2" />
          <div class="comment" style="flex: 1">같은 지역 범위 내 사용자들과의 방을 생성해보세요!</div>
          <div style="flex: 3">
            <v-img src="@/assets/button/btn_roomCreate.png" alt="travel on" />
          </div>
          <div style="flex: 2" />
        </div>
        <div style="flex: 1" />
      </div>
    </div>
    <!-- page 3 -->
    <div class="page" style="background-color: #e7ecff; flex-direction: column">
      <div style="flex: 3" />
      <div style="flex: 1" />
      <div style="flex: 6; display: flex">
        <div style="flex: 4">
          <v-img src="@/assets/screen/videochat.png" alt="travel on" />
        </div>
        <div style="flex: 1" />
        <div style="flex: 4; display: flex; flex-direction: column">
          <div style="flex: 3">
            <div class="title">
              <div style="text-align: left; color: #4d5ef4">여행플래너</div>
            </div>
            <div class="comment" style="text-align: left">
              같은 장소를 방문했다면 더 많은 이야기를 나눌 수 있을거에요.<br />
              서로 여행장소에 대해 이야기해보면서 정보를 공유해보세요!
            </div>
          </div>
          <div style="flex: 1" />
          <div style="flex: 3">
            <v-img src="@/assets/button/btn_planner.png" alt="travel on" />
          </div>
        </div>
      </div>
      <div style="flex: 1" />
      <div style="flex: 3" />
    </div>
    <!-- page 4 -->
    <div class="page" style="background-color: #8b97ff; flex-direction: column">
      <div style="flex: 3" />
      <div style="flex: 1" />
      <div style="flex: 6; display: flex">
        <div style="flex: 4">
          <v-img src="@/assets/screen/videochat.png" alt="travel on" />
        </div>
        <div style="flex: 1" />
        <div style="flex: 4; display: flex; flex-direction: column">
          <div style="flex: 3">
            <div class="title">
              <div style="text-align: left; color: #4d5ef4">여행플래너</div>
            </div>
            <div class="comment" style="text-align: left">
              같은 장소를 방문했다면 더 많은 이야기를 나눌 수 있을거에요.<br />
              서로 여행장소에 대해 이야기해보면서 정보를 공유해보세요!
            </div>
          </div>
          <div style="flex: 1" />
          <div style="flex: 3">
            <v-img src="@/assets/button/btn_planner.png" alt="travel on" />
          </div>
        </div>
      </div>
      <div style="flex: 1" />
      <div style="flex: 3" />
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
          <div style="flex: 3; background-color: #ffffff; border-radius: 15px; display: flex; flex-direction: column">
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
          <div style="flex: 3; background-color: #ffffff; border-radius: 15px; display: flex; flex-direction: column">
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
        <!-- <div style="flex: 4">
          <v-img src="@/assets/screen/videochat.png" alt="travel on" />
        </div>
        <div style="flex: 1" />
        <div style="flex: 4; display: flex; flex-direction: column">
          <div style="flex: 3">
            <div class="title">
              <div style="text-align: left; color: #4d5ef4">여행플래너</div>
            </div>
            <div class="comment" style="text-align: left">
              같은 장소를 방문했다면 더 많은 이야기를 나눌 수 있을거에요.<br />
              서로 여행장소에 대해 이야기해보면서 정보를 공유해보세요!
            </div>
          </div>
          <div style="flex: 1" />
          <div style="flex: 3">
            <v-img src="@/assets/button/btn_planner.png" alt="travel on" />
          </div>
        </div> -->
      </div>
      <div style="flex: 1" />
    </div>
  </v-container>
</template>

<script>
import { defineComponent, onMounted } from "vue";
import { mapActions } from "vuex";
import AOS from "aos";
import MainNav from "../components/common/MainNav.vue";

onMounted(() => {
  AOS.init();
});

export default defineComponent({
  name: "HomeTest",
  components: { MainNav },
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
    scrollEvents() {
      // const headerState = this.headerState;
      this.headerScrollEvent();
      // 특정페이지에는 이 부분의 주석을 해제해 준다.
      // this.navScrollEvent();
      // this.test();
    },
    headerScrollEvent() {
      // 현재 스크롤 탑
      // const beforeScrollTop = this.currentScrollValue;
      // 스크롤 후의 탑
      // const afterScrollValue = document.documentElement.scrollTop;
      // page의 헤더
      // const pageHeader = document.querySelector(".row1");

      // 추후에 nav가 생기더라도 수정해줄 필요가 없다.
      // if (this.headerState) {
      //   if (afterScrollValue > beforeScrollTop) {
      //     this.headerState = false;
      //   }
      //   this.currentScrollValue = beforeScrollTop;
      // } else if (afterScrollValue > beforeScrollTop) {
      //   this.headerState = true;
      // }
      // this.currentScrollValue = afterScrollValue;

      if (this.checkVisible() && !this.headerState) {
        alert("엘리먼트 보임 !!");
        this.headerState = true;
      }
    },
    checkVisible() {
      // return true;
      // console.log("viewportHeight");
      // console.log(this.window.height);
      // console.log("scrolltop");
      // console.log(window.scrollY);
      // console.log("y");
      // console.log(this.$refs.second.scrollTop);
      // console.log("elementHeight");
      // console.log(this.$refs.second.offsetHeight);
      // return true;
      // viewportHeight = $(window).height(), // Viewport Height
      //   scrolltop = $(window).scrollTop(), // Scroll Top
      //   y = $(elm).offset().top,
      //   elementHeight = $(elm).height();
      // eval = eval || "object visible";
      // var viewportHeight = $(window).height(), // Viewport Height
      //   scrolltop = $(window).scrollTop(), // Scroll Top
      //   y = $(elm).offset().top,
      //   elementHeight = $(elm).height();
      // if (eval == "object visible") return y < viewportHeight + scrolltop && y > scrolltop - elementHeight;
      // if (eval == "above") return y < viewportHeight + scrolltop;
    },
    navScrollEvent() {
      // 현재 스크롤 탑
      const beforeScrollTop = this.currentScrollValue;
      // 스크롤 후의 탑
      const afterScrollValue = document.documentElement.scrollTop;
      // page의 nav
      const pageNav = document.querySelector(".wrap_company_nav_container");

      if (afterScrollValue > beforeScrollTop) {
        pageNav.classList.add("nav_none");
        this.currentScrollValue = afterScrollValue;
      } else {
        pageNav.classList.remove("nav_none");
        this.currentScrollValue = afterScrollValue;
      }
    },
  },
  mounted() {
    this.fetchCurrentUser();
    document.addEventListener("scroll", this.scrollEvents);
    // window.scroll({ top: this.$refs.second.offsetTop, behavior: "smooth" });
    window.scroll({ top: 0, behavior: "smooth" });
    // window.scrollTo(this.$refs.second.offsetTop, 0);
    // this.page1Height = this.window.height - this.$refs.nav.offsetHeight;
    // this.$refs.second.height = this.page1Height;
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
.title {
  font-size: 55px;
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
  margin-left: -10px;
  text-align: center;
  animation: arrow_down 1.5s infinite;
}
.scroll-arrow {
  width: 24px;
  height: 24px;
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
  margin-top: 2px;
  animation-delay: 0.15s;
}
.scroll-arrow:nth-child(3) {
  margin-top: 4px;
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
