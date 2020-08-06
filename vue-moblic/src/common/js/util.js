 // 解析url参数
 export function urlParse() {
   let url = window.location.search;
   let obj = {};
   let reg = /[?&][^?&]+=[^?&]]+/g;
   let arr = url.match(reg);
   if (arr) {
     arr.forEach((item) => {
       let tempArr = item.substring(1).split('=');
       let key = tempArr[0];
       let val = tempArr[1];
       obj[key] = val;
     });
   }
   return {
     id: 123123
   };
 };

 //发Ajax请求
 export function sendAjax(type, path, data, async, successCallback, errorCallback) {
   $.ajax({
     type: type,
     url: path,
     data: data,
     async: async,
     xhrFields: {
       withCredentials: true
     },
     crossDomain: true,
     beforeSend: function (XMLHttpRequest) {
       XMLHttpRequest.setRequestHeader("X-Requested-With", "XMLHttpRequest");
     },
     success: function (data) {
       successCallback(data);
     },
     error: function (xhr, type, exception) {
       errorCallback(xhr, type, exception);
     }
   });
 }

 //浏览器存数据
 export function savaToLocal(id, key, value) {
   let seller = window.localStorage.__seller__;
   if (!seller) {
     seller = {};
     seller[id] = {};
   } else {
     seller = JSON.parse(seller);
     if (!seller[id]) {
       seller[id] = {};
     }
   }
   seller[id][key] = value;
   window.localStorage.__seller__ = JSON.stringify(seller);
 }

 //浏览器取数据
 export function loadFromlLocal(id, key, def) {
   let seller = window.localStorage.__seller__;
   if (!seller) {
     return def;
   }
   seller = JSON.parse(seller)[id];
   if (!seller) {
     return def;
   }
   let ret = seller[key];
   return ret || def;
 }

 //日期处理
 export function formatDate(date, fmt) {
   if (/(y+)/.test(fmt)) {
     fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
   }
   let o = {
     'M+': date.getMonth() + 1,
     'd+': date.getDate(),
     'h+': date.getHours(),
     'm+': date.getMinutes(),
     's+': date.getSeconds()
   };
   for (let k in o) {
     if (new RegExp(`(${k})`).test(fmt)) {
       let str = o[k] + '';
       fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
     }
   }
   return fmt;
 }

 function padLeftZero(str) {
   return ('00' + str).substr(str.length);
 }
