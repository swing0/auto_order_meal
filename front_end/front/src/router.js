import Vue from 'vue'
import Router from 'vue-router'
import Login from './components/Login.vue'
import Home from './components/Home.vue'
import Register from './components/Register.vue'
import Welcome from './components/Welcome.vue'
import Shangjia from './components/Shangjia.vue'
import Order from './components/Order.vue'
import Submit from './components/Submit'
import Submit_order from './components/Submit_order'

Vue.use(Router)
export default new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { 
      path: '/home', 
      component: Home,
      redirect:'/welcome',
      children:[
        { path: '/welcome', component: Welcome },
        { path: '/shangjia', component: Shangjia },
        { path: '/order', component: Order },
        { path: '/submit', component: Submit }
       
    ]},
    { path: '/Register', component: Register },
    { path: '/Submit_order', component: Submit_order },
    
  ]
})

// const router = new Router({
//   routes: [
//     { path: '/', redirect: '/login' },
//     { path: '/login', component: Login },
//     { path: '/home', component: Home }
//   ]
// })

// // 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数，表示放行
//   //     next()  放行    next('/login')  强制跳转

//   if (to.path === '/login') return next()
//   // 获取token
//   const tokenStr = window.sessionStorage.getItem('token')
//   if (!tokenStr) return next('/login')
//   next()
// })
