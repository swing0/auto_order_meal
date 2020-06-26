module.exports = {
    devServer: {
    port: 4040, 
    publicPath: '/',
    proxy:{ 
        '/api': {  //使用"/api"来代替"http://localhost:8080/" 
          target: 'http://localhost:8080', //源地址 
          changeOrigin: true, //改变源 
          pathRewrite: { 
            '^/api': '' //路径重写 
            } 
        } 
      },
  },
};