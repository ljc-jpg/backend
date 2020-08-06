<template>
  <div style="height:100%">

    <div class="top">
      <div class="top-left" v-bind:class="{ letActive: isCollapse,leftAnimation:isActive }">
          <img src="../../common/img/logo.png"  style="width:100%;height:100%">
      </div>
      <div class="top-right" v-bind:class="{ rightActive: !isCollapse,rightAnimation:isActive,rightActiveAnimation:isCollapse }">
          22323
      </div>
    </div>
    <div class="left"  v-bind:class="{ letActive: isCollapse,leftAnimation:isActive }">
      
        <v-Menu :isCollapse="isCollapse" :routers="routers"/>
        
        <div class="controller"  @click="menuController(isCollapse)">
          <i class="el-icon-arrow-left" v-if="!isCollapse"/>
          <i class="el-icon-arrow-right" v-if="isCollapse"/>
        </div>
    </div>

    <div class="right" v-bind:class="{ rightActive: !isCollapse,rightAnimation:isActive,rightActiveAnimation:isCollapse }">
      <!--包裹动态组件时，会缓存不活动的组件实例，而不是销毁它们 -->
       <keep-alive>
            <router-view></router-view>
      </keep-alive>

    </div>

  </div>
</template>

<script type="text/ecmascript-6">
import Menu from "./menu.vue";
import $ from 'jquery'

export default {
  data() {
    return {
      isCollapse: false,
      isActive:false,
      routers:[]
    };
  },
  created() {
    let that = this;
    $.ajax({
      type : "POST", //提交方式 
      data : {schId:"",xqhId:""},
      url : "http://127.0.0.1:8751/Menu/MenuBySchId",//路径 
      success : function(result) {//返回数据根据结果进行相应的处理 
        if ( result.returnCode ==1 ) { 
          that.routers = result.data;
        } else { 
          console.log('test/searchTest调用失败')
        } 
      } 
    }); 
  },
  components: {
    "v-Menu": Menu
  },  
  watch: {
    isCollapse(val,oldValue) {
      this.isActive = true;
    }
  },
  methods: {
    menuController(data){
      this.isCollapse = !data;
    }
  }
  
};
</script>

<style lang="stylus" rel="stylesheet/stylus" >
html, body, #app {
  height : 100%;
}

body {
  margin : 0 ;
}

.controller {
  background-color : red;
  position:absolute;
  top: 50%;
  right: 0;
}

.top {
  height 10%
  background-color : #f8f8f8
  .top-left {
    float: left;
    width: 15%;
    height: 100%;
    background-color :#4f4;
  }
  .top-right {
    float : right;
    width : 85%;
    height : 100%;
  }
}

.left {
  float : left;
  width : 15%;
  height : 90%;
  background : #333;
  overflow-y : auto;
  overflow-x : hidden;
  position : relative;
}

.leftAnimation
  -webkit-animation:myfirst1 1s;

@-webkit-keyframes myfirst1 {
  0%   {width:5%;}
  20%  {width:7%;}
  40%  {width:9%;}
  60%  {width:11%;}
  80% {width:13%;}
  100% {width:15%;}
}

.right {
  width : 92%;
  height : 83%;
  float : right;
  background : #e7e7e7 ;
  // background : green;
  overflow : hidden;
  padding-top: 1.8%;
  padding-bottom: 1.5%;
  padding-left : 2%;
  padding-right : 1%
}  

.rightAnimation
  -webkit-animation:myfirst3 1s;

@-webkit-keyframes myfirst3 {
  0% {width:92%;}
  20%  {width:90%;}
  40%  {width:88%;}
  60%  {width:86%;}
  80%  {width:84%;}
  100%   {width:82%;}
}

.letActive {
  -webkit-animation:myfirst2 1s;
  width: 4% !important;
  .el-menu >.el-submenu:nth-child(1)>.el-submenu__title{
    padding: 0 0 0 30% !important;
  }
}

@-webkit-keyframes myfirst2 {
  0% {width:15%;}
  20%  {width:13%;}
  40%  {width:11%;}
  60%  {width:9%;}
  80%   {width:7%;}
  100%   {width:4%;}
}

.rightActive {
  width : 82% !important;
}

.rightActiveAnimation {
  width : 93% !important;
  -webkit-animation : myfirst4 1s;
}

@-webkit-keyframes myfirst4 {
  0%  {width:82%;}
  20%   {width:84%;}
  40%  {width:86%;}
  60%  {width:88%;}
  80%  {width:90%;}
  100%  {width:93%;}
}

/*修改滚动条样式*/
.left::-webkit-scrollbar
  width 10px
  height 10px

.left::-webkit-scrollbar-track
  background rgb(239, 239, 239) 
  border-radius 2px 

.left::-webkit-scrollbar-thumb
  background #bfbfbf
  border-radius 10px

.left::-webkit-scrollbar-thumb:hover 
  background #333

.left::-webkit-scrollbar-corner 
  background #179a16
  

</style>
