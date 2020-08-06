<template>
  <div class="goods">

    <div class="goods-left" ref="menuWrapper">
      <ul class="goods-ul">
        <li v-for="(item, index) in goods" class="menu-item" @click="selectMenu(index, $event)">
          <span class="goods-left-text">
            <span v-show="item.type>0" class=" icon" :class="classMap[item.type]"/>
            {{item.name}}
          </span>
        </li>
      </ul>
    </div>

    <div class="goods-right" ref="foodWrapper">
      <ul class="goods-ul">
        <li v-for="item in goods" class="food-list food-list-hook">
          <h1 class="title">{{item.name}}</h1>

          <ul class="goods-ul">
            <li v-for="food in item.foods" class="food-item" @click="selectFood(food, $event)">
              <div class="icon">
                <img :src="food.icon" alt="" width="57">
              </div>
              <div class="content">
                <h2 class="name">{{food.name}}</h2>
                <p class="desc">{{food.description}}</p>
                <div class="extra">
                  <span class="count">月售{{food.sellCount}}</span><span class="count">好评{{food.rating}}</span>
                </div>
                <div class="price">
                  <span class="now">￥{{food.price}}</span><span class="old"
                </div>
              </div>
            </li>
          </ul>

        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import star from "../star/star";

import BScroll from "better-scroll";
import { formatDate } from "../../../common/js/util.js";
import datas from "../../../../data.json";

export default {
  data() {
    return {
      goods:[],
      listHeight: [],
    }
  },   
  props: {
    seller: {
      type: Object
    }
  },
  created() {
    this.goods = datas.goods;
    this.classMap = ['decrease', 'discount', 'special', 'invoice', 'guarantee'];
    this.$nextTick(() => {
      this._initScroll();
      // this._calculateHeight();
    });
  },
  methods: {
    _initScroll() {
      this.menuScroll = new BScroll(this.$refs.menuWrapper, {
        click: true
      });
      this.foodScroll = new BScroll(this.$refs.foodWrapper, {
        probeType: 3,
        click: true
      });
      this.foodScroll.on('scroll', (pos) => {
        this.scrolly = Math.abs(Math.round(pos.y));
      });
    },
    _calculateHeight() {
      let foodList = this.$refs.foodWrapper.getElementsByClassName('food-list-hook');
      let height = 0;
      this.listHeight.push(height);
      for (let i = 0; i < foodList.length; i++) {
        let item = foodList[i];
        height += item.clientHeight;
        this.listHeight.push(height);
      }
    },
    selectMenu(index, event) {
      if (!event._constructed) {
        // 去掉自带click事件的点击
        return;
      }
      let foodList = this.$refs.foodWrapper.getElementsByClassName('food-list-hook');
      let el = foodList[index];
      this.foodScroll.scrollToElement(el, 300);
    },
    
    selectFood(food, event) {
      if (!event._constructed) {
        // 去掉自带click事件的点击
        return;
      }
      this.selectedFood = food;
      this.$refs.food.show();
    }


  }
}
</script>


<style lang="stylus" rel="stylesheet/stylus">
@import 'goods.styl';
</style>
