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
<script type="text/javascript" src="/clc/js/main.js"></script>

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
      <a href="/clc/member/myinfo.clc" class="w3-bar-item">
      	<img src="/clc${AVT.dir}${AVT.afile}" class="w3-circle w3-card-4 w3-margin-right" style="width:46px">
      </a>
    </div>
    <div class="w3-col s8">
      <span>Welcome, <strong id="sid">${SID}</strong></span><br>
      <a href="/clc/member/logout.clc" class="w3-col m9 w3-tiny w3-round w3-button w3-orange w3-text-white" style="margin-top: 5px;">Logout</a>
      <a href="/clc/member/friendlist.clc" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i></a>
      <div class="w3-bar-item w3-button" id="searchfriend"><i class="fa fa-user"></i></div>      
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
    <a href="/clc/search/mapSearch.clc" class="w3-bar-item w3-button w3-padding"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i>  지도 검색</a>
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
	<div class="w3-container" style="padding: 100px 0;">
		<div class="w3-content mw700 w3-padding-bottom">
			<h1 class="w3-col w3-center w3-margin-top w3-text-dark-gray" style="font-size: 42px;"><i class="fa fa-bus" aria-hidden="true"> </i><b> Luxury City</b>
				<span class="w3-cell w3-button w3-green w3-hover-grey w3-right" style="font-size: 12px; width: 100px; border-radius: 5px 5px; padding: 10px;" id="searchroute">길찾기 <i class="fa fa-search" aria-hidden="true" style="font-size: 1em;"></i></span>
			</h1>
			
			<div class="w3-col w3-padding">
				
<!-- 				<form method="POST" action=""> -->
			
				<!-- 버스 검책창 -->
					<div class="w3-col" id="bus" style="position: relative;">
						<div class="w3-cell-row">
							<input type="text" placeholder="버스번호를 입력하세요." id="bsearch" style="outline: 0; border-bottom: 0px!important; border-left: 5px solid #2196f3; border-radius: 5px 0 0 5px; padding: 16px; font-size: 1.2857em;" class="w3-cell w3-input" autocomplete="off">
							<span class="w3-cell w3-button w3-blue w3-hover-blue" style="width: 64px; border-radius: 0 5px 5px 0; padding: 16px;" id="srcroute"><i class="fa fa-search" aria-hidden="true" style="font-size: 2em;"></i></span>
						</div>
						<div id="blist" class="w3-card-2 w3-white" style="display: none; z-index: 2; position: absolute; left: 0; right: 0; font-size: 1.1em;">
					
						</div>
					</div>
					
					<!-- 정류소 검책창 -->
					<div class="w3-col w3-hide" id="busstop" style="position: relative;">
						<div class="w3-cell-row">
							<input type="text" placeholder="정류소를 입력하세요." id="bssearch" style="outline: 0; borde-bottomr: 0px!important; border-left: 5px solid #9c27b0; border-radius: 5px 0 0 5px; padding: 16px; font-size: 1.2857em;" class="w3-cell w3-input" autocomplete="off">
							<span class="w3-cell w3-button w3-purple w3-hover-purple" style="width: 64px; border-radius: 0 5px 5px 0; padding: 16px;" id="srcstation"><i class="fa fa-search" aria-hidden="true" style="font-size: 2em;"></i></span>
						</div>
						<div id="stalist" class="w3-card-2 w3-white" style="display: none; z-index: 2; position: absolute; left: 0; right: 0; font-size: 1.1em;"></div>
					</div>
					
		
<!-- 				</form> -->
				
				<div class="w3-col w3-margin-top" style="font-size: 1.2857em;">
					<span class="w3-button w3-blue w3-border-bottom w3-border-right w3-hover-blue w3-half" style="border-radius: 5px 0 0 5px;" id="busbtn">버스</span>
					<span class="w3-button w3-border-gray w3-hover-purple w3-border-bottom w3-half" style="border-radius: 0 5px 5px 0;" id="bsbtn">정류소</span>
				</div>
			</div>
		</div>
	</div>
	

	<!-- 버스 데이터 전송 담당 태그 -->
	<form  method="post" id="routefrm">
		<input type="hidden" name="route_id" id="routeid">
		<input type="hidden" name="district_cd" id="district_cd">
	</form>
	
	<!-- 정류소 데이터 전송 담당 태그 -->
	<form method="post" id="stationfrm">
		<input type="hidden" name="station_id" id="stationid">
		<input type="hidden" name="district_cd" id="districtcd">
