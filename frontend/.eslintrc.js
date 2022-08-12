module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: ["plugin:vue/vue3-essential", "@vue/airbnb", "plugin:prettier/recommended"],
  parserOptions: {
    parser: "@babel/eslint-parser",
  },
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    // 뷰티파이 관련 룰
    // "vuetify/no-deprecated-classes": "error",
    // "vuetify/no-legacy-grid": "error",
    "import/prefer-default-export": "off",
    "import/no-unresolved": "off",
  },
};
