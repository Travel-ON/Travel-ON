<template>
  <div>
    <v-container>
      <v-expansion-panels v-model="panel" multiple>
        <v-expansion-panel
          v-for="item in faq"
          :key="item.faqId"
          :title="`Q. ${item.question}`"
          :text="`A. ${item.answer}`"
        >
        </v-expansion-panel>
      </v-expansion-panels>
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
      <v-container class="">
        <v-form>
          <v-text-field
            v-model="keyword"
            placeholder="검색어를 입력하세요"
            @keydown.enter.prevent="searchPage()"
          ></v-text-field>
        </v-form>
        <v-btn color="indigo" @click="searchPage()">검색</v-btn>
      </v-container>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState("Notices", ["faq", "totalPage"]),
  },
  watch: {
    page(num) {
      this.handlePage(num);
    },
  },
  data() {
    return { panel: [], page: 1, isStatusOn: false, keyword: "" };
  },
  mounted() {
    this.$store.dispatch("Notices/getFAQ");
  },
  methods: {
    handlePage() {
      this.$store.dispatch("Notices/getFAQ", this.page - 1);
    },
    toggleOn() {
      this.isStatusOn = !this.isStatusOn;
    },
    searchPage() {
      this.$store.dispatch("Notices/getSearchFAQ", this.keyword);
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
