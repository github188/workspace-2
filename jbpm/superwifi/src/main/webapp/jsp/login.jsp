<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/jsp/comm/baseJs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Cache-Control" content="Expires: 36000" />
<meta http-equiv="Content-Type" content="text/html" charset="utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<title>超级wifi</title>
<link rel="stylesheet" href="/jsp/mobile/css/themes/a.min.css" />
<link rel="stylesheet" href="/jsp/mobile/css/themes/jquery.mobile.icons.min.css" />
<link rel="stylesheet" href="/jsp/mobile/css/themes/jquery.mobile.structure-1.4.5.min.css" />
<link href="../css/superwifi.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="div1">
	<!-- <h1 class="yazuoWifi">yazuo.superwifi.com</h1> -->
	<div class="clientPic"><em>商户自定义图片</em> <c:if test="${!empty param.picUrl}"><img src="${param.picUrl}" /></c:if>
	<c:if test="${empty param.picUrl}"><img src="/image/portal.png" /></c:if></div>
	<%-- <c:choose> 
	  <c:when test="${param.rmac=='04:8d:38:58:4a:90'}">
	  	<!-- 黄记煌 -->
	    <div class="clientPic"><em>商户自定义图片</em> <img src="../image/hjhlogo.jpg" /></div>
	  </c:when> 
	  <c:when test="${param.rmac=='04:8d:38:56:50:58'}">   
	  	<!-- 水木锦堂-->
	    <div class="clientPic"><em>商户自定义图片</em> <img src="../image/smjtlogo.jpg" /></div>
	  </c:when> 
	  <c:otherwise>
	    <div class="clientPic"><em>商户自定义图片</em> <img src="../image/yzlogo.png" /></div>
	  </c:otherwise> 
	</c:choose>  --%>
	<h3 class="wifiWelcome">
		<span><i class="wifiIco wifiWelIco"></i> 欢迎使用免费超级WIFI</span>
	</h3>
	
	<div class="loginWifi">
		请输入手机号
		<p class="formItem btnItem">
			<input type="tel" class="wifiTxt phoneNumTxt" id="JS_loginPhoneNum" />
		   <a href="javascript:;" class="sendVerifyCodeBtn" id="JS_sendVerifyBtn">获取验证码</a> 
		   <span class="sendVerifyCodeSpan" id="JS_sendVerifyCodeSpan">获取验证码</span>
		</p>
		
		请输入验证码
		<p class="formItem"><input type="tel" class="wifiTxt verifyCodeTxt" id="JS_verifyCodeTxt" /></p>
		<p id="JS_errorTip">验证码错误，请检查您填写的信息</p>
		<p class="formItem login-xy">
			<input type="checkbox" id="xy-check" checked/><span class="xy-click"> 我已阅读并同意<a class="wifirenzheng" href="javascript:void();">《WIFI认证协议》</a></span>
		</p>
		<a href="javascript:;" class="wifiLoginBtn" id="JS_wifiLoginBtn" style="margin:0;">登  录</a>
		<a href="javascript:;" class="wifiLoginBtnDisabled" id="JS_wifiLoginBtnDisabled"  style="display:none;">登  录</a>
		<!-- <p class="wifiLoginTip"><i class="wifiIco loginTipIco"></i>提示：您每日可上网时长为<em>30</em>分钟</p> -->
	</div>
</div>
<div id="div2" style="display: none;">
<div class="xy-header"><a href="javascript:void();" class="xy-back"></a>WIFI连接认证协议</div>
	<div class="xy-main">
		<h1>请仔细阅读下方的认证协议</h1>
		<div class="xy-cont">
			<p>本协议是到店就餐用户与餐饮行业商户之间就WIFI网络服务产品提供服务等相关事宜所订立的契约，请您仔细阅读本认证协议，您点击“已阅读并同意WIFI认证协议”，并点击“登录”按钮后，本协议即构成对双方有约束力的法律文件。</p>
			<h2>一、	总则</h2>
			<p>1、雅座回头宝服务是由餐饮行业商户（以下简称“商户”）向到店就餐用户（以下简称“用户”）提供的一款WIFI网络服务产品。</br>
2、用户确认在使用本协议之前，已充分阅读、理解并接受本协议的全部内容，在完成连接并开启服务流程时，即同意遵循本协议之所有约定。</br>
3、商户有权随时对本协议及相应的服务规则、内容进行单方面的变更,并以消息推送、网页公告等方式予以公布，无需另行单独通知。用户有义务不时关注并阅读最新版的协议、推送消息及公告，若用户在本协议内容公告变更后继续使用本服务，即视为用户已充分阅读、理解并接受修改后的协议内容，并将遵循修改后的规定；若用户不同意修改的，可以立即停止接受商户根据本协议提供的服务。</br>
4、双方承诺在签订本协议时均为已具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织。</br>
5、用户在通过设备进行网络连接时，应当按照法律规定，提供真实、准确和完整的资料。如用户提供的资料存在错误、虚假、过时或不完整的，商户有权向用户发出要求改正的通知，并有权直接删除相应资料、暂停或终止提供部分或全部服务。由此而产生的任何损失及责任由用户自行承担。
			</p>
			<h2>二、	双方权利义务</h2>
			<p>商户的权利和义务</br>
