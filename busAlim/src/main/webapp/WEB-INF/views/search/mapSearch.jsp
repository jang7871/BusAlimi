<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- (로그인, 회원가입 제외) 모든 페이지에 공통으로 적용될 탬플릿 -->
<title>지도 검색</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="/clc/css/mapSearch.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="/clc/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/clc/js/search/mapSearch.js"></script>
<!-- 네이버 지도 api 클라이언트 아이디를 본인의 아이디로 바꿔주세요 -->
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=qorwnz8rsn&submodules=panorama,geocoder"></script>
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
a:link {
  text-decoration: none;
}

a:visited {
  text-decoration: none;
}

</style>
<script>
// 로드뷰를 표시하는 함수
function roadView(e) {
	var position = $(e).attr('id');
	var loc_x = position.substring(0, position.indexOf(','));
	var loc_y = position.substring(position.indexOf(',') + 1);
	
    pano = new naver.maps.Panorama("pano", {
        position: new naver.maps.LatLng(loc_y, loc_x),
        pov: {
            pan: -133,
            tilt: 0,
            fov: 100
        }
    });
}

// 정류소 상세보기로 이동하는 버튼 이벤트 처리
function staDetail(e){
	var station_id = $(e).attr('id');
	let sfrm = $(document.createElement('form'));
	$(sfrm).attr('method', 'POST');
	$(sfrm).attr('action', '/clc/search/stationdetail.clc');
	
	let sinput = $(document.createElement('input'));
	$(sinput).attr('type', 'hidden');
	$(sinput).attr('name', 'station_id');
	$(sinput).val(station_id);
	
	$(sfrm).append(sinput);
	$('body').prepend(sfrm);
	
	$(sfrm).submit();
}
</script>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:105">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>  Menu</button>
  <a href="/clc/main.clc" class="w3-bar-item w3-right"><i class="fa fa-bus fa-fw" aria-hidden="true"></i>&nbsp;LuxuryCity</a>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:104;width:300px;" id="mySidebar"><br>

	<!-- 로그인 했을 경우 -->
<c:if test="${not empty SID}">
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="/clc${AVT.dir}${AVT.afile}" class="w3-circle w3-margin-right" style="width:46px">
    </div>
    <div class="w3-col s8">
      <span>Welcome, <strong>${SID}</strong></span><br>
      <a href="#" class="w3-col m9 w3-tiny w3-round w3-button w3-orange w3-text-white" style="margin-top: 5px;">Logout</a>
    </div>
  </div>
</c:if>

	<!-- 로그인 하지 않을 경우 -->

<c:if test="${empty SID}">
  <div class="w3-container w3-row">
    <div class="w3-col w3-center">
      <span>로그인 후 이용해주세요.</span><br>
      <div class="w3-bar w3-center w3-margin-top">
	      <a href="#" class="w3-bar-item w3-button w3-small w3-green w3-round w3-margin-right">Login</a>
	      <a href="#" class="w3-bar-item w3-small w3-button w3-red w3-round">Join</a>
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
    <a href="/clc/main.clc" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
    <a href="/clc/board/board.clc" class="w3-bar-item w3-button w3-padding"><i class="fa fa-pencil fa-fw" aria-hidden="true"></i>  문의게시판</a>
    <a href="/clc/search/mapSearch.clc" class="w3-bar-item w3-button w3-padding w3-blue"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i>  지도 검색</a>
 <!-- 마이페이지는 로그인 했을 경우에만 뜨도록 한다 -->
<c:if test="${not empty SID}">
    <div class="w3-dropdown-hover">
	    <div class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw"></i>  마이페이지 <i class="fa fa-caret-down w3-right"></i></div>
    	<div class="w3-dropdown-content w3-bar-block">
    		<a href="/clc/member/mypage.clc" class="w3-bar-item w3-button"><span class="w3-col m11 w3-right"><i class="fa fa-star fa-fw" aria-hidden="true"></i>&nbsp;&nbsp;즐겨찾기</span></a>
    		<a href="/clc/member/myinfo.clc" class="w3-bar-item w3-button"><span class="w3-col m11 w3-right"><i class="fa fa-info-circle fa-fw" aria-hidden="true"></i>&nbsp;&nbsp;회원정보</span></a>
    	</div>
    </div>
</c:if>
  </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer; z-index: 103;" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">



	<!-- 스크롤탑 버튼 -->
	<div id="scrollTopBtn" class="w3-circle w3-white w3-card-4 w3-button w3-display-container"><i class="fa fa-chevron-up w3-display-middle" aria-hidden="true"></i></div>
	
	<!-- 이 영역에 데이터를 추가하면 됩니다 -->
	<div class="w3-col">
		<!-- 검색창 영역 -->
		<div class="w3-col" style="padding: 5px; position: relative;">
			<div class="w3-cell-row w3-card-2 w3-white">
				<input type="text" id="searchInput" placeholder="버스, 정류소 검색" class="w3-cell w3-input w3-white" autocomplete="off">
				<span class="w3-cell w3-button w3-white w3-hover-white" style="visibility: hidden; width: 64px; border-radius: 0 5px 5px 0;" id="searchBtn"><i class="fa fa-search" aria-hidden="true" style="font-size: 1.6em; color: #7B68EE;"></i></span>
			</div>
			<!-- <div class="w3-card-2 w3-white" id="keyList" style="margin: 0 5px 0 5px; display: none; z-index: 102; position: absolute; left: 0; right: 0; font-size: 1.1em;">
				
			</div>
			 -->
		</div>
		
		<div id="w3-col map_container" tabindex="-1" style="width:100%;height:100vh;">
			<!-- 지도 영역 -->
			<div id="map" tabindex="-1" style="outline: 0;width:100%;height:100%;position:relative;" class="w3-display-container">
				<!-- 중심좌표 기준 주소 표시 영역 -->
				<div class="hAddr w3-display-topmiddle stop-dragging"><span id="centerAddr"></span></div>
				<!-- 현재 내 위치 버튼 -->				
				<div id="currentPos" class="w3-circle w3-display-topleft w3-card-2" title="내 위치"><i class="fa fa-location-arrow fa-2x" aria-hidden="true" style="font-size: 1.3em; line-height: 2.0;"></i></div>
				
				<!-- 내위치 사용할 때 표시될 메세지창 -->
				<div class="w3-round locMsgPan stop-dragging" style="display: none;"></div>
				
				<!-- 검색 패널 영역 -->
				
				<div class="w3-display-middle searchPan" style="display: none; width: 100%; height: 100%;">
				 	<div class="w3-col searchHeader">
						<!-- 현재 위치 출력 및 검색 영역 열고 닫기 버튼 -->
						<div class="w3-col w3-padding w3-cell-row">
						
							<div class="w3-cell w3-text-purple closeBtn" style="cursor: pointer; width: 60px;">
								<i class="fa fa-times fa-2x" aria-hidden="true" style="font-size: 1.4em;"></i><span>&nbsp;<b>닫기</b></span>
							</div>
							
							<div class="w3-cell w3-right" style="font-size: 0.9em;">
								<i class="fa fa-compass fa-fw w3-text-deep-purple" aria-hidden="true"></i><span class="w3-text-deep-purple" style="font-weight: bold;">위치</span> <span class="w3-text-gray" id="centerAddr2"></span>
							</div>
						</div>
						<!-- 정류소, 버스 구분버튼 -->
						<div class="w3-col w3-margin-bottom">
							<div id="sBtn" class="w3-button w3-half w3-border-bottom w3-padding w3-border-deep-purple w3-text-deep-purple w3-hover-deep-purple" style="width: 50%; font-size: 1em;">정류소</div>
							<div id="rBtn" class="w3-button w3-text-gray w3-half w3-border-bottom w3-padding w3-hover-deep-purple" style="width: 50%; font-size: 1em;">버스</div>
						</div>
				 	</div>
					
					<div id="keyList" class="w3-col" style="height: 75%; overflow: auto;"></div>
					
				</div>
			</div>
			<div id="pano" style="outline: 0;width:100%;height:100%;position:absolute;top:0; z-index: 102; display:none;" class="w3-display-container">
				<div id="closeRoadView" class="w3-circle w3-display-topleft w3-card-2" title="닫기"><i class="fa fa-times fa-2x" aria-hidden="true" style="font-size: 1.3em; line-height: 2.0;"></i></div>
			</div>
			
			<div id="routePan" style="outline: 0;width:100%;height:100px;position:absolute;bottom:0; z-index: 101; display:none; cursor: pointer;" class="w3-display-container w3-card-2 w3-padding routePan">
				<div class="w3-col" style="font-size: 2em;"><span class="routeNm"></span><span class="region" style="font-size: 0.7em;"></span>&nbsp;&nbsp;<span style="font-size: 0.5em;" class="roadDistance"></span></div>
				<div class="w3-col"><span class="st_nm"></span> <i class="fa fa-arrows-h" aria-hidden="true"></i> <span class="ed_nm"></span></div>
			</div>
			
		</div>
		
	</div>

  <hr>
  <!-- 
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
   -->

  <!-- Footer -->
  <!-- 
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <p>Developed by <b>Silicon Valley</b></p>
  </footer>
   -->

  <!-- End page content -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
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
