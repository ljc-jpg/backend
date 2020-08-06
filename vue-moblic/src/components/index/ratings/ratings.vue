<template>
  <div class="ratings">
    <div class="ratings-content">
      <div class="overView">
        <div class="overView-left">
          <div class="overView-left-top">
            <span class="overView-left-top-title">{{sellers.seller.score}}</span>
            <span class="overView-left-top-content">口味</span>
            <div class="overView-left-top-star">
              <star :size="24" :score="sellers.seller.score"/>
            </div>
            <span class="overView-left-top-score">{{sellers.seller.kw}}</span>
          </div>

          <div class="overView-left-bottom">
            <span class="overView-left-bottom-title">商家评分</span>
            <span class="overView-left-top-content">包装</span>
            <div class="overView-left-top-star">
              <star :size="24" :score="sellers.seller.score"/>
            </div>
            <span class="overView-left-top-score">{{sellers.seller.bz}}</span>
          </div>
        </div>

        <div class="overView-right">
          <div class="overView-right-title">{{sellers.seller.myd}}</div>
          <div class="overView-right-content">配送满意度</div>
        </div>
      </div>

      <splite/>

      <div class="content">
        <div class="content-title">
          <label class="content-content-title" :class="{ 'title-active':selected[2] }">
            <input @click="needShow($event)" value="2" class="radio" name="sex">全部
          </label>
          <label class="content-content-title my" :class="{ 'title-active':selected[0] }">
            <input @click="needShow($event)" value="0" type="radio" class="radio" name="sex">满意
          </label>
          <label class="content-content-title tc" :class="{ 'title-active':selected[1] }">
            <input @click="needShow($event)" value="1" class="radio" name="sex">吐槽
          </label>
        </div>

        <ul class="content-content-ul">
          <li v-for="info in ratings" class="content-content-top">
            <div class="content-content-left">
              <img :src="info.avatar" class="avatar" width="28" height="28">
            </div>
            <div class="content-content-right">
              <div class="content-content-right-top">
                <h1 class="name">{{info.username}}</h1>
                <div class="time">{{info.rateTime | formatDate}}</div>
              </div>

              <div class="content-content-middle">
                <star :size="24" :score="info.score"/>
                <p class="content">{{info.score}}</p>
              </div>

              <div class="content-content-bottom">
                <span class="content">{{info.text}}</span>
              </div>

              <div class="content-content-last" v-show="info.recommend &&info.recommend.length">
                <i class="iconfont icon-damuzhi"></i>
                <span class="iconfont-text" v-for="icon in info.recommend">{{icon}}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import splite from "../split/split";
import star from "../star/star";

import BScroll from "better-scroll";
import { formatDate } from "../../../common/js/util.js";
import datas from "../../../../data.json";

export default {
  props: {
    seller: {
      type: Object
    }
  },
  data() {
    return {
      selected: [false, false, true],
      sellers: [],
      ratings:[]
    };
  },
  components: {
    splite,
    star
  },
  created() {
    this.sellers = datas;
    this.ratings = datas.ratings;
    if (!this.scroll) {
      this.$nextTick(() => {
        this.scroll = new BScroll(this.$el, { click: true });
      });
    } else {
      this.scroll.refresh();
    }
  },
  methods: {
    needShow(selected) {
      let event = selected.target.value;
      //替换数组的下标  替换的长度   替换的值
      for (let i = 0; i < 3; i++) {
        if (i == event) {
          this.selected.splice(i, 1, true);
        } else {
          this.selected.splice(i, 1, false);
        }
      }
    }
  },
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd hh:mm");
    }
  },
  watch: {
    selected: function(newVal) {
      //下标 0 my  1 tc 2  qb
      for (let i = 0; i < 3; i++) {
        if (newVal[i] == true) {
          if (i == 0) {
            this.ratings = datas.my;
          } else if (i == 1) {
            this.ratings = datas.tc;
          } else{
            this.ratings =datas.ratings;
          }
        } else {
          continue;
        }
      }
      //重新计算ul长度
      this.$nextTick(() => {
        this.scroll.refresh();
      });
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import 'ratings.styl';
</style>

