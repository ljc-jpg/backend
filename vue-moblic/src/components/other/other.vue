<template>
  <div>
    <div>{{other}}</div>
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script type="text/ecmascript-6">
import hello from "./hello/hello";
export default {
  data() {
    return {
      other: "welcome to vue for you ! db743f3048c0c1453d507dc44f379002eae2c8ec"
    };
  },
  components: {
    hello
  }
};
</script>