1、	根据用户选择的服务提供合格的网络技术和信息服务。</br>
2、	为登陆过WIFI的用户，提供一定的会员权益。</br>
3、	用户同意，商户拥有通过短信、电话等形式，向登陆过WIFI的用户发送营销信息、促销活动等告知信息的权利。</br>
4、	对于用户在使用雅座回头宝时提供的个人信息，商户有权在产品的服务范围内使用。</br>
5、	承诺对用户的资料采取保密措施，不向第三方披露，或授权使用前述资料，除非：</br>
（1）依据本协议条款或者与商户签订的其他服务协议、合同、在线条款等规定可以提供的；</br>
（2）依据法律法规的规定应当提供的；</br>
（3）行政、司法等职权部门要求商户提供的；</br>
（4）用户同意商户向第三方提供的；</br>
（5）商户因解决举报事件、提起诉讼而提交的；</br>
（6）商户为防止严重违法行为或涉嫌犯罪行为发生而采取必要合理行动所必须提交的；</br>
（7）商户因向用户提供产品、服务、信息而向第三方提供的，包括商户通过第三方的技术及服务向用户提供产品、服务、信息的情况（但商户需保证第三方应承担与商户同等的保密义务）。
用户的权利和义务</br>
1、	用户有权利享受商户提供的互联网技术和信息服务，并有权利在接受服务时获得商户的技术支持、咨询等服务。</br>
2、	登录WIFI后即成为商户的WIFI会员，享受WIFI会员权益。</br>
3、	用户保证不会利用技术或其他手段破坏或扰乱商户的其他互联网产品。</br>
4、	用户应尊重商户及其他第三方的知识产权和其他合法权利，并保证在发生侵犯上述权益的违法事件时尽力保护商户及其股东、雇员、合作伙伴等免于因该等事件受到影响或损失；商户保留因用户侵犯本公司合法权益时终止提供服务的权利。</br>
5、	因用户向雅座回头宝提供的联络方式有误而导致的一切后果，由用户自行承担责任，包括但不限于因用户未能及时收到雅座回头宝的相关通知而导致的后果和损失。</br>
6、	用户保证在使用本产品服务时，遵从国家、地方法律法规、行业惯例和社会公共道德，不会利用商户提供的服务进行存储、发布、传播如下信息和内容：违反国家法律法规政策的任何内容（信息）；违反国家规定的政治宣传和/或新闻信息；涉及国家秘密和/或安全的信息；封建迷信和/或淫秽、色情、下流的信息或教唆犯罪的信息；博彩有奖、赌博游戏；违反国家民族和宗教政策的信息；防碍互联网运行安全的信息；侵害他人合法权益的信息和/或其他有损于社会秩序、社会治安、公共道德的信息或内容。用户同时承诺不得为他人发布上述不符合国家规定和/或本服务条款约定的信息内容提供任何便利，包括但不限于设置URL、BANNER链接等。商户有权在用户违反上述约定时终止提供服务并不予退还任何款项，因用户上述行为给商户造成损失的，用户应予赔偿。
			</p>
			<h2>三、	责任限制及不承诺担保</h2>
			<p>1、	除非另有明确的书面说明，商户通过雅座回头宝向用户提供的任何信息、内容、服务均由商户自行承担相关的法律责任。</br>
2、	除非另有明确的书面说明，雅座不对商户通过雅座回头宝向用户提供的任何信息、内容、服务做任何形式的、明示或默示的声明或担保（根据中华人民共和国法律另有规定的除外）。</br>
3、	如因不可抗力或其他无法控制的原因导致WIFI网络服务系统崩溃或无法正常使用等情况，商户将尽力协助处理善后事宜。
			</p>
			<h2>四、法律适用与管辖</h2>
			<p>本协议之效力、解释、变更、执行与争议解决均适用中华人民共和国法律。因本协议产生之争议，双方应友好协商解决，协商不成的，任何一方均有权向北京仲裁委员会申请仲裁解决。仲裁裁决是终局的，对双方均具有约束力。</p>
			<h2>五、其他</h2>
			<p>1、	本协议内容中以黑体、加粗、下划线或斜体等方式显著标识的条款，请用户着重阅读。</br>
2、	用户点击本协议下方的“同意”以下协议，提交“按钮”即视为您完全接受本协议，在点击之前请您再次确认已知悉并完全理解本协议的全部内容。
			</p>
		</div>
	</div>
</div>
<script src="/jsp/mobile/script/jquery1.11.js"></script>
<script src="/jsp/mobile/script/jquery.mobile-1.4.5.min.js"></script>
<script src="../js/login.js"></script>
<script type="text/javascript">
var deviceMac='${param.rmac}';
var userMac='${param.pcmac}';
var deviceSSID='${param.ssid}';
var isPassWordCheck=${param.isPassWordCheck};
</script>
</body>
</html>
