<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<title>BusDetail</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/clc/css/cls.css">
<script type="text/javascript" src="/clc/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/clc/js/search/routedetail.js"></script>
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
a:link {
  text-decoration: none;
}
a:visited {
  text-decoration: none;
}
#routeNm{
	border-radius: 10px;
	height: 50px;
	border: 0px solid white!important;
	margin-bottom: 10px;
}
.btnword{
	background-color: white;
	height: 50px;
	color: lightgrey;
	padding-top: 15px;
	border-radius: 10px;
}
.btnword1{
	background-color: white;
	color: dimgray;
	height: 45px;
	padding-top: 10px;
	border-radius: 10px;
}
.btndi{
	border-radius: 9px;
	
}
.text{
	font-size: 10pt;
}
.icon{
	position: absolute;
	z-index: -1;
}
#scrollTopBtn {
	z-index: 1;
	position: fixed;
	bottom: 15px;
	right: 15px;
	width: 50px;
	height: 50px;
	display: none;
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
      	<img src="/clc${AVT.dir}${AVT.afile}" class="w3-circle w3-margin-right" style="width:46px">
      </a>
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
    <a href="/clc/search/busdetail.clc" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
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
  
  
  <hr>
<!-- 데이터가 넘어가는 영역 -->
<form method="post" id="frm">
	<input type="hidden" name="routeid" id="routeid" value ="${param.route_id}">

	<input type="hidden" name="stationid" id="stationid" value ="${param.station_id}">
	<input type="hidden" name="district_cd" id="district_cd" value ="${param.district_cd}">

	<input type="hidden" name="id" id="id" value ="${SID}">
<!-- 
	<input type="hidden" name="routenm" id="routenm">
	<input type="hidden" name="routestnm" id="routestnm">
	<input type="hidden" name="routeednm" id="routeednm">
	<input type="hidden" name="routepk" id="routepk">
 -->
