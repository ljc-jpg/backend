<template>
<!--  default-active="1-4-1" 默认展开  class="el-menu-vertical-demo"     -->
<el-menu :collapse="isCollapse" text-color="#fff" active-text-color="#fff">
    

 <!--    <div  v-for="(item, index) in routers" :key="index" v-if="item.menus.length==0">
            <el-menu-item :index="item.url" @click="go(item.url)" >
            <i :class='item.icon'/<span slot="title">{{item.menuName}}</span>>
        </el-menu-item>  
    </div> -->

    <el-submenu :index="item.url" v-for="(item, index) in routers" :key="index" v-if="item.menus.length!=0">
        <template slot="title">
            <i :class='item.icon'/><span slot="title">{{item.menuName}}</span>
        </template>

        <div  v-for="(item, axdex) in item.menus" :key="axdex">
            <el-menu-item :index="item.url" @click="go(item.url)" v-if="0==item.menus.length">
                <i :class='item.icon'/>{{item.menuName}}
            </el-menu-item>
        
            <el-submenu :index="item.url" v-if="0!=item.menus.length">
                <template slot="title">
                    <i :class='item.lessIcon'/><span slot="title">{{item.menuName}}</span>
                </template>
                
                <el-menu-item :index="item.lessUrl" @click="go(item.lessUrl)" v-for="(item, txdex) in item.menus" :key="txdex">
                    <i :class='item.lessIcon'/>{{item.lessMenuName}}
                </el-menu-item>
            </el-submenu>
        </div>
    </el-submenu>
    

</el-menu>
</template>

<script>
export default {
    methods: {
        go(data){
            if(data){
                // console.log(data)
                this.$router.push(data)
            }
        }
    },
    //子组件接受父组件值
    props: {
        routers: {
            type: Array
        },
        isCollapse:{
            type:Boolean
        }
    }
};
</script>

<style lang="css" >

.el-menu-item {
    background-color: #333  !important;
}

.el-submenu__title {
    background-color: #333  !important;
} 

.el-menu {
    border-right: none;
}

.el-submenu__title > i:nth-child(1){
    width: 17.6px;
    padding: 0 3px 0 0;
}


.el-submenu__title:hover{
    outline: 0 !important;
    color: #409EFF !important;
}

.el-menu-item:hover{
    outline: 0 !important;
    color: #409EFF !important;
}

.el-menu-item.is-active {
    color: #fff !important;
    background-color: #409EFF !important;
}
 
   /* .el-menu--vertical>.el-submenu .el-submenu__title {
       padding: 0;
  } */

/* 水平菜单子菜单中的所有菜单项 */
/* .el-menu--vertical .el-menu .el-menu-item {
    padding: 0;
}  */

/* 水平菜单子菜单的标题（注意，标题和菜单项不一样，标题是使用<i slot="title">表示的，需要单独处理），
这里使用.el-submenu>.el-submenu_title定位到子菜单（不是菜单项） */
.el-menu--vertical .el-menu {
    padding: 0;
} 
  

</style>
