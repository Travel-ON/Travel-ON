<template>
  <div style="display: flex; justify-content: space-between">
    <div style="flex: 3; height: 100px; margin-right: 30px">
      <v-carousel height="360px" show-arrows="hover" cycle hide-delimiters>
        <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src" cover></v-carousel-item>
      </v-carousel>
      <div style="height: 40px; background-color: #6499ff; border-radius: 0 0 16px 16px">{{ key }}</div>
    </div>
    <div style="flex: 2; height: 100px; background-color: aquamarine"></div>
  </div>
</template>

<script>
export default {
  name: "MainServices",
  data() {
    return {
      items: [
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
  border-radius: 16px 16px 0 0;
  border: 5px solid #6499ff;
}
</style>
