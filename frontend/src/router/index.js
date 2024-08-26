import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Login_Register from "@/views/Login_Register";
import SignUpView from "@/views/SignUpView";
import ForgetView from "@/views/ForgetView";

const routes = [
  {
    path: '/',
    redirect: '/login', // 重定向到 /login
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: Login_Register
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignUpView,
  },
  {
    path: '/forget',
    name: 'forget',
    component: ForgetView,
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/hot',
    name: 'hot',
    component: () => import('../views/HotView.vue')
  },
  {
    path: '/searchview',
    name: 'searchview',
    component: () => import('../views/SearchView.vue'),
  },
  {
    path: '/personview',
    name: 'personview',
    component: () => import('../views/PersonView.vue')
  },
  {
    path: '/centerview',
    name: 'centerview',
    component: () => import('../views/CreateCenter.vue')
  },
  {
    path: '/docpreview',
    name: 'docpreview',
    component: () => import('../components/BookDetail')
  },
  {
    path: '/docpreview/docdetailview',
    name: 'docdetailview',
    component: () => import('../components/ReadingInterface')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const element = document.getElementById('banner');
  const top = document.getElementById('top');
  const bottom = document.getElementById('bottom');
  if (to.name === 'home') {
    // 在'home'路由下，显示元素
    element.style.display = 'block'
    top.style.display = 'block';
    bottom.style.display = 'flex';
  } else {
    // 在其他路由下，隐藏元素
    element.style.display = 'none'
    if (to.name === 'centerview') {
      bottom.style.display = 'none';
    } else {
      bottom.style.display = 'flex';
      if (to.name === 'login'|| to.name === 'signup'
          ||to.name === 'forget') {
        top.style.display = 'none';
        bottom.style.display = 'flex';
      } else {
        top.style.display = 'block';
        bottom.style.display = 'flex';
      }
    }
  }
  window.scrollTo(0, 0);
  next()
})

export default router
