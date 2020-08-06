<template>
  <div>
    <v-header :seller="seller"></v-header>
    <!-- 主体切换 -->
    <div class="tab border-1px">
      <div class="tab-item">
        <router-link v-bind:to="'/goods'">
          商品
        </router-link>
      </div>
      <div class="tab-item">
        <router-link to="/ratings">
          评论
        </router-link>
      </div>
      <div class="tab-item">
        <router-link to="/seller">
          商家
        </router-link>
      </div>
    </div>

    <!-- 包裹动态组件时，会缓存不活动的组件实例，而不是销毁它们 -->
    <!-- 主体内容 -->
    <keep-alive>
      <router-view :seller="seller"></router-view>
    </keep-alive>
  </div>
</template>

<script type="text/ecmascript-6">
import Header from "./../index/header/header.vue";
import datas from "../../../data.json";

export default {
  data() {
    return {
      seller: {}
    };
  },
  created() {
    this.seller = datas;
  },
  components: {
    "v-header": Header
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import '../../common/stylus/mixin.styl';

.tab {
  display: flex;
  width: 100%;
  height: 40px;
  line-height: 40px;
  border-1px(rgba(7, 17, 27, 0.1));
}

.tab .tab-item {
  flex: 1;
  text-align: center;
}

.tab .tab-item a {
  display: block;
  font-size: 14px;
  text-decoration: none
  color: rgb(77, 85, 93);
}

.tab .tab-item .active {
  color: rgb(240, 20, 20);
}
</style>