</form>

	<!-- 스크롤탑 버튼 -->
	<div id="scrollTopBtn" class="w3-circle w3-white w3-card-4 w3-button w3-display-container"><i class="fa fa-chevron-up w3-display-middle" aria-hidden="true"></i></div>
	
	<!-- 이 영역에 데이터를 추가하면 됩니다 -->
	<div class="w3-container">

		<div class="w3-content w3-center mw800">
			
		<!-- 
			<div class="w3-col m10">
				<div class="w3-co1 m1 w3-left w3-margin-right"><h5>버스번호 : </h5></div>
				<div class="w3-col m3 w3-left" id="${INFO.route_id}"><h5>&nbsp;${INFO.route_nm}</h5></div>
				<div class="w3-col m7 w3-left"><h5>[${INFO.st_sta_nm}, ${INFO.ed_sta_nm}]방향</h5></div>
			</div>
			<div class="w3-rest w3-left w3-btn btnword" id="add">추가</div>
			<div class="w3-col">
				<div class="w3-col m10">
					<div class="w3-col m5 w3-left w3-btn w3-margin-left w3-margin-bottom w3-green btndi">정방향</div>
					<div class="w3-col m5 w3-right w3-btn w3-margin-right w3-red btndi">역방향</div>
				</div>
				<div class="w3-col m1 w3-left btnword1">${PEEK}~${NPEEK}분</div>
			</div>
		 -->
		 	<!-- 버스 정보 영역 -->
			<div class="w3-col w3-padding-24 w3-card-2" id="busInfo">
				<div class="w3-col">${ROUTE[0].route_tp}</div>
				<div class="w3-col" style="font-size: 4em;">${ROUTE[0].route_nm}</div>
				<div class="w3-col" style="opacity: 0.8;">${ROUTE[0].st_sta_nm} <i class="fa fa-arrows-h" aria-hidden="true"></i> ${ROUTE[0].ed_sta_nm}</div>
				<div class="w3-col w3-margin-top">
					<div id="infoBtn" class="w3-button w3-round-xxlarge w3-border w3-border-white w3-hover-light-gray w3-margin-right"><i class="fa fa-info-circle" aria-hidden="true"></i> 정보</div>
					
	<c:if test="${not empty SID}">
			<c:if test="${empty BOOKMARK}">			
					<div class="w3-button w3-round-xxlarge w3-border w3-border-white w3-hover-light-gray addBtn"><i class="fa fa-star-o" aria-hidden="true"></i> 추가</div>				
			</c:if>

			<c:if test="${not empty BOOKMARK}">		
					<div id="${BOOKMARK[0].bmno}" class="w3-button w3-round-xxlarge w3-border w3-border-white w3-hover-light-gray delBtn"><i class="fa fa-star" aria-hidden="true"></i> 삭제</div>				
			</c:if>
	</c:if>
	
	<c:if test="${empty SID}">
					<div class="w3-button w3-round-xxlarge w3-border w3-border-white w3-hover-light-gray addBtn"><i class="fa fa-star-o" aria-hidden="true"></i> 추가</div>	
	</c:if>	
				</div>
			</div>

			<div class="w3-col w3-padding w3-white w3-card-2">
	<%--
		<c:forEach var="route" items="${ROUTE}">
		<c:if test="${route.direction eq '정'}">
				<div class="w3-col h50">
		<!-- 정방향 -->
					<div class="w3-col m2 text icon">
						<i class="fa fa-circle-o-notch fa-4x w3-green" aria-hidden="true"></i>
					</div> 
					<div class="w3-col m2 text w3-margin-top" style="padding-left: 12px;">
						<b>${route.region}</b>
					</div> 
					<div class="w3-col m4 text w3-margin-top"><b>${route.station_nm}</b></div>
					<div class="w3-col m2 text w3-margin-top">${route.mobile_no}</div>
					<div class="w3-col m2 text w3-margin-top">${route.str_order}</div>
				</div>
			</c:if>
	 --%>
	
	<%--
			<c:if test="${route.direction eq '역'}">
				<div class="w3-col h50">
		<!-- 역방향 -->
					<div class="w3-col m2 text icon">
						<i class="fa fa-circle-o-notch fa-4x w3-red" aria-hidden="true"></i>
					</div> 
					<div class="w3-col m2 text w3-margin-top" style="padding-left: 12px;">
						<b>${route.region}</b>
					</div> 
					<div class="w3-col m4 text w3-margin-top"><b>${route.station_nm}</b></div>
					<div class="w3-col m2 text w3-margin-top">${route.mobile_no}</div>
					<div class="w3-col m2 text w3-margin-top">${route.str_order}</div>
				</div>
			</c:if>
			</c:forEach>
	 --%>			
				<div class="w3-col">
					<!-- 정방향, 역방향 버튼 -->
			<c:if test="${ROUTE[0].ed_sta_nm != ROUTE[0].st_sta_nm }">
					<div class="w3-col w3-border w3-margin-bottom" id="diBtn">
						<div id="sBtn" class="w3-button w3-half w3-border-right w3-padding" style="width: 50%; font-size: 1em;">${ROUTE[0].ed_sta_nm} 방향</div>
						<div id="rBtn" class="w3-button w3-half w3-padding" style="width: 50%; font-size: 1em;">${ROUTE[0].st_sta_nm} 방향</div>
					</div>
			</c:if>		
					
					<!-- 경로 영역 -->
		<c:forEach var="route" items="${ROUTE}" varStatus = "st">
			
			<c:if test="${route.direction eq '정'}">
					<!-- 정방향 -->
					<div class="w3-col w3-hover-light-gray w3-button " style="padding: 0 16px;">
						<div class="w3-col m11 w3-right w3-cell-row">
							<div class="w3-cell w3-display-container w3-teal" style="width: 14.400px;">
					<c:set var="no" value="${0}" />
								<c:forEach var="loc" items="${ARRIVE}">
									<c:if test= "${st.count+1 eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: green;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
									<c:if test= "${route.station_id eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: green;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
								</c:forEach>
								<c:if test="${no eq 0}">
									<i class="fa fa-chevron-circle-down w3-display-middle"></i>
								</c:if>
							</div>
							<div class="w3-container w3-padding w3-cell w3-left-align sDirect routeBtn" id="${route.station_id}">
					<c:if test="${fn:length(route.station_nm) >= 20}">
							<div class="w3-col"><b>${route.station_nm.substring(0, 20)}...</b></div>
					</c:if>
					<c:if test="${fn:length(route.station_nm) < 20}">
							<div class="w3-col"><b>${route.station_nm}</b></div>
					</c:if>
						<c:if test="${not empty route.mobile_no}">
								<div class="w3-col w3-text-gray">${route.mobile_no}</div>
						</c:if>
						<c:if test="${route.mobile_no eq ' ' or route.mobile_no eq null}">
								<div class="w3-col w3-text-gray">고유번호없음</div>
						</c:if>
							</div>
							<!-- 버스 + 정류소 즐겨찾기 추가/삭제 버튼 -->
							<div class="w3-cell w3-display-container" style="width: 40px;" id="bm${route.station_id}">	
									<i class="w3-text-gray w3-display-middle w3-col fa fa-star-o fa-2x addBtn" aria-hidden="true" style="cursor: pointer;"></i>
									<i class="w3-text-amber w3-display-middle w3-col fa fa-star fa-2x delBtn w3-hide" aria-hidden="true" style="cursor: pointer;"></i>
							</div>
						</div>
					</div>
			</c:if>
			
			
			<c:if test="${route.direction eq '역'}">
					<!-- 역방향 -->
					<div class="w3-col w3-hover-light-gray w3-button" style="padding: 0 16px;">
						<div class="w3-col m11 w3-right w3-cell-row">
							<div class="w3-cell w3-display-container w3-red" style="width: 14.400px;">
								<c:set var="no" value="${0}" />
								<c:forEach var="loc" items="${ARRIVE}">
									<c:if test= "${st.count+1 eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: red;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
									<c:if test= "${route.station_id eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: red;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
								</c:forEach>
								<c:if test="${no eq 0}">
									<i class="fa fa-chevron-circle-down w3-display-middle"></i>
								</c:if>
							</div>
							<div class="w3-container w3-padding w3-cell w3-left-align rDirect routeBtn" id="${route.station_id}">

					<c:if test="${fn:length(route.station_nm) >= 20}">
							<div class="w3-col"><b>${route.station_nm.substring(0, 20)}...</b></div>
					</c:if>
					<c:if test="${fn:length(route.station_nm) < 20}"> 
							<div class="w3-col"><b>${route.station_nm}</b></div>
					</c:if>


						
						<c:if test="${not empty route.mobile_no}">
								<div class="w3-col w3-text-gray">${route.mobile_no}</div>
						</c:if>
						<c:if test="${route.mobile_no eq ' ' or route.mobile_no eq null}">
								<div class="w3-col w3-text-gray">고유번호없음</div>
						</c:if>
							</div>
							<!-- 버스 + 정류소 즐겨찾기 추가/삭제 버튼 -->
							<div class="w3-cell w3-display-container" style="width: 40px;" id="bm${route.station_id}">	
									<i class="w3-text-gray w3-display-middle w3-col fa fa-star-o fa-2x addBtn" aria-hidden="true" style="cursor: pointer;"></i>
									<i class="w3-text-amber w3-display-middle w3-col fa fa-star fa-2x delBtn w3-hide" aria-hidden="true" style="cursor: pointer;"></i>
							</div>
						</div>
					</div>
			</c:if>
			
			<c:if test="${route.direction eq '무'}">
					<!-- 방향 데이터 존재하지 않음 -->
					<div class="w3-col w3-hover-light-gray w3-button" style="padding: 0 16px;">
						<div class="w3-col m11 w3-right w3-cell-row">
							<div class="w3-cell w3-display-container w3-blue-gray" style="width: 14.400px;">
								<c:set var="no" value="${0}" />
								<c:forEach var="loc" items="${ARRIVE}">
									<c:if test= "${st.count+1 eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: #0B243B;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
									<c:if test= "${route.station_id eq loc}">
										<c:set var="no" value="${no + 1}" />
										<font size="6em" color="white">
											<div class="w3-display-middle" style="width:40px; height:40px; border-radius:20px 20px; background-color: #0B243B;">
												<i class="fa fa-bus w3-display-middle"></i>	
											</div>
										</font>
									</c:if>
								</c:forEach>
								<c:if test="${no eq 0}">
									<i class="fa fa-chevron-circle-down w3-display-middle"></i>
								</c:if>

							</div>
							<div class="w3-container w3-padding w3-cell w3-left-align routeBtn" id="${route.station_id}">

					<c:if test="${fn:length(route.station_nm) >= 20}">
							<div class="w3-col"><b>${route.station_nm.substring(0, 20)}...</b></div>
					</c:if>
					<c:if test="${fn:length(route.station_nm) < 20}"> 
							<div class="w3-col"><b>${route.station_nm}</b></div>
					</c:if>


						
						<c:if test="${not empty route.mobile_no}">
								<div class="w3-col w3-text-gray">${route.mobile_no}</div>
						</c:if>
						<c:if test="${route.mobile_no eq ' '}">
								<div class="w3-col w3-text-gray">고유번호없음</div>
						</c:if>
							</div>
							<!-- 버스 + 정류소 즐겨찾기 추가/삭제 버튼 -->
							<div class="w3-cell w3-display-container" style="width: 40px;" id="bm${route.station_id}">	
									<i class="w3-text-gray w3-display-middle w3-col fa fa-star-o fa-2x addBtn" aria-hidden="true" style="cursor: pointer;"></i>
									<i class="w3-text-amber w3-display-middle w3-col fa fa-star fa-2x delBtn w3-hide" aria-hidden="true" style="cursor: pointer;"></i>
							</div>
						</div>
					</div>
			</c:if>
		</c:forEach>
				</div>
			</div>	
		</div>
	</div>
	
