<template>
  <div class="seller">
    <div class="seller-content">
      <div class="overview">
        <h1 class="title">{{seller.seller.name}}</h1>

        <div class="desc border-1px">
          <star :size="36" :score="seller.seller.score"></star>
          <span class="text">({{seller.seller.score}})</span>
          <span class="text">月售{{seller.seller.sellCount}}单</span>
        </div>

        <div class="favorite" @click="toggleFavorite($event)">
          <i class="iconfont icon-aixin" :class="{'active':favorite}"></i>
          <span>{{favoriteText}}</span>
        </div>

        <ul class="remark" v-if="seller.seller.deatals" >
          <li class="block" v-for="deatal in seller.seller.deatals">
            <h2>{{deatal.title}}</h2>
            <div class="content">
              <span class="stress">{{deatal.val}}</span>
            </div>
          </li>
        </ul>

      </div>

      <split></split>

      <div class="bulletin">
        <h1 class="title">公告与活动</h1>

        <div class="content-wrapper border-1px">
          <p class="content">{{seller.seller.bulletin}}</p>
        </div>

        <ul v-if="seller.seller.supports" class="supports">
          <li class="support-item" v-for="(item, index) in seller.seller.supports">
            <span class="icon" :class="classMap[seller.seller.supports[index].type]"></span>
            <span class="text">{{seller.seller.supports[index].description}}</span>
          </li>
        </ul>
      </div>

      <split></split>

      <div class="pics">
        <h1 class="title">商家实景</h1>

        <div class="pic-wrapper" ref="picWrapper">
          <ul class="pic-list" ref="picList">
            <li class="pic-item" v-for="pic in seller.seller.pics">
              <img :src="pic" width="120" height="120">
            </li>
          </ul>
        </div>
      </div>

      <split></split>

      <div class="info">
        <div class="title border-1px">商家信息</div>

        <ul class="info-content">
          <li class="info-item" v-for="info in seller.seller.infos">{{info}}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
import star from "../star/star.vue";
import split from "../split/split.vue";

import BScroll from "better-scroll";

export default {
  props: {
    seller: {
      type: Object
    }
  },
  components: {
    star,
    split
  },
  data() {
    return {
      favorite: false
    };
  },
  //计算属性
  computed: {
    favoriteText() {
      return this.favorite ? "已收藏" : "收藏";
    }
  },
  created() {
    this.classMap = ["decrease", "discount", "special", "invoice", "guarantee"];
    if (!this.scroll) {
      this.$nextTick(() => {
        this.scroll = new BScroll(this.$el, { click: true });
      });
    } else {
      this.scroll.refresh();
    }
  },
  methods: {
    toggleFavorite(event) {
      // console.log(event);
      if (!event._constructed) {
        return;
      }
      this.favorite = !this.favorite;
      //无后台数据保存在浏览器缓存中   有后台Ajax请求得到数据
      // savaToLocal(this.seller.id, "favorite", this.favorite);
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import 'seller.styl';
</style>