<!-- 		<input type="hidden" name="station_nm" id="stationnm"> -->
<!-- 		<input type="hidden" name="loc_x" id="x"> -->
<!-- 		<input type="hidden" name="loc_y" id="y"> -->
<!-- 		<input type="hidden" name="mobile_no" id="mobile"> -->
<!-- 		<input type="hidden" name="region" id="region"> -->
	</form>
	<form method="post" id="searchroutefrm">

		<input type="hidden" name="start_nm" id="startnm">
		<input type="hidden" name="end_nm" id="endnm">

	</form>




	<!-- 버스검색 모달창 -->
	<div id="busmodal" class="w3-modal">
	<span id="nowPage" class="w3-hide"></span>
	  <div class="w3-modal-content w3-animate-opacity w3-card-4">
	    <header class="w3-container w3-border-bottom w3-blue"> 
	      <span id="closebusmodal" class="w3-button w3-display-topright">&times;</span>
	      <h3>"<b><span class="keyword"></span></b>" 검색 결과</h3>
	    </header>
	    <div class="w3-container" id="busdata">	
	 

			
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
 	
 	
 	<!-- 정류소 검색 모달창 -->
	<div id="stamodal" class="w3-modal" style="position:absolute; z-index:12;">
	  <div class="w3-modal-content w3-animate-opacity w3-card-4">
	    <header class="w3-container w3-border-bottom w3-purple"> 
	      <span id="closestamodal" class="w3-button w3-display-topright">&times;</span>
	      <h3>"<b><span class="keyword"></span></b>" 검색 결과</h3>
	    </header>
	    <div class="w3-container" id="stadata">	
	    
			<div class="w3-col w3-white w3-margin-bottom w3-hover-pale-yellow w3-border-bottom" id="${slist.bmno}" style="cursor: pointer;">
				<div class="w3-col w3-padding">
					<div class="w3-col w3-border-bottom w3-border-blue w3-text-gray">경기</div>
					<div class="w3-col" style="padding-top: 5px;">	
						<div class="w3-col">
							<div class="w3-col m4 w3-border-right w3-border-blue" style="font-size: 40px;"><i class="fa fa-map-pin" aria-hidden="true"></i>11111</div>
							<div class="w3-col m8 w3-padding">
								<div class="w3-col w3-small" style="visibility: hidden;"><b>　　　　　　</b></div>
								<div class="w3-col"><b>지하철2호선강남역</b></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
	    </div>
	    <footer class="w3-container w3-center">
	      	<!-- 페이징 버튼 -->
			<div class="w3-col w3-margin-top">
				<div class="w3-bar w3-border w3-round">
					<!-- 이전 버튼 처리 -->
				  		<span class="w3-bar-item w3-button pagebtn2" style="display: none;">&laquo;</span>
				  
				  
					<!-- 다음 버튼 처리 -->
				  		<span class="w3-bar-item w3-button pagebtn2" style="display: none;">&raquo;</span>
				</div>
			</div>
	    </footer>
	  </div>
	</div>
	

	<!--  길찾기 모달  -->
	
	<div id="sroutemodal" class="w3-modal" style="position:absolute; z-index:10;">
	  <div class="w3-modal-content w3-animate-opacity w3-card-4">
	    <header class="w3-container w3-border-bottom w3-blue"> 
	      <span id="closesroutemodal" class="w3-button w3-display-topright">&times;</span>
	      <h3>"<b><span class="keyword"></span></b>길찾기</h3>
	    </header>
	    <div class="w3-container" id="routedata">	
	    	<div class="w3-cell-row w3-margin-top w3-margin-bottom">
				<input type="text" placeholder="출발 정류소를 입력하세요." id="startroute" style="outline: 0; borde-bottomr: 0px!important; border-left: 5px solid #9c27b0; border-radius: 5px 0 0 5px; padding: 16px; font-size: 1.2857em;" class="w3-cell w3-input" autocomplete="off">
			</div>
			<div id="startlist" class="w3-card-2 w3-white" style="display: none; z-index: 2; position: absolute; left: 0; right: 0; font-size: 1.1em;"></div>
	    	
	    	<div class="w3-cell-row w3-margin-top w3-margin-bottom">
				<input type="text" placeholder="도착 정류소를 입력하세요." id="endroute" style="outline: 0; borde-bottomr: 0px!important; border-left: 5px solid #9c27b0; border-radius: 5px 0 0 5px; padding: 16px; font-size: 1.2857em;" class="w3-cell w3-input" autocomplete="off">
			</div>
			<div id="endlist" class="w3-card-2 w3-white" style="display: none; z-index: 2; position: absolute; left: 0; right: 0; font-size: 1.1em;"></div>
			<div class="w3-button w3-cell-row w3-margin-top w3-padding w3-green searchrouteoption"><b>검색</b></div>
	    </div>
	  </div>
	</div>
	
 	<!-- 친구 검색 모달창 -->
	<div id="frimodal" class="w3-modal" style="position:absolute; z-index:12;">
	  <div class="w3-modal-content w3-animate-opacity w3-card-4">
	    <header class="w3-container w3-border-bottom w3-orange"> 
	      <span id="closefrimodal" class="w3-button w3-display-topright">&times;</span>
	      <h3>친구검색</h3>
	    </header>
	   
	    <div class="w3-container" id="frienddata">	
	    	<div class="w3-cell-row w3-margin-top w3-margin-bottom">
				<input type="text" placeholder="이름을 입력하세요." id="friendname" style="outline: 0; borde-bottomr: 0px!important; border-left: 5px solid orange; border-radius: 5px 0 0 5px; padding: 16px; font-size: 1.2857em;" class="w3-cell w3-input" autocomplete="off">
			</div>
			<div id="friendlist" class="w3-card-2 w3-white" style="display: none; z-index: 2; position: absolute; left: 0; right: 0; font-size: 1.1em;"></div>
			<div class="w3-button w3-cell-row w3-margin-top w3-padding w3-orange"><b>검색</b></div>
	    </div>
			
	    </div>
	  </div>
	</div>
  <hr>
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


<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");
// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");
// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
	$('#busmodal').slideUp(300);
	$('#stamodal').slideUp(300);
	$('#nowPage').text('');
	$('#busdata').html('');
	$('#stadata').html('');
	$('nav').css('top', '');
	$('nav').css('bottom', '');
	$('html, body').css('overflow', ''); 
	//scroll hidden 해제 
	$('html, body').off('scroll touchmove mousewheel'); // 터치무브 및 마우스휠 스크롤 가능
	if (mySidebar.style.display === 'block') {
	  mySidebar.style.display = 'none';
	  overlayBg.style.display = "none";
	} else {
	  mySidebar.style.display = 'block';
	  overlayBg.style.display = "block";
	}
}
// Close the sidebar with the close button
function w3_close() {
 	mySidebar.style.display = "none";
  	overlayBg.style.display = "none";
}
</script>

</body>
</html>
