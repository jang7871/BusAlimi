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
<script type="text/javascript" src="/clc/js/friend.js"></script>

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
      <div class="w3-bar-item w3-button"><i class="fa fa-envelope"></i></div>
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

<!-- 친구 목록 -->
<div class="w3-container">
    <h5>Friend List</h5>
<c:forEach var="friend" items="${FRIEND}">    
    <ul class="w3-ul w3-card-4 w3-white">
      <li class="w3-padding-16">
        <img src="/clc/avatar/${friend.afile}" class="w3-left w3-circle w3-margin-right" style="width:35px">
        <span class="w3-xlarge">${friend.name} &nbsp;&nbsp;</span>
        <span class="w3-xlarge">${friend.id}</span>
        <span class="w3-button msg" style="float: right;"><i class="fa fa-envelope"></i></span><br>
      </li>
    </ul>
</c:forEach>
</div>
  <hr>
  
<!-- 메세지 보내는 모달창 -->
<div id="msgmodal" class="w3-modal">
	<div class="w3-modal-content w3-card-8">
		<header class="w3-container w3-orange">
			<span id="msgclose" class="w3-button w3-display-topright">&times;</span>
			<label class="w3-button">receiver : &nbsp;<span id="receiverId"></span></label>
		</header>
		<div class="w3-container">
			<textarea id="message" class="w3-input w3-border" style="resize: none; height: 200px;"></textarea>
		</div>
		<footer class="w3-container">
			<p>&nbsp;</p>
			<h3><span class="w3-card-4 w3-orange w3-button w3-display-bottomright" id="send">send</span></h3>
		</footer>
	</div>
</div>
  
<!-- 받은 메세지 -->
<div class="w3-container">
    <h5>Recent Comments</h5>
<c:if test="${PAGE.endCont-1 > 0 }">
<c:forEach var="message" items="${MESSAGE}" begin="${PAGE.startCont-1}" end="${PAGE.endCont-1}">    
    <div class="w3-row">
      <div class="w3-col m2 text-center">
        <img class="w3-circle" src="/clc/avatar/${message.afile}" style="width:96px;height:96px">
      </div>
      <div class="w3-col m10 w3-container">
<c:if test="${message.msgcheck eq 'N'}">
	  	<div class="w3-button w3-right w3-round">
	  		<i class="fa fa-comment" aria-hidden="true">&nbsp;</i>
	  		<input type="hidden" value="${message.msgno}">
	  	</div>
</c:if>
<c:if test="${message.msgcheck eq 'Y'}">	  
	  	<div class="w3-right w3-round">
	  		<i class="fa fa-comment-o" aria-hidden="true">&nbsp;</i>
	  	</div>
</c:if>      
       	<h4>${message.name} <span class="w3-opacity w3-medium" id="date">${message.adate}</span></h4>
       	<p>
       		<span id="${message.msgno}">${message.message}</span>
       	</p>
       	<div class="w3-right w3-round" id="dbtn">
	  		<i class="w3-button fa fa-trash-o" aria-hidden="true">&nbsp;</i>
	  	</div>       	
       	<br>      
      </div>
    </div>
</c:forEach>
</c:if>
</div>
  <br>

<!-- 친구 수락 모달창 -->
<c:if test="${not empty msgVO}">
<div id="respmodal" class="w3-modal" style="display: block;">
	<div class="w3-modal-content w3-card-8">
		<header class="w3-container w3-orange">
			<span id="respclose" class="w3-button w3-display-topright">&times;</span>
			<label class="w3-button">sender : &nbsp;<span id="senderId">${msgVO.id}</span></label>
		</header>
		<div class="w3-container">
			<div id="resp" class="w3-input w3-border" style="resize: none; height: 200px;">${msgVO.message}</div>
		</div>
		<footer class="w3-container">
			<p>&nbsp;</p>
			<h3><span class="w3-card-4 w3-orange w3-button w3-display-bottomright" id="ok">OK</span></h3>
		</footer>
	</div>
</div>
	<form id="reqok" method="post">
		<input type="hidden" name="id" value="${msgVO.id}">
		<input type="hidden" name="frid" value="${msgVO.reid}">
	</form>
</c:if>
  
<!-- 페이징 처리 -->
<div class="w3-bar w3-center">
	<form method="post" name="pfrm" id="pfrm">
		<input type="hidden" name="nowPage" id="nowPage">
	</form>
				
<!-- 이전버튼 -->
<c:if test="${PAGE.startPage != 1}">
	<span class="w3-button pbtn" id="${PAGE.startPage-1}">&laquo;</span>
</c:if>	
<c:if test="${PAGE.startPage == 1}">
	<span class="pbtn" id="${PAGE.startPage-1}">&laquo;</span>
</c:if>
			
<!-- 기능이 완성되면 주석을 풀어주세요. -->
<c:forEach var="page" begin="${PAGE.startPage}" end="${PAGE.endPage}">
	<span class="w3-button pbtn">${page}</span>
</c:forEach>
			
<!-- 다음버튼 -->
<c:if test="${PAGE.endPage == PAGE.totalPage}">
	<span class="pbtn" id="${PAGE.endPage+1}">&raquo;</span>
</c:if> 
<c:if test="${PAGE.endPage != PAGE.totalPage}">
	<span class="w3-button pbtn" id="${PAGE.endPage+1}">&raquo;</span>
</c:if> 
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
