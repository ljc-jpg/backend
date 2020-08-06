<template>
  <div class="header">
    <div class="content-wrapper">
      <div class="avatar">
        <img width="64" height="64" :src="seller.seller.avatar">
      </div>

      <div class="content">
        <div class="title">
          <span class="brand"></span>
          <span class="name">{{seller.seller.name}}</span>
        </div>
        <div class="description">{{seller.seller.description}}/{{seller.seller.deliveryTime}}分钟送达</div>
        <div class="support" v-if="seller.seller.supports">
          <span class="icon" :class="classMap[seller.seller.supports[0].type]"></span>
          <span class="text">{{seller.seller.supports[0].description}}</span>
        </div>
      </div>

      <div class="supports-count" @click="showDeatil(seller.seller.supports.length)">
        <span class="count">{{seller.seller.supports.length}}个</span>
        <i class="icon iconfont icon-zuoyoujiantou"></i>
      </div>
    </div>

    <div class="background">
      <img :src="seller.avatar" alt class width="100%" height="100%">
    </div>

    <div class="bulletin-wrapper" @click="showDeatil(seller.seller.supports.length)">
      <span class="bulletin-title"></span>
      <span class="bulletin-text">{{seller.seller.bulletin}}</span>
      <i class="icon iconfont icon-zuoyoujiantou"></i>
    </div>

    <transition name="fade">
      <div v-show="detailShow" @click="cloaseDeatil(1)" class="detail">
        <div class="detail-wrapper">
          <div class="detail-main">
            <h1 class="name">{{seller.seller.name}}</h1>

            <div class="star-wrapper">
              <!-- 父组件将值传给子组件 -->
              <!-- 父组件直接用 v-on 来监听子组件spot事件。 -->
              <!-- 父组件调用子组件方法需要加上 ref="child1" -->
              <star :size="48" :score="seller.seller.score" v-on:spot="spot" ref="child1"></star>
            </div>

            <div class="title">
              <div class="line"></div>
              <div class="text">优惠信息</div>
              <div class="line"></div>
            </div>

            <ul v-if="seller.seller.supports" class="supports">
              <li class="support-item" v-for="(item, index) in seller.seller.supports">
                <span class="icon" :class="classMap[seller.seller.supports[index].type]"></span>
                <span class="text">{{seller.seller.supports[index].description}}</span>
              </li>
            </ul>

            <div class="title">
              <div class="line"></div>
              <div class="text">商家公告</div>
              <div class="line"></div>
            </div>

            <div class="bulletin">
              <p class="content">{{seller.seller.bulletin}}</p>
            </div>

            <div class="detail-close" @click="cloaseDeatil(2)">
              <i class="iconfont icon-cha"></i>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script type="text/ecmascript-6">
import star from "../star/star";
import {sendAjax} from "../../../common/js/util.js";
import $ from 'jquery'

export default {
  name: "Header",
  //子接受父组件传入的参数
  props: {
    seller: {
      type: Object
    }
  },
  components: {
    star
  },
  data() {
    return {
      detailShow: false,
      userList: []
    };
  },
  created() {
    this.classMap = ["decrease", "discount", "guarantee", "invoice", "special"];
    let data = {
      "id":"16592598d4f5416f84769832a28b6de5"
    } 
    
    $.ajax({
      type : "POST", //提交方式 
      data : data,
      url : "http://localhost:8749/user/selectByUserId",//路径 
      success : function(result) {//返回数据根据结果进行相应的处理 
        if ( result.returnCode ==1 ) { 
          console.log(result.data)
        } else { 
          console.log('test/searchTest调用失败')
        } 
      } 
    }); 
    
  },
  methods: {
    showDeatil(e) {
      // console.log(e);
      this.detailShow = true;
      //父组件通过this.$refs.子组件ref="child1"的child1   调用子组件childSagy方法
      this.$refs.child1.childSagy();
    },
    cloaseDeatil(e) {
      // console.log(e);
      this.detailShow = false;
    },
    //父组件监控子组件的事件和传入的参数
    spot(e) {
      console.log(e);
    },
    fatherSay() {
      console.log("我是父组件方法");
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import 'header.styl';
</style>
