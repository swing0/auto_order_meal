module.exports = {
    devServer: {
        port: 4040, 
    publicPath: '/',
    proxy: {
        '/api': {
            target: 'http://localhost:8080', //接口域名
            changeOrigin: true,             //是否跨域
            ws: true,                       //是否代理 websockets
            secure: true,                   //是否https接口
            pathRewrite: {                  //路径重置
                '^/api': ''
            }
        }
    }
}

};