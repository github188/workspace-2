//From origin/asset/index.js
define('index',['avalon','util','common','originmoduleuserwidgetuserwidgetjs','ready!'],function(e,f,i){function g(d,a){var b='',c;return a=a||0,a++,_.each(d,function(c){var e,d=c.navTitle||c.title,f='',h='',i='',j='',k=c.phref?'phref='+c.phref:'',l=!!c.forceRefresh;c.path?e=c.path:e=c.route?'#'+c.route:'javascript:;',a===1?(d='<i class="icon-toggle iconfont" data-state="hidden">&#xe600;</i>&nbsp;&nbsp;'+d,i='ms-click="mainNavToggle"'):j='ms-hover=state-hover',c.isHidden&&(f='fn-hide'),l&&(h+='force-refresh-link'),b+='<li class="page-nav-item '+f+'"><div class="nav-title"><a '+j+' href="'+e+'" '+i+' '+k+' class="'+h+'">'+d+'</a></div>',c.children&&c.children.length>0?b+=g(c.children,a)+'</li>':b+='</li>';}),a===1?c='main-page-nav-list':c='sub-page-nav-list fn-hide','<ul class="'+c+' page-nav-list page-nav-'+a+'">'+b+'</ul>';}var b=this,c=b.erp,a=c.page,h=a.pageEvent,d={};return _.extend(d,{$init:function(){i.setup(function(){var d=$('.app-bd'),n=$('.app-hd'),j=e.define('app',function(a){a.appBdMinHeight=0,a.pageTitle='',a.blankImgSrc=c.ASSET_PATH+'image/s.gif',a.adjustBdMinHeight=function(){var c=$('.app-inner-l',d).height(),e=n.outerHeight(),f=$(b).height();c<f-e&&(c=f-e),a.appBdMinHeight=c;},a.mainNavToggle=function(){var e=$(this).closest('.page-nav-item'),b=$('.icon-toggle',this),c=e.children('.page-nav-list'),d=b.data('state');!d||d=='shown'?(c.stop(!0,!0).slideUp('fast',function(){a.adjustBdMinHeight();}),b.html('&#xe600;'),b.data('state','hidden')):(c.stop(!0,!0).slideDown('fast',function(){a.adjustBdMinHeight();}),b.html('&#xe601;'),b.data('state','shown'));},a.clickNavVisibleHandler=function(){var a=$(this),c=$('.app-inner-l',d),e=$('.app-inner-l-inner',d),f=$('.app-inner-r',d);a.hasClass('state-shown')?(e.hide(),c.css('overflow','hidden').stop(!0,!0).animate({width:0},'fast',function(){f.css('margin-left',0),a.css('left',0),a.addClass('state-hidden').removeClass('state-shown').html('&#xe625;');})):(e.show(),c.css('overflow','visible').stop(!0,!0).animate({width:'230px'},'fast',function(){f.css('margin-left','231px'),a.css('left',230-$(b).scrollLeft()+'px'),a.addClass('state-shown').removeClass('state-hidden').html('&#xe624;');}));};}),k=$('.page-nav-wrapper');k.html(g(a.routes)),setTimeout(function(){j.adjustBdMinHeight();},300),f.regPageNav($('a',k));var o;$(b).scroll(function(){var a=$('.page-nav-visible-h',d);clearTimeout(o),o=setTimeout(function(){a.hasClass('state-shown')&&a.css({left:230-$(this).scrollLeft()+'px'});},30);}),h.on('inited switched',function(b,c,a){j.adjustBdMinHeight(),j.pageTitle=a.title;}),e.scan(),a.lastPagePath||f.jumpPage('#/welcome');var i,l,m;if(a.lastPagePath){i=$('[href="#'+a.lastRoute+'"]',k),l=i.closest('.page-nav-list'),m=l.prev('.nav-title');while(m.length)i=$('a',m),l=i.closest('.page-nav-list'),m=l.prev('.nav-title');$('.icon-toggle',i).click();}});},$remove:function(){}}),d;});