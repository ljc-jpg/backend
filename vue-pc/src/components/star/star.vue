<template>
  <!-- 子组件绑定spot事件向父组件传递参数，并监控事件 -->
  <div class="star" v-on:click="spot">
    <div class="star-item" :class="classType">
      <span v-for="itemClasse in itemClasses" :class="itemClasse" class="star-item"/>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
//最高评分
const LENGTH = 5;
//满星星
const CLS_ON = "on";
//半星星
const CLS_HALF = "half";
//空星星
const CLS_OFF = "off";

export default {
  created() {
    //取父组件值传入的值
    // console.log(this.size);
    this.size = 48;
    this.score = 4.3;
    console.log(this.size)
  },
  //子组件接受父组件值
  props: {
    size: {
      type: Number
    },
    score: {
      type: Number
    }
  },
  //computed计算属性,用来监控自己定义的变量，在computed,或其他组件传入里面定义
  //watch主要用于监控vue实例的变化，监控的变量必须在data里面声明
  computed: {
    classType() {
      return "star-" + this.size;
    },
    itemClasses() {
      let itemClasses = [];
      //分数处理   4.0-4.4转为4  4.5-4.9转为4.5
      let score = Math.floor(this.score * 2) / 2;
      //判断是否需要半颗星
      let hasHalf = score % 1 !== 0;
      //整星的数量
      let num = Math.floor(score);
      for (let i = 0; i < num; i++) {
        itemClasses.push(CLS_ON);
      }
      if (hasHalf) {
        itemClasses.push(CLS_HALF);
      }
      while (itemClasses.length < LENGTH) {
        itemClasses.push(CLS_OFF);
      }
      // return [CLS_ON, CLS_ON, CLS_ON, CLS_OFF];
      return itemClasses;
    }
  },
  methods: {
    spot() {
      //通过此方法子组件向父组件传递参数
      this.$emit(
        "spot",
        "我是子组件传给父组件的内容就我    父组件监听子组件事件成功！"
      );
      //通过this.$parent.调用父组件fatherSay方法
      this.$parent.fatherSay();
    },
    childSagy() {
      console.log("我是子组件方法");
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">


</style>
