<template>
  <div class="container">
    <div>
      <div style="font-weight: bold; font-size: large">방만들기</div>
      <div>직접 방을 생성해 사람들을 만나보세요!</div>
    </div>
    <div>
      <div style="font-weight: bold; font-size: large">방매칭하기</div>
      <div>이미 만들어진 방에 들어가 사람들을 만나보세요!</div>
    </div>
    <div>
      <div style="font-weight: bold; font-size: large">여행플래너 작성하기</div>
      <div>여행을 하며 방문한 장소와 가보고 싶은 장소를 기록해보세요!</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeMainButtons",
};
</script>

<style>
.container {
  display: flex;
  margin-bottom: 50px;
}
.container > div {
  line-height: 150%;
  text-align: left;
  flex: 1;
  margin: 0 10px;
  padding: 20px;
  height: 120px;
  background-color: ivory;
}
</style>