<!-- 버스정보 모달 영역 -->
  <div id="opeInfo" class="w3-modal">
    <div class="w3-modal-content w3-animate-right w3-card-4">
      <header class="w3-container"> 
        <span onclick="document.getElementById('opeInfo').style.display='none'" 
        class="w3-button w3-display-topright">&times;</span>
        <h1 class="w3-center"><i class="fa fa-bus fa-fw" aria-hidden="true"></i> ${ROUTE[0].route_nm} 정보</h1>
      </header>
      <div class="w3-container w3-padding-large">
        <div class="w3-col w3-border-bottom">
        	<h4><b>운행지역</b></h4>
        	<h4>${ROUTE[0].st_sta_nm} <i class="fa fa-arrows-h" aria-hidden="true"></i> ${ROUTE[0].ed_sta_nm}</h4>
        </div>
        <div class="w3-col w3-border-bottom">
        	<h4><b>운행시간</b></h4>
   <c:if test="${not empty ROUTE[0].up_first_time}">   	
        	<h4>기점 : 평일 ${ROUTE[0].up_first_time} ~ ${ROUTE[0].up_last_time}</h4>
        	<h4>종점 : 평일 ${ROUTE[0].down_first_time} ~ ${ROUTE[0].down_last_time}</h4>
   </c:if>
   <c:if test="${empty ROUTE[0].up_first_time}">   	
        	<h4>정보 없음</h4>
   </c:if>
        </div>
        <div class="w3-col">
        	<h4><b>배차간격</b></h4>
   <c:if test="${ROUTE[0].npeek_alloc ne 0}">
        	<h4>${ROUTE[0].peek_alloc} ~ ${ROUTE[0].npeek_alloc} 분</h4>
   </c:if>
   <c:if test="${ROUTE[0].npeek_alloc eq 0}">
        	<h4>${ROUTE[0].peek_alloc} 분</h4>
   </c:if>
   <c:if test="${ROUTE[0].npeek_alloc eq 0 and ROUTE[0].peek_alloc eq 0}">
        	<h4>정보 없음</h4>
   </c:if>
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