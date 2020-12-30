<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- (로그인, 회원가입 제외) 모든 페이지에 공통으로 적용될 탬플릿 -->
<title>Luxury City Main</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/clc/css/cls.css">
<script type="text/javascript" src="/clc/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/clc/js/option.js"></script>

<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
a:link {
  text-decoration: none;
}
a:visited {
  text-decoration: none;
}

</style>

<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>  Menu</button>
  <a href="/clc/main.clc" class="w3-bar-item w3-right"><i class="fa fa-bus fa-fw" aria-hidden="true"></i>&nbsp;LuxuryCity</a>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>

	<!-- 로그인 했을 경우 -->
<c:if test="${not empty SID}">
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="/clc${AVT.dir}${AVT.afile}" class="w3-circle w3-card-4 w3-margin-right" style="width:46px">
    </div>
    <div class="w3-col s8">
      <span>Welcome, <strong>${SID}</strong></span><br>
      <a href="/clc/member/logout.clc" class="w3-col m9 w3-tiny w3-round w3-button w3-orange w3-text-white" style="margin-top: 5px;">Logout</a>
    </div>
  </div>
</c:if>

	<!-- 로그인 하지 않을 경우 -->

<c:if test="${empty SID}">
  <div class="w3-container w3-row">
    <div class="w3-col w3-center">
      <span>로그인 후 이용해주세요.</span><br>
      <div class="w3-bar w3-center w3-margin-top">
	      <a href="/clc/member/login.clc" class="w3-bar-item w3-button w3-small w3-green w3-round w3-margin-right">Login</a>
	      <a href="/clc/member/join.clc" class="w3-bar-item w3-small w3-button w3-red w3-round">Join</a>
      </div>
    </div>
  </div>
</c:if>


  <hr>
<!-- 메뉴 영역 -->
  <div class="w3-container">
    <h5>Menu</h5>
  </div>
  <div class="w3-bar-block">
    <a href="/clc/main.clc" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
    <a href="/clc/board/board.clc" class="w3-bar-item w3-button w3-padding"><i class="fa fa-pencil fa-fw" aria-hidden="true"></i>  문의게시판</a>
    <a href="" class="w3-bar-item w3-button w3-padding"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i>  지도 검색</a>
 <!-- 마이페이지는 로그인 했을 경우에만 뜨도록 한다 -->
<c:if test="${not empty SID}">
    <div class="w3-dropdown-hover">
	    <div class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw"></i>  마이페이지 <i class="fa fa-caret-down w3-right"></i></div>
    	<div class="w3-dropdown-content w3-bar-block">
    		<a href="/clc/member/mypage.clc" class="w3-bar-item w3-button"><span class="w3-col m11 w3-right"><i class="fa fa-star fa-fw" aria-hidden="true"></i>&nbsp;&nbsp;즐겨찾기</span></a>
    		<a href="/clc/member/myinfo.clc" class="w3-bar-item w3-button"><span class="w3-col m11 w3-right"><i class="fa fa-info-circle fa-fw" aria-hidden="true"></i>&nbsp;&nbsp;회원정보</span></a>
    	</div>
    </div>
