import Vue from 'vue';
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';
import '../common/stylus/index.styl';
//首页组件
import index from '@/components/index/index';
//导入其他组件

//页面主体
import goods from '@/components/index//goods/goods';
//页面评价
import ratings from '@/components/index/ratings/ratings';
//页面商家信息
import seller from '@/components/index/seller/seller';

//其他主页
import other from '@/components/other/other';
//其他主页的内容页面
import hello from '@/components/other/hello/hello';

Vue.use(VueResource);
Vue.use(VueRouter);

let routes = [
  {
    path: '/',
    name: 'index',
    component: index,
    children: [
      {
        path: '/goods',
        component: goods
      }, {
        path: '/ratings',
        component: ratings
      }, {
        path: '/seller',
        component: seller
      }
    ]
  },
  {
    path: '/other',
    name: 'other',
    component: other,
    children: [
      {
        path: '/hello',
        component: hello
      }
    ]
  }
];

let router = new VueRouter({
  'linkActiveClass': 'active',
  routes
});

//制定默认进入的页面
router.push('/goods');
//另一个主页的内容页面
// router.push('/hello');

export default router;