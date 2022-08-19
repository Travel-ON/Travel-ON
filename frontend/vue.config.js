const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    host: "0.0.0.0",
    port: 8080,
    open: true,
    allowedHosts: "all",
    client: {
      webSocketURL: "wss://i7b301.p.ssafy.io:443/ws",
    },
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
  },

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
});
