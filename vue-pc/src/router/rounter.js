import Vue from 'vue';
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

//首页
import index from '@/components/index/index';


import ratings from '@/components/ratings/ratings.vue';

import liveWeb from '@/components/liveWeb/liveWeb';

import studentScore from '@/components/studentScore/studentScore';

import teacherSign from '@/components/teacherSign/teacherSign';

import classCircle from '@/components/classCircle/classCircle';




Vue.use(VueResource);
Vue.use(VueRouter);

let routes = [
  {
    path: '/',
    name: 'index',
    component: index,
    children: [
      {
        path: '/ratings',
        component: ratings
      }, {
        path: '/liveWeb',
        component: liveWeb
      }, {
        path: '/studentScore',
        component: studentScore
      }, {
        path: '/teacherSign',
        component: teacherSign
      } , {
    
        path: '/classCircle',
        component: classCircle
      }
    ]
  }
];

let router = new VueRouter({
  mode: 'history', 
  'linkActiveClass': 'active',
  routes
});

//制定默认进入的页面
router.push('/');
//另一个主页的内容页面
// router.push('/hello');

export default router;