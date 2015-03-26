/**
 * 回访记录模块
 */
define(["avalon", "util", "json", "moment", "text!./visitinfo.html", "text!./visitinfo.css", "common", "../../widget/dialog/dialog", '../../module/address/address'], function (avalon, util, JSON, moment, tmpl, cssText) {
    var win = this,
        erp = win.erp;
    var styleEl = $("#module-style");
    if (styleEl.length === 0) {
        styleEl = $('<style id="module-style"></style>');
        styleEl.appendTo('head');
    }
    var styleData = styleEl.data('module') || {};

    if (!styleData["visitinfo"]) {
        try {
            styleEl.html(styleEl.html() + util.adjustModuleCssUrl(cssText, 'visitinfo/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += util.adjustModuleCssUrl(cssText, 'visitinfo/');
        }
        styleData["visitinfo"] = true;
        styleEl.data('module', styleData);
    }
    var module = erp.module.visitinfo = function (element, data, vmodels) {
        var opts = data.visitinfoOptions,
            elEl = $(element);
        var visitinfoDialogId = data.visitinfoId + '-dialog',//dialog对话框
            visittype1 = data.visitinfoId + '-type1',//联系人所属
            visittype2 = data.visitinfoId + '-type2',//联系人
            addressModule = data.visitinfoId + '-addressModule'//添加联系人

        var model = avalon.define(data.visitinfoId, function (vm) {
            avalon.mix(vm, opts);
            var time;
            vm.argv = visittype2;//添加通讯录传参
            vm.maxTab = 1;//回访需求单&变更联系人标签切换依据
            vm.languageTab = 1;//话术标签切换依据
            vm.isEnd = 0;//通话是否结束
            vm.startTime = 0;//通话时间
            vm.hours = 0;//小时
            vm.min = '00';//分钟
            vm.seconds = '00';//秒
            vm.name = '';//变更联系人姓名
            vm.mobilePhone = '';//变更联系人电话
            vm.logName = '';//回访记录联系人
            vm.logMobilePhone = '';//回访记录电话
            vm.questions = [];//回访问题列表
            vm.language = [];//话术列表
            vm.linkArr = [];//联系人
            //发送后台的部分数据
            vm.documentType = '';//需求题类型
            vm.requirementId = '';  //需求的ID
            vm.merchantId = '';  //商户ID
            vm.beginTime = '';//开始时间
            vm.endTime = '';//结束时间
            vm.remark = '';//备注
            vm.contactId = ''; //如果不为空 是变更联系人
            //需要验证的dom类别
            vm.domArr = [];
            /*测试
            vm.arr = [
                {text:'商户',value:1,isSelected:1,children:[{text:'b111',value:1,isSelected:0,children:'',mobilePhone:'13222222222'},{text:'b222',value:2,isSelected:0,children:'',mobilePhone:'15222222222'}]},
                {text:'前端',value:2,isSelected:0,children:[{text:'c111',value:1,isSelected:0,children:'',mobilePhone:'13333222222'},{text:'c222',value:2,isSelected:0,children:'',mobilePhone:'13222244444'}]}
            ];
            测试-end*/
            vm.changeMaxTab = function(){//回访需求单&变更联系人标签切换
                var id = $(this).attr('data-id');
                vm.maxTab = id;
            };
            vm.changeLanguageTab = function(){//话术标签切换
                var id = $(this).attr('data-id');
                vm.languageTab = id;
            };
            vm.changeEnd = function(){//回访结束（记时结束）
                vm.endTime = moment()/1;
                vm.isEnd = 1;
                clearInterval(time);
            };
            vm.changeLog = function(){//变更回访记录联系人&电话
                vm.logName = vm.name;
                vm.logMobilePhone = vm.mobilePhone;
                vm.contactId = avalon.getVm(visittype2).selectedValue;
            };

            vm.$type1SelectOpts = {//联系人所属
                "selectId" : visittype1,
                "options" : [],
                "onSelect" : function(v,t,o){
                    vm.setSelect(o.children,visittype2);
                }
            };
            vm.$type2SelectOpts = {//联系人
                "selectId" : visittype2,
                "options" : [],
                "onSelect" : function(v,t,o){
                    vm.name = t;
                    vm.mobilePhone = o['mobilePhone'];
                },
                "getTemplate": function (tmpl) {
                    var tmps = tmpl.split('MS_OPTION_HEADER');
                    tmpl = 'MS_OPTION_HEADER<ul class="select-list" ms-on-click="selectItem"ms-css-min-width="minWidth">' +
                        '<li class="select-item" ms-repeat="options" ms-css-min-width="minWidth - 16"' +
                        'ms-class="state-selected:$index==selectedIndex" ms-class-last-item="$last" ms-hover="state-hover" ' +
                        'ms-data-index="$index" ms-attr-title="el.text" ms-data-value="el.value">{{el.text}}</li>' +
                        '<li><a style="display: block;line-height: 35px;background: #eee;border-top: 1px solid #ccc;cursor: pointer;text-indent: 10px;" ms-click=addCommunication(argv) href="javascript:;">添加联系人</a></li>' +
                        '</ul>';
                    return tmps[0] + tmpl;
                }
            };
            //回访问题单选（设置isSelected=1&其他兄弟isSelected=0）(如果是第一个且选择否其他的问题隐藏---数据格式有问题。。。)
            vm.radioBox = function(obj){
                var questionsLen = vm.questions.length;
                var dialog = avalon.getVm(visitinfoDialogId);
                var con=obj['sysDocumentDtlOptionList'];
                var inputClass=$(this).attr('data-class');
                for(var i=0;i<con.length;i++){
                    con[i].isSelected = '0';
                    con[i].comment = '';
                }
                obj.comment = 'true';
                this.$vmodel.$model.el.isSelected = '1';
                util.testing($(dialog.widgetElement)[0],$('.'+inputClass));
                var val = $(this).attr('data-val');
                var text=this.$vmodel.$model.el.optionContent;
                if(val=='0' && text=='否'){
                    vm.domArr=['.question0'];
                    for(var i=1;i<questionsLen;i++){
                        $('.question'+i).hide();
                        $('.question'+i).find('input').removeAttr('checked');
                        vm.questions[i]['comment'] = '';
                        for(var j=0;j<vm.questions[i]['sysDocumentDtlOptionList'].length;j++){
                            vm.questions[i]['sysDocumentDtlOptionList'][j]['isSelected'] = '0';
                            vm.questions[i]['sysDocumentDtlOptionList'][j]['comment'] = '';
                        }
                    }
                    util.testCancel();
                }else if(val=='0' && text=='是'){
                    vm.domArr=['.question0'];
                    for(var i=1;i<questionsLen;i++){
                        $('.question'+i).show();
                        vm.domArr.push('.question'+i);
                    }
                }
            };
            //回访问题多选（如果isSelected=1就设置未0否则设置未1)
            vm.checkBox = function(obj){
                var dialog = avalon.getVm(visitinfoDialogId);
                var inputClass=$(this).attr('data-class');
                var con = this.$vmodel.$model.el;
                if(con.isSelected == '1'){
                    con.isSelected = '0';
                }else{
                    con.isSelected = '1';
                }
                var newArr = obj['sysDocumentDtlOptionList'];
                var flag = '';
                for(var i=0;i<newArr.length;i++){
                    if(newArr[i].isSelected == '1'){
                        flag = 'true';
                    }
                }
                if(flag){
                    obj.comment = 'true';
                }else{
                    obj.comment = '';
                }
                util.testing($(dialog.widgetElement)[0],$('.'+inputClass));
            };
            vm.$visitinfoOpts = {//对话框dialog
                "dialogId": visitinfoDialogId,
                "width": 900,
                "autoFocusInput": false,
                "title": "回访记录",
                "getTemplate": function (tmpl) {
                    return '<div class="ui-dialog-mask" ms-visible="toggle"></div>' +
                    'MS_OPTION_WIDGET' +
                    '<div ms-widget="dialog,MS_OPTION_ID,MS_OPTION_OPTS"></div>' +
                    'MS_OPTION_INNERWRAPPER' +
                    '<div class="ui-dialog-inner" ms-draggable></div>' +
                    'MS_OPTION_HEADER' +
                    '<div class="ui-dialog-header">' +
                    '<div class="ui-dialog-title">{{ title|html }}</div>' +
                    '</div>' +
                    'MS_OPTION_CONTENT' +
                    '<div class="ui-dialog-content">11111</div>' +
                    'MS_OPTION_FOOTER' ;
                },
                "onOpen": function () {
                },
                "onClose": function () {
                    vm.remark = '';
                },
                "submit": function (evt) {
                    var dialog = avalon.getVm(visitinfoDialogId);
                    var pass = true;
                    for(var i=0;i<vm.domArr.length;i++){
                        var flag = util.testing($(vm.domArr.$model[i])[0]);
                        if(!flag){
                            pass = false;
                        }
                    }
                    if(pass){
                        /*console.log(
                            JSON.stringify({
                                "besCallRecordVO" : {
                                    "requirementId": vm.requirementId,  //需求的ID
                                    "merchantId": vm.merchantId,  //商户ID
                                    "beginTime": vm.beginTime,
                                    "endTime": vm.endTime
                                },
                                "sysDocumentVO" : {
                                    "documentType": vm.documentType,
                                    "merchantId": vm.merchantId,
                                    "sysDocumentDtlList":vm.questions.$model
                                },
                                "contact" : {
                                    "contactId": vm.contactId //如果不为空 是变更联系人
                                }
                            })
                        )*/
                        util.c2s({
                            "url": erp.BASE_PATH + 'besRequirement/savePaperAndCallRecord.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify({
                                "besCallRecordVO" : {
                                    "requirementId": vm.requirementId,  //需求的ID
                                    "merchantId": vm.merchantId,  //商户ID
                                    "beginTime": vm.beginTime,
                                    "endTime": vm.endTime,
                                    "remark": vm.remark
                                },
                                "sysDocumentVO" : {
                                    "documentType": vm.documentType,
                                    "merchantId": vm.merchantId,
                                    "sysDocumentDtlList":vm.questions.$model
                                },
                                "contact" : {
                                    "contactId": vm.contactId //如果不为空 是变更联系人
                                }
                            }),
                            "success": function (responseData) {
                                if (responseData.flag == 1) {
                                    dialog.close();
                                    vm.callFn(responseData,{name:vm.logName,mobilePhone:vm.logMobilePhone});
                                }
                            }
                        });
                    }else{
                        if(vm.maxTab != 1){
                            $('.tab1').addClass('warn');
                        }else{
                            $('.tab1').removeClass('warn');
                        }
                        var errorEle = $(dialog.widgetElement).find('.valid-error')[0];
                        var elePosition = $(errorEle).closest('.question-list').position('.content',dialog.widgetElement).top+$('.con-center-main',dialog.widgetElement).scrollTop()-50;
                        $('.con-center-main').scrollTop(elePosition>0?elePosition:0);

                    }
                }
            };


            vm.open = function () {
                vm.maxTab = 1;
                vm.isEnd = 0;
                vm.beginTime = moment()/1;
                vm.hours = 0;
                vm.min = 0;
                vm.seconds = 0;
                time = setInterval(function(){
                    vm.seconds++;
                    if(vm.seconds<10){
                        vm.seconds = '0'+vm.seconds;
                    }
                    if(vm.seconds>=60){
                        vm.min++;
                        if(vm.min<10){
                            vm.min = '0'+vm.min;
                        }
                        vm.seconds = '00';
                    }
                    if(vm.min>=60){
                        vm.hours++;
                        vm.min = 0;
                    }
                },1000);
                var myDate = new Date();
                vm.startTime = myDate.getHours()+':'+myDate.getMinutes();
                setTimeout(function(){vm.setSelect(vm.linkArr,visittype1);},1000);
                avalon.getVm(visitinfoDialogId).open();
                //设置验证dom列表
                for(var i=0;i<vm.questions.length;i++){
                    vm.domArr.push('.question'+i);
                }
                /*设置联动select方法*/
                vm.setSelect = function(arr,name){
                    var indexAt;
                    _.some(arr,function(item,index){
                        if(item['isSelected'] == 1){
                            indexAt = index;
                            return;
                        }
                    });
                    avalon.getVm(name).setOptions(arr,indexAt);
                };
            };
            /*添加通讯录*/
            vm.$addressOpts = {
                "addressId": addressModule,
                "isSearch": false,
                "displayType": 'add',
                "moduleType": 'fes',
                "merchantId" : 0
            };
            vm.addCommunication = function(name){
                var dialog = avalon.getVm(addressModule);
                dialog.readonly = 0;
                dialog.callFn = function(responseData){
                    var data;
                    if (responseData.flag) {
                        data = responseData.data;
                        var linkDialogVm = avalon.getVm(name);
                        if(linkDialogVm){
                            var obj = {
                                isSelected: "0",
                                mobilePhone: data['mobilePhone'],
                                text: data['name'],
                                value: data['id']
                            };
                            linkDialogVm.panelVmodel.options.push(obj);
                            linkDialogVm.selectValue(obj.value,true);
                        }
                    }
                };
                dialog.requestData = {
                    merchantId:model.merchantId,
                    id:$(this).attr('data-id')
                };
                dialog.isSearch = false;
                dialog.displayType = 'add';
                dialog.open();
            };
            /*添加通讯录-end*/
            vm.$init = function () {
                elEl.addClass('module-visitinfo');
                elEl.html(tmpl);
                //扫描
                avalon.scan(element, [model].concat(vmodels));
            };
            vm.$remove = function () {
                avalon.getVm(visittype1)&&$(avalon.getVm(visittype1).widgetElement).remove();
                avalon.getVm(visittype2)&&$(avalon.getVm(visittype2).widgetElement).remove();
                avalon.getVm(addressModule)&&$(avalon.getVm(addressModule).widgetElement).remove();
                $(avalon.getVm(visitinfoDialogId).widgetElement).remove();
                elEl.removeClass('module-visitinfo').off().empty();
            };

        });
        return model;
    };
    module.defaults = {
        "requirementId" : 0,//需求的ID
        "merchantId" : 0,//商户ID
        "linkArr" : [],//变更联系人数组
        "logName" : '',//变更联系人姓名
        "logMobilePhone" : '',//变更联系人电话
        "language" : [],
        "questions" : [],
        "documentType" : '',//需求题类型
        "callFn" : avalon.noop
    };
    return avalon;
});