import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Order from '../views/Order.vue'
import Menu from '../views/Menu.vue'
import Cart from '../views/Cart.vue'
import About from '../views/About.vue'

const routes = [
  { path: '/', component: Home },
  {
    path: '/order',
    component: Order,
    children: [
      { path: 'menu', component: Menu },
      { path: 'cart', component: Cart }
    ]
  },
  { path: '/about', component: About }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router