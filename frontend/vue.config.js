const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  transpileDependencies: true,
  
    devServer: {
         host: '0.0.0.0',
         port: 8080,
         open: true,
         allowedHosts:'all',
         headers: {
            'Access-Control-Allow-Origin' : '*',
         }
  },

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
});
