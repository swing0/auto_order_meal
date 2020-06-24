import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
// 导入字体图标
import './assets/fonts/iconfont.css'
// 导入全局样式表
import './assets/css/global.css'
import axios from 'axios'
// 配置请求的跟路径
// axios.defaults.baseURL = ''
Vue.prototype.$axios = axios
 Vue.config.productionTip = true

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
