//From origin/asset/boot.js
!function(f,n,j,c,b,e,h,d,a,k,l,g,i,m){j=document,c={},b=VERSION,e=BASE_PATH||location.protocol+'//'+location.host+location.pathname,h=e+'portal/',d=e+'portal/origin/',a=d+'asset/',k=d+'module/',l=d+'page/',g=d+'widget/',_.extend(c,{VERSION:b,PUBLISH_MODEL:PUBLISH_MODEL||'development',BASE_PATH:e,PORTAL_PATH:h,ORIGIN_PATH:d,ASSET_PATH:a,MODULE_PATH:k,PAGE_PATH:l,WIDGET_PATH:g,getAppData:function(b){var a=c.appData||{};return a[b];},setAppData:function(b,d){var a=c.appData||{};a[b]=d,c.appData=a;}}),i={},c.page=i,c.module={},require.config({paths:{mmHistory:a+'third/avalon/mmHistory.js',mmRouter:a+'third/avalon/mmRouter.js',events:a+'events.js',common:a+'common.js',util:a+'util.js',index:a+'index.js',json:a+'third/json2.js',dialog:g+'dialog/dialog',raphael:a+'third/raphael/raphael.js',uploadify:a+'third/uploadify/jquery.uploadify.js',moment:a+'third/moment.min.js',jwplayer:a+'third/jwplayer/jwplayer.js',sortable:a+'third/jquery-sortable.js',graphael:a+'third/graphael/g.raphael.js',highcharts:a+'third/highcharts/js/highcharts-all.js'},shim:{json:{exports:'JSON'},uploadify:{exports:function(){return{uploadify:$.fn.uploadify,SWF_PATH:a+'third/uploadify/uploadify.swf'};}},jwplayer:{exports:'jwplayer'},sortable:{exports:'$.fn.sortable'},graphael:{deps:['raphael'],exports:'Raphael'},highcharts:{exports:'Highcharts'},moment:{exports:'moment'}},debug:c.PUBLISH_MODEL==='development'}),_.extend(f.modules,{eve:{state:2}}),_.extend(f.filters,{encodeURIComponent:function(a){return encodeURIComponent(a);}}),m=f.bindingHandlers.value,f.bindingHandlers.value=function(d,f){var e=d.value.trim(),b=d.element,a=c.msValueStore||[];return a=_.reject(a,function(a){return a.element===b||!$.contains(j.body,a.element);}),a.push({element:b,text:e,vmodels:f}),c.msValueStore=a,m.apply(this,arguments);},b=b.split('.'),_.each(['js','css','text'],function(c){var a=require.config.plugins,d=a[c];a[c]=function(a){var e=Array.prototype.slice.call(arguments,0),c=a.split('?');return/\/asset\//.test(a)?a=c[0]+'?v='+b[0]+'-'+b[1]:/\/module\//.test(a)?a=c[0]+'?v='+b[0]+'-'+b[2]:/\/page\//.test(a)?a=c[0]+'?v='+b[0]+'-'+b[3]:/\/widget\//.test(a)&&(a=c[0]+'?v='+b[0]+'-'+b[4]),c[1]&&(a+='&'+c[1]),d.apply(this,[a].concat(e.slice(1)));},a[c].ext=d.ext;}),n.erp=c;}(window.avalon,window);