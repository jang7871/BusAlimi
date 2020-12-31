var colors = ['w3-text-red', 'w3-text-green', 'w3-text-blue', 'w3-text-pink', 'w3-text-indigo', 'w3-text-purple', 'w3-text-amber', 'w3-text-lime', 'w3-text-yellow'];
/*
	모달창 이벤트를 처리하는 함수 정의
*/
// 정류소 검색 이벤트
var staClick = function() {
	// 0. 모달창의 데이터(이전 검색 기록)과 페이징 버튼 세팅을 초기화한다.
	$('#stadata').html('');
	$('.pagenum2').detach();
	$('#stamodal footer').children().children().children().eq(0).css('display', 'none');
	$('#stamodal footer').children().children().children().last().css('display', 'none');
	// 1. 검색창에 있는 값을 변수에 담는다.
    var station = $('#bssearch').val();
/*
    var nowPage = $('#nowPage').text();
	alert($(this).attr('id'));
	if(nowPage == ''){ 
		nowPage = 1;
	}
*/
	var nowPage = $(this).attr('id');
//	alert(nowPage);
	if(nowPage == '' || nowPage == 'srcstation' || nowPage == undefined) {
		nowPage = 1;
	}
	// 2. 검색 창의 값이 없으면 이벤트를 작동하지 않게 한다.
	if(station == '') {
		alert('검색어를 입력하세요.');
        return;
    } else {
		// 3. 값이 있다면 json 형식으로 만들어준다.
		var data = {name: station, nowPage: nowPage};
		
		// 4. ajax 처리한다.
		$.ajax({
   		url: '/clc/search/staModal.clc',
   		type: 'POST',
   		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
   		data: JSON.stringify(data),
   		success:function(obj){
			// 4-0. 검색 키워드를 헤더에 추가해준다.
			$('.keyword').text(station);
			// 4-1. 모달창에 검색 데이터를 뿌려준다.
			var rcnt = Object.keys(obj).length;
			if(rcnt == 0){
				// 이때 조회된 데이터가 없으면 이 문구를 표시한다.
				$('#stadata').append('<h3 style="text-align: center; padding-top: 30px;">조회된 데이터가 없습니다.</h3>');
			}
			$.each(obj, function(index, item) {
				var tag = '';
				var color = '';
				if(item.region == '서울') {
					color = colors[0];
				} else if(item.region == '경기'){
					color = colors[1];
				} else if(item.region == '인천'){
					color = colors[2];
				}
				tag += '<div class="w3-col w3-white w3-margin-bottom w3-hover-pale-yellow w3-border-bottom stadatalist" id="'+ item.station_id +'" style="cursor: pointer;">';
				tag += '	<div class="w3-col w3-padding">';
				tag += '		<div class="w3-col w3-border-bottom w3-border-blue w3-text-gray">'+ item.region +'</div>';
				tag += '		<div class="w3-col" style="padding-top: 5px;">';
				tag += '			<div class="w3-col">';
				tag += '				<div class="w3-col m4 w3-border-right w3-border-blue" style="font-size: 40px;"><i class="fa fa-map-signs '+ color +'" aria-hidden="true"></i> '+ item.mobile_no +'</div>';
				tag += '				<div class="w3-col m8 w3-padding">';
				tag += '					<div class="w3-col w3-small" style="visibility: hidden;"><b>　　　　　　</b></div>';
				tag += '					<div class="w3-col"><b>'+ item.station_nm +'</b></div>';
				tag += '				</div>';
				tag += '			</div>';
				tag += '		</div>';
				tag += '	</div>';
				tag += '</div>';
				$('#stadata').append(tag); 
			});
			
			// 4-2. 페이징 버튼 처리를 한다. 조회된 데이터가 없는 경우 처리를 하지 않는다.
			if(rcnt != 0) {
				var startPage = obj[0].page.startPage;
				var endPage = obj[0].page.endPage;
				var totalPage = obj[0].page.totalPage;
				nowPage = obj[0].page.nowPage;
				// 4-2-1. 이전 버튼 처리
				$('#stamodal footer').children().children().children().eq(0).attr('id', startPage - 1);
				if(startPage != 1){
					$('#stamodal footer').children().children().children().eq(0).css('display', 'block');
				}
				// 4-2-2. 페이지 버튼들 처리
				for(var i = endPage; i >= startPage; i--) {
					var color = 'w3-white'
					if(i == nowPage) {
						color = 'w3-green';
					}
					var tmp = '<span class="w3-bar-item w3-button pagebtn2 pagenum2 '+ color +'" id="'+ i +'">'+ i +'</span>';
					$('#stamodal footer').children().children().children().eq(0).after(tmp);
				}
				// 4-2-3. 다음 버튼 처리
				$('#stamodal footer').children().children().children().last().attr('id', endPage + 1);
				if(endPage != totalPage) {
					$('#stamodal footer').children().children().children().last().css('display', 'block');
				}
				/*
				// 4-2-4. 현재 페이지 데이터 담기
				$('#nowPage').text(nowPage);
				*/
			}
			// 4-3. 모달창을 보여준다.
			$('#stamodal').css('display', 'block');
			// 4-4. 부모 컨테이너(html, body)의 스크롤을 막아둔다.
			$('html, body').css('overflow', 'hidden'); // 모달팝업 중 html,body의 scroll을 hidden시킴 
			$('nav').css('top', '43px');
			$('nav').css('bottom', '-43px');
			$('html, body').on('scroll touchmove mousewheel', function(event) { 
				// 터치무브와 마우스휠 스크롤 방지     
				event.preventDefault();     
				event.stopPropagation();     
				return false; 
			});
		},
  		error: function(request, error){
			alert('### 통신 실패 ###');
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
   
		});
	}
}

// 버스 검색 이벤트
var busClick = function() {
	// 0. 모달창의 데이터(이전 검색 기록)과 페이징 버튼 세팅을 초기화한다.
	$('#busdata').html('');
	$('.pagenum').detach();
	$('#busmodal footer').children().children().children().eq(0).css('display', 'none');
	$('#busmodal footer').children().children().children().last().css('display', 'none');
	// 1. 검색창에 있는 값을 변수에 담는다.
    var bus = $('#bsearch').val();
/*
    var nowPage = $('#nowPage').text();
	alert($(this).attr('id'));
	if(nowPage == ''){ 
		nowPage = 1;
	}
*/
	var nowPage = $(this).attr('id');
	if(nowPage == '' || nowPage == 'srcroute' || nowPage == undefined) {
		nowPage = 1;
	}
//	alert(nowPage);
	// 2. 검색 창의 값이 없으면 이벤트를 작동하지 않게 한다.
	if(bus == '') {
		alert('검색어를 입력하세요.');
        return;
    } else {
		// 3. 값이 있다면 json 형식으로 만들어준다.
		var data = {name: bus, nowPage: nowPage};
		
		// 4. ajax 처리한다.
		$.ajax({
   		url: '/clc/search/busModal.clc',
   		type: 'POST',
   		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
   		data: JSON.stringify(data),
   		success:function(obj){
			// 4-0. 검색 키워드를 헤더에 추가해준다.
			$('.keyword').text(bus);
			// 4-1. 모달창에 검색 데이터를 뿌려준다.
			var rcnt = Object.keys(obj).length;
			if(rcnt == 0){
				// 이때 조회된 데이터가 없으면 이 문구를 표시한다.
				$('#busdata').append('<h3 style="text-align: center; padding-top: 30px;">조회된 데이터가 없습니다.</h3>');
			}
			$.each(obj, function(index, item) {
				var tag = '';
				var color;
				if(item.route_cd == 6 || item.route_cd == 11 || item.route_cd == 14 || item.route_cd == 16 || item.route_cd == 21 || item.route_cd == 22 || item.route_cd == 42){
					color = colors[0];
				} else if(item.route_cd == 4 || item.route_cd == 13 || item.route_cd == 43) {
					color = colors[1];
				} else if(item.route_cd == 3 || item.route_cd == 12 || item.route_cd == 23 ) {
					color = colors[2];
				} else if(item.route_cd == 2 || item.route_cd == 15 ) {
					color = colors[3];
				} else if(item.route_cd == 41) {
					color = colors[4];
				} else if(item.route_cd == 1 || item.route_cd == 51 ) {
					color = colors[5];
				} else if(item.route_cd == 52 ) {
					color = colors[6];
				} else if(item.route_cd == 53 ) {
					color = colors[7];
				} else if(item.route_cd == 5 || item.route_cd == 30){
					color = colors[8];
				}
				tag +='<div class="w3-col w3-white w3-margin-bottom w3-hover-pale-green w3-border-bottom busdatalist" id="'+ item.route_id +'" style="cursor: pointer;">';
				tag +='	<div class="w3-col w3-padding">';
				tag +='		<div class="w3-col w3-border-bottom w3-border-blue w3-text-gray">'+ item.route_tp +'</div>';
				tag +='		<div class="w3-col" style="padding-top: 5px;">';	
				tag +='			<div class="w3-col">';
				tag +='				<div class="w3-col m4 w3-border-right w3-border-blue" style="font-size: 40px;"><i class="fa fa-bus '+ color +'" aria-hidden="true"></i> '+ item.route_nm +'</div>';			
				tag +='				<div class="w3-col m8 w3-padding">';
				tag +='					<div class="w3-col w3-text-blue w3-small">'+ item.region +'</div>';
				tag +='					<div class="w3-col"><b>'+ item.st_sta_nm +' <i class="fa fa-arrows-h" aria-hidden="true"></i> '+ item.ed_sta_nm +'</b></div>';				
				tag +='				</div>';					
				tag +='			</div>';					
				tag +='		</div>';					
				tag +='	</div>';					
				tag +='</div>';
				$('#busdata').append(tag); 
			});
			
			// 4-2. 페이징 버튼 처리를 한다. 조회된 데이터가 없는 경우 처리를 하지 않는다.
			if(rcnt != 0) {
				var startPage = obj[0].page.startPage;
				var endPage = obj[0].page.endPage;
				var totalPage = obj[0].page.totalPage;
				nowPage = obj[0].page.nowPage;
				// 4-2-1. 이전 버튼 처리
				$('#busmodal footer').children().children().children().eq(0).attr('id', startPage - 1);
				if(startPage != 1){
					$('#busmodal footer').children().children().children().eq(0).css('display', 'block');
				}
				// 4-2-2. 페이지 버튼들 처리
				for(var i = endPage; i >= startPage; i--) {
					var color = 'w3-white'
					if(i == nowPage) {
						color = 'w3-green';
					}
					var tmp = '<span class="w3-bar-item w3-button pagebtn pagenum '+ color +'" id="'+ i +'">'+ i +'</span>';
					$('#busmodal footer').children().children().children().eq(0).after(tmp);
				}
				// 4-2-3. 다음 버튼 처리
				$('#busmodal footer').children().children().children().last().attr('id', endPage + 1);
				if(endPage != totalPage) {
					$('#busmodal footer').children().children().children().last().css('display', 'block');
				}
				// 4-2-4. 현재 페이지 데이터 담기
				$('#nowPage').text(nowPage);
			}
			// 4-3. 모달창을 보여준다.
			$('#busmodal').css('display', 'block');
			// 4-4. 부모 컨테이너(html, body)의 스크롤을 막아둔다.
			$('html, body').css('overflow', 'hidden'); // 모달팝업 중 html,body의 scroll을 hidden시킴 
			$('nav').css('top', '43px');
			$('nav').css('bottom', '-43px');
			$('html, body').on('scroll touchmove mousewheel', function(event) { 
				// 터치무브와 마우스휠 스크롤 방지     
				event.preventDefault();     
				event.stopPropagation();     
				return false; 
			});
		},
  		error: function(request, error){
			alert('### 통신 실패 ###');
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
   
		});
	}
}
// 출발지 검색 이벤트
var startClick = function() {
	var startnm = $('#startroute').val();
	$('#startnm').val(startnm);
}
// 도착지 검색 이벤트
var endClick = function() {
	var endnm = $('#endroute').val();
	$('#endnm').val(endnm);
	
}
var busFocusOut = function() {
	if($('#bsearch').val() == '') {	
		$('#blist').css('display', 'none');
		$('#blist').html('');
	}
}

var staFocusOut = function(){
	if($('#bssearch').val() == '') {
		$('#stalist').css('display', 'none');
	}
}

/* =============================================================================================== */
/* 동적으로 생성된 객체(append 등으로 생성된) 이벤트 처리 */



// 페이지 버튼 이벤트 처리
$(document).on("click",".pagebtn", busClick);
$(document).on("click",".pagebtn2", staClick);

// 연관검색어 클릭에 대한 이벤트 처리
$(document).on("click", ".busKeyword", function(){
	$('#bsearch').val($(this).text());
	$('#blist').html('');
	busClick();
});
$(document).on("click", ".staKeyword", function(){
	$('#bssearch').val($(this).text());
	$('#stalist').html('');
	staClick();
});
$(document).on("click", ".startKeyword", function(){
	$('#startroute').val($(this).text());
	$('#startlist').html('');
	startClick();
});
$(document).on("click", ".endKeyword", function(){
	$('#endroute').val($(this).text());
	$('#endlist').html('');
	endClick();
});
$(document).on("click", '.busdatalist', function() {
		// 검색한 내용이 없으면 리턴
		var routeid = $(this).attr('id');
		// 지역을 구분해서 검색 하기 위해 지역 정보를 가져온다.
		var region = $(this).children().children().eq(1).children().children().eq(1).children().first().text();
		var district_cd = '';
		if(region == '서울') {
			district_cd = 1;
		} else if(region == '경기') {
			district_cd = 2;
		} else if(region == '인천') {
			district_cd = 3;
		}
		if(!routeid){
			alert('검색할 내용이 입력되지 않았습니다.');
			return;
		}
		$('#routeid').val(routeid);
		$('#district_cd').val(district_cd);
		// 버스 검색 버튼을 누르면 버스 상세 페이지로 이동
		$('#routefrm').attr('action', '/clc/search/busdetail.clc');
		$('#routefrm').submit();
	});
$(document).on("click", '.stadatalist', function() {
		// 검색한 내용이 없으면 리턴
		var stationid = $(this).attr('id');

		var district_cd = '';
		var region = $(this).children().children().first().text();
		if(region == '서울') {
			district_cd = 1;
		} else if(region == '경기') {
			district_cd = 2;
		} else if(region == '인천') {
			district_cd = 3;
		}
		
		if(!stationid){
			alert('검색할 내용이 입력되지 않았습니다.');
			return;
		}
		$('#stationid').val(stationid);
		$('#districtcd').val(district_cd);
		
		// 버스 검색 버튼을 누르면 버스 상세 페이지로 이동
		$('#stationfrm').attr('action', '/clc/search/stationdetail.clc');
		$('#stationfrm').submit();
	});
$(document).on("click", '.startdatalist', function() {
		// 검색한 내용이 없으면 리턴
		var startid = $(this).attr('id');
		if(!startid){
			alert('검색할 내용을 입력되지 않았습니다.');
			return;
		}
		$('#startid').val(startid);
		$('#stamodal').hide();
	});
$(document).on("click", '.enddatalist', function() {
		// 검색한 내용이 없으면 리턴
		var endid = $(this).attr('id');
		if(!endid){
			alert('검색할 내용을 입력되지 않았습니다.');
			return;
		}
		$('#endid').val(endid);
		$('#stamodal').hide();
	});

/* ======================================================================================================================== */
$(document).ready(function(){	
	
	$('#bsearch').blur(busFocusOut);
	$('#bsearch').focus(function(){
		$('#blist').css('display', 'block');
	});
	
	$('#bssearch').blur(staFocusOut);
	$('#bssearch').focus(function(){
		$('#stalist').css('display', 'block');
	});
	
	
	// 정류소 탭 클릭할 경우 정류소 검색창을 보여준다
	$('#bsbtn').click(function(){
		$('#bus').addClass('w3-hide');
		$('#busstop').removeClass('w3-hide');
		$('#blist').css('display', 'none');
		$("#bsearch").val('');
		$('#busbtn').removeClass('w3-blue');
		$('#bsbtn').addClass('w3-purple');
	});
	
	// 버스 탭 클릭할 경우 버스 검색창을 보여준다
	$('#busbtn').click(function(){
		$('#bus').removeClass('w3-hide');
		$('#busstop').addClass('w3-hide');
		$('#bslist').css('display', 'none');
		$("#bssearch").val('');
		$('#bsbtn').removeClass('w3-purple');
		$('#busbtn').addClass('w3-blue');
	});
	
	// 버스 검색 모달 창 닫기 이벤트 처리
	$('#closebusmodal').click(function(){
		$('#nowPage').text('');
		$('#busmodal').css('display', 'none');
		$('#busdata').html('');
		$('nav').css('top', '');
		$('nav').css('bottom', '');
		$('html, body').css('overflow', ''); 
		//scroll hidden 해제 
		$('html, body').off('scroll touchmove mousewheel'); // 터치무브 및 마우스휠 스크롤 가능
	});
	$('#closesroutemodal').click(function(){
		$('#sroutemodal').css('display', 'none');
		$('html, body').css('overflow', ''); 
		//scroll hidden 해제 
		$('html, body').off('scroll touchmove mousewheel'); // 터치무브 및 마우스휠 스크롤 가능
	});
	
	// 정류소 검색 모달 창 닫기 이벤트 처리
	$('#closestamodal').click(function(){
		$('#nowPage').text('');
		$('#stamodal').css('display', 'none');
		$('#stadata').html('');
		$('nav').css('top', '');
		$('nav').css('bottom', '');
		$('html, body').css('overflow', ''); 
		//scroll hidden 해제 
		$('html, body').off('scroll touchmove mousewheel'); // 터치무브 및 마우스휠 스크롤 가능
	});
	
	// 버스 연관검색 키워드 리스트 이벤트
	$('#bsearch').keyup(function(){
		if($('#bsearch').val() == '') {	
			$('#blist').css('display', 'none');
			$('#blist').html('');
			return;
		}

		// 1. 검색창의 키워드를 가져온다.
		var keyWord = $('#bsearch').val();
		// 2. 키워드를 json 형식으로 만든다.
		var data = {keyword: keyWord};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/relationlist.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				$('#blist').html('');
				var rcnt = Object.keys(obj).length;
				if(rcnt == 0){
					// 이때 조회된 데이터가 없으면 리턴
					return;
				}
				$.each(obj, function(index, item) {
					var tag = '<div class="w3-col w3-padding-16 w3-button w3-hover-blue busKeyword" style="text-align: left; display: block;">'+ item.route_nm +'</div>';
					$('#blist').append(tag);
				});
				$('#blist').css('display', 'block');
			},
			error:function(){
				alert('### 통신에 실패했습니다. ###');
			}
		});
	});
	
	// 정류소 연관검색 키워드 리스트 이벤트
	$('#bssearch').keyup(function(){
		if($('#bssearch').val() == '') {	
			$('#stalist').css('display', 'none');
			$('#stalist').html('');
//			$('#stalist').remove('.staKeyword');
			return;
		}
		// 1. 검색창의 키워드를 가져온다.
		var keyWord = $('#bssearch').val();
		// 2. 키워드를 json 형식으로 만든다.
		var data = {keyword: keyWord};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/starelationlist.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				// 3-1. 리스트 초기화
				$('#stalist').html('');
				// 3-2. 조회된 리스트 데이터 갯수 조회
				var rcnt = Object.keys(obj).length;
				if(rcnt == 0){
					// 이때 조회된 데이터가 없으면 리턴
					return;
				}
				// 3-3. 조회된 리스트 append
				$.each(obj, function(index, item) {
					var tag = '<div class="w3-col w3-padding-16 w3-button w3-hover-purple staKeyword" style="text-align: left; display: block;">'+ item.station_nm + '</div>';
					$('#stalist').append(tag);
				});
				// 3-4. 보여주기
				$('#stalist').css('display', 'block');
			},
			error:function(){
				alert('### 통신에 실패했습니다. ###');
			}
		});
	});
		// 출발지 검색 리스트 이벤트 ( 다 따로 아이디 값으로 하는 이유는 클래스로 하면 한쪽입력하면 다 바뀜..)
	$('#startroute').keyup(function(){
		if($('#startroute').val() == '') {	
			$('#startlist').css('display', 'none');
			$('#startlist').html('');
//			$('#stalist').remove('.staKeyword');
			return;
		}
		// 1. 검색창의 키워드를 가져온다.
		var keyWord = $('#startroute').val();
		// 2. 키워드를 json 형식으로 만든다.
		var data = {keyword: keyWord};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/starelationlist.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				// 3-1. 리스트 초기화
				$('#startlist').html('');
				// 3-2. 조회된 리스트 데이터 갯수 조회
				var rcnt = Object.keys(obj).length;
				if(rcnt == 0){
					// 이때 조회된 데이터가 없으면 리턴
					return;
				}
				// 3-3. 조회된 리스트 append
				$.each(obj, function(index, item) {
					var tag = '<div class="w3-col w3-padding-16 w3-button w3-hover-purple startKeyword" style="text-align: left; display: block;">'+ item.station_nm + '</div>';
					$('#startlist').append(tag);
				});
				// 3-4. 보여주기
				$('#startlist').css('display', 'block');
			},
			error:function(){
				alert('### 통신에 실패했습니다. ###');
			}
		});
	});
		// 도착지 검색 리스트 이벤트 ( 다 따로 아이디 값으로 하는 이유는 클래스로 하면 한쪽입력하면 다 바뀜..)
	$('#endroute').keyup(function(){
		if($('#endroute').val() == '') {	
			$('#endlist').css('display', 'none');
			$('#endlist').html('');
//			$('#stalist').remove('.staKeyword');
			return;
		}
		// 1. 검색창의 키워드를 가져온다.
		var keyWord = $('#endroute').val();
		// 2. 키워드를 json 형식으로 만든다.
		var data = {keyword: keyWord};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/starelationlist.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				// 3-1. 리스트 초기화
				$('#endlist').html('');
				// 3-2. 조회된 리스트 데이터 갯수 조회
				var rcnt = Object.keys(obj).length;
				if(rcnt == 0){
					// 이때 조회된 데이터가 없으면 리턴
					return;
				}
				// 3-3. 조회된 리스트 append
				$.each(obj, function(index, item) {
					var tag = '<div class="w3-col w3-padding-16 w3-button w3-hover-purple endKeyword" style="text-align: left; display: block;">'+ item.station_nm + '</div>';
					$('#endlist').append(tag);
				});
				// 3-4. 보여주기
				$('#endlist').css('display', 'block');
			},
			error:function(){
				alert('### 통신에 실패했습니다. ###');
			}
		});
	});

	// 버스검색 버튼을 클릭하면 나타나는 이벤트
	$("#srcroute").click(function(){
		$('#blist').html('');
		busClick();	
	});
	// 정류소 검색 버튼을 클릭하면 나타나는 이벤트
	$('#srcstation').click(function(){
		$('#stalist').html('');
		staClick();	
	});
	// 길찾기 검색
	$('#searchroute').click(function(){
		$('#sroutemodal').css('display', 'block');
		$('html, body').css('overflow', 'hidden'); // 모달팝업 중 html,body의 scroll을 hidden시킴 
		$('html, body').on('scroll touchmove mousewheel', function(event) { 
				// 터치무브와 마우스휠 스크롤 방지     
			event.preventDefault();     
			event.stopPropagation();     
			return false; 
		});
	});
	$('.searchrouteoption').click(function(){

		if(!$('#startnm').val()){
			alert("출발 정류소가 입력되지 않았습니다.");
			return;
		}
		if(!$('#endnm').val()){
			alert("도착 정류소가 입력되지 않았습니다.");
			return;
		}
		$('#searchroutefrm').attr('action', '/clc/search/searchrouteoption.clc');
		$('#searchroutefrm').submit();
	});
	
	
	$('#login').click(function(){
		$(location).attr('href','/clc/member/login.clc');
	});

});
// 친구 관련 기능
/* =============================================================================================== */
// 친구 등록 및 삭제 함수
var fr_add_delbtn = function(e) {
	
	var frid = $(e).prev().html().trim();
	var data = {frid:frid};
	var starO = $(e).children().attr('class');
	if(starO.indexOf('fa-star-o') != -1) {
		// 친구 추가 요청
		var mark = '?';
		connect(e, mark);
	} else {
		// 친구 삭제
		var mark = '*';
		connect(e, mark);	
	}

	$.ajax({
		url: '/clc/member/add_delfriend.clc',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(data),
		success: function(obj){
		},
		error: function(){
			alert('### 비동기 통신 실패 ###');
		}
	})
	
	$(e).children().toggleClass('w3-hide');
};
// 친구추가 요청을 위한 웹소켓
var websocket = new WebSocket("ws://localhost/clc/chat-clc");
function connect(e, mark) {	
	websocket.onopen = onOpen(e, mark);
	websocket.onclose = onClose;
	websocket.onerror = onError;
	websocket.close();
};

function onOpen(e, mark) {
	var sid = $('#sid').text();
	var id = $(e).prev().text();
	alert(id + '님을 선택하셨습니다.');
	websocket.send(mark+sid+':'+id);
};

function onClose(evt) {
	var code = evt.code;
	var reason = evt.reason;
	var wasClean = evt.wasClean;
	
	if(wasClean) {
		console.log('Connection closed normally.');
	} else {
		console.log('Connection closed normally.' + reason + 'Code : ' + code);
	}
};
// 웹 소켓 연결 도중 에러 발생시 호출되는 함수
function onError(evt) {
	$('#message').val('Error: ' + evt);
}

function disconnect() {
	websocket.close();
};

$(document).ready(function() {
	var sid = $('#sid').html();
	// 친구 검색 리스트  이벤트
	$('#friendname').keyup(function() {
		// 1. 검색창 키워드를 가져온다
		var keyword = $('#friendname').val();
		// 2. 키워드를 json 형식으로 만든다.
		var data = {keyword: keyword};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/searchfriend.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				// 3-1. 리스트 초기화
				$('#friendlist').html('');
				// 3-2. 조회된 리스트 데이터 갯수 조회
				var rcnt = Object.keys(obj).length;
				if(rcnt == 0) {
					// 조회된 데이터가 없으면 리턴
					return;
				}
				// 3-3. 조회된 리스트 append
				$.each(obj, function(index, item) {
					if(sid == item.id) {
						
					} else {
						if(item.cnt == 0) {							
							var tag = '<div class="w3-col m11 w3-cell-row w3-button" style="text-align: left; display: block;">';
							tag += '<div class="w3-container w3-padding w3-cell w3-left-align">'+item.name+', &nbsp;</div>';
							tag += '<div class="w3-container w3-padding w3-cell w3-left-align">'+item.id+'</div>';
							tag += '<div class="w3-cell w3-display-container" id="'+item.mno+'" style="width: 40px;" onclick="fr_add_delbtn(this)">';
							tag += '<i class="w3-text-gray w3-display-middle w3-col fa fa-star-o fa-1x" aria-hidden="true" style="cursor: pointer;"></i>';
							tag += '</div>';
							tag += '</div>';
							$('#friendlist').append(tag);
							$('#friendlist').css('display', 'block');
						} else if(item.cnt == 1) {
							var tag = '<div class="w3-col m11 w3-cell-row w3-button" style="text-align: left; display: block;">';
							tag += '<div class="w3-container w3-padding w3-cell w3-left-align">'+item.name+', &nbsp;</div>';
							tag += '<div class="w3-container w3-padding w3-cell w3-left-align">'+item.id+'</div>';
							tag += '<div class="w3-cell w3-display-container" id="'+item.mno+'" style="width: 40px;" onclick="fr_add_delbtn(this)">';
							tag += '<i class="w3-text-amber w3-display-middle w3-col fa fa-star fa-1x" aria-hidden="true" style="cursor: pointer;"></i>';
							tag += '</div>';
							tag += '</div>';
							$('#friendlist').append(tag);
							$('#friendlist').css('display', 'block');
						}
					}
				});
					
			},
			error:function(){
				alert('### 비동기 통신에 실패하였습니다.');
			}
		})
	})
	// 친구 검색 모달창 활성화
	$('#searchfriend').click(function() {
		$('#frimodal').css('display', 'block');
	});
	
	// 천구 검색 모달창 닫기
	$('#closefrimodal').click(function() {
		$('#frimodal').css('display', 'none');
		$('#friendlist').css('display', 'none');
		disconnect();
	});
	
})
	