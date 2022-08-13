<!-- 관광정보 캐러젤 -->
<template>
  <div style="margin-bottom: 30px">
    <v-carousel height="360px" show-arrows="hover" cycle hide-delimiters>
      <v-carousel-item v-for="(item, i) in items_tour" :key="i" :src="item.src" cover>
        <v-sheet height="100%" color="transparent">
          <div
            style="
              position: absolute;
              bottom: 0;
              background-color: #6499ff;
              color: white;
              font-size: 24px;
              z-index: 5000;
              height: 40px;
              width: 100%;
              display: flex;
              justify-content: center;
              align-items: center;
            "
          >
            <div>
              {{ item.name }}
            </div>
          </div>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<script>
export default {
  name: "MainServices",
  data() {
    return {
      items_tour: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
          name: "1",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
          name: "2",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg",
          name: "3",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
          name: "4",
        },
      ],
    };
  },
};
</script>

<style>
.v-window {
  border-radius: 16px;
  border: 5px solid #6499ff;
}
</style>
