const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  transpileDependencies: true,
  
  devServer: {
        proxy: {
		'/': {
			target: 'http://i7b301.p.ssafy.io:8080/api',
			changeOrigin:true
		}
	} 
  },

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
});
