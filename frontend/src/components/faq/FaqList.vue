<template>
  <div>
    <v-container>
      <v-row v-for="item in faq" :key="item.faqId">
        <v-col>Q</v-col>
        <v-col>{{ item.question }}</v-col>
        <v-col v-show="isStatusOn">{{ item.answer }}</v-col>
        <v-col @click="toggleOn">V</v-col>
      </v-row>
      <div class="text-center">
        <v-container>
          <v-row justify="center">
            <v-col cols="8">
              <v-container class="max-width">
                <v-pagination v-model="page" class="my-4" :length="totalPage"></v-pagination>
              </v-container>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["faq", "totalPage"]),
  },
  watch: {
    page(num) {
      this.handlePage(num);
    },
  },
  data() {
    return { page: 1, isStatusOn: false };
  },
  mounted() {
    this.$store.dispatch("getFAQ");
  },
  methods: {
    handlePage() {
      console.log(this.page - 1);
      this.$store.dispatch("getFAQ", this.page - 1);
    },
    toggleOn() {
      this.isStatusOn = !this.isStatusOn;
    },
  },
};
</script>

<style scoped>
#title {
  text-align: center;
  color: #2c3e50;
  justify-content: center;
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
}
</style>