</c:if>
  </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">


	<!-- 이 영역에 데이터를 추가하면 됩니다 -->
	<h1 class="w3-col w3-center w3-margin-top w3-text-dark-gray" style="font-size: 42px;"><i class="fa fa-bus" aria-hidden="true"> </i><b> Luxury City</b></h1>
	<div class="w3-container" style="padding: 100px 0;">
		<div class="w3-content mw700 w3-padding-bottom">
			
			<div class="w3-animate-opacity w3-card-4">
			    <header class="w3-container w3-border-bottom w3-blue"> 
			      <span id="closebusmodal" class="w3-button w3-display-topright">&times;</span>
			      <h3>"<b><span class="keyword">${LIST[0].start_nm} -> ${LIST[0].end_nm}</span></b>" 검색 결과</h3>
			    </header>
			    <div class="w3-container" id="busdata">	
			    <c:forEach var="list" items= "${LIST}">
			 		<div class="w3-col w3-white w3-margin-bottom w3-hover-pale-green w3-border-bottom busdatalist" id="'+ item.route_id +'" style="cursor: pointer;">
						<div class="w3-col w3-padding">

							<div class="w3-col m3 w3-border-blue w3-text-grey w3-center" >&nbsp;</div>
							<c:if test="${empty list.route_nm}">
								<div class="w3-col m3 w3-border-blue w3-text-grey w3-center"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_list[0]}</div>
								<div class="w3-col m3 w3-border-blue w3-text-grey w3-center"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_list[1]}</div>
								<div class="w3-col m3 w3-border-blue w3-text-blue w3-center">&nbsp;</div>
								<div class="w3-col m2 w3-border-blue w3-text-blue w3-center" style="height:22px"> <i class="fa fa-dot-circle-o w3-right" aria-hidden="true"></i></div>
								<div class="w3-col m4  w3-text-blue w3-center"><div class="w3-border-bottom w3-border-blue w3-col m11" style="height:9px">&nbsp;</div><div class="w3-border-top w3-border-blue w3-col m11" style="height:13px">&nbsp;</div><div><i class="fa fa-arrow-circle-o-right w3-rightw3-col m1" aria-hidden="true"></i></div></div>
								<div class="w3-col m4  w3-text-blue w3-center"><div class="w3-border-bottom w3-border-blue" style="height:9px">&nbsp;</div><div class="w3-border-top w3-border-blue" style="height:13px">&nbsp;</div></div>
								<div class="w3-col m2 w3-border-blue w3-text-blue w3-center" style="height:22px"> <i class="fa fa-dot-circle-o  w3-left" aria-hidden="true"></i></div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue w3-text-gray w3-center" style="height:46px"><b>${list.start_nm}</b> </div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue  w3-text-gray w3-center" style="height:46px"><b>${empty list.transfer_nm ? '없음' : list.transfer_nm}</b></div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue w3-text-gray w3-center" style="height:46px"><b>${list.end_nm}</b></div>
							</c:if>
							<c:if test="${!empty list.route_nm}">
								<div class="w3-col m6 w3-border-blue w3-text-grey w3-center"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_nm}</div>
								<div class="w3-col m3 w3-border-blue w3-text-blue w3-center">&nbsp;</div>
								<div class="w3-col m2 w3-border-blue w3-text-blue w3-center" style="height:22px"> <i class="fa fa-dot-circle-o w3-right" aria-hidden="true"></i></div>
								<div class="w3-col m8  w3-text-blue w3-center"><div class="w3-border-bottom w3-border-blue" style="height:9px">&nbsp;</div><div class="w3-border-top w3-border-blue" style="height:13px">&nbsp;</div></div>
								<div class="w3-col m2 w3-border-blue w3-text-blue w3-center" style="height:22px"> <i class="fa fa-dot-circle-o  w3-left" aria-hidden="true"></i></div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue w3-text-gray w3-center" style="height:46px"><b>${list.start_nm}</b> </div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue  w3-text-gray w3-center" style="height:46px">&nbsp;</div>
								<div class="w3-col m4 w3-border-bottom w3-border-blue w3-text-gray w3-center" style="height:46px"><b>${list.end_nm}</b></div>
							</c:if>
							<div class="w3-col" style="padding-top: 5px;">	
								<div class="w3-col">
								<c:if test="${empty list.transfer_nm }">
									<div class="w3-col m3 w3-border-right w3-border-blue" style="font-size: 20px;"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_nm}</div>			
									<div class="w3-col m9 w3-padding">
										<div class="w3-col"><b>${list.start_nm}<i class="fa fa-arrows-h" aria-hidden="true"></i> ${list.end_nm}</b> <span class="w3-right">이동 정거장 수 : [${list.str_order_cnt}]</span></div>				
									</div>					
								</c:if>								
								<c:if test="${!empty list.transfer_nm }">
									<div class="w3-col m3 w3-border-right w3-border-blue" style="font-size: 20px;"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_list[0]}</div>			
									<div class="w3-col m9 w3-padding">
										<div class="w3-col"><b>${list.start_nm}<i class="fa fa-arrows-h" aria-hidden="true"></i> ${list.transfer_nm}</b></div>				
									</div>					
									<div class="w3-col m3 w3-border-right w3-border-blue" style="font-size: 20px;"><i class="fa fa-bus w3-text-green" aria-hidden="true"></i>${list.route_list[1]}</div>			
									<div class="w3-col m9 w3-padding">
										<div class="w3-col"><b>${list.transfer_nm}<i class="fa fa-arrows-h" aria-hidden="true"></i> ${list.end_nm}</b><span class="w3-right">이동 정거장 수 : [${list.str_order_cnt - 2}]</span></div>				
									</div>					
								</c:if>								
								</div>					
							</div>					
						</div>					
					</div>
				</c:forEach>
					
			    </div>
			    <footer class="w3-container w3-center">
			      	<!-- 페이징 버튼 -->
					<div class="w3-col w3-margin-top">
						<div class="w3-bar w3-border w3-round">
							<!-- 이전 버튼 처리 -->
						  		<span class="w3-bar-item w3-button pagebtn" style="display: none;">&laquo;</span>
						  
						  
							<!-- 다음 버튼 처리 -->
						  		<span class="w3-bar-item w3-button pagebtn" style="display: none;">&raquo;</span>
						</div>
					</div>
			    </footer>
			  </div>
		</div>
	</div>
	

	<!-- 버스 데이터 전송 담당 태그 -->
	<form  method="post" id="routefrm">
		<input type="hidden" name="route_id" id="routeid">
	</form>



  <div class="w3-container w3-dark-grey w3-padding-32">
    <div class="w3-row">
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-green">Region</h5>
        <p>Seoul</p>
        <p>Gyeonggi</p>
        <p>Incheon</p>
      </div>
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-red">Information Provider</h5>
        <p>TOPIS</p>
        <p>Gbis</p>
        <p>More</p>
      </div>
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-orange">Support</h5>
        <p>Increpas</p>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <p>Developed by <b>Silicon Valley</b></p>
  </footer>

  <!-- End page content -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");
// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");
// Toggle between showing and hiding the sidebar, and add overlay effect

</script>

</body>
</html>
