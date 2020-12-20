var colors = ['w3-red', 'w3-teal', 'w3-blue', 'w3-pink', 'w3-indigo', 'w3-purple', 'w3-amber', 'w3-lime', 'w3-yellow'];
// 노선 + 정류소 즐겨찾기가 있는지 여부를 검사하고 가져오는 요청 처리 함수
var findBookmark = function(){
	// 1. 즐겨찾기를 찾기 위한 노선 아이디 값을 가져온다.
	var route_id = $('#routeid').val();
	// 2. json 형식으로 만들어준다.
	var data = {data: route_id, type: 'route'};
	$.ajax({
		url: '/clc/search/findBookmarkProc.clc',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8;',
		data: JSON.stringify(data),
		success:function(obj){
			if(Object.keys(obj).length == 0){
				// 조회된 즐겨찾기가 없는 경우 리턴
				return;
			} else {
				// 조회된 즐겨찾기가 있는 경우
				$.each(obj, function(index, item) {
					var addTag = $('#bm' + item.station_id).children('.addBtn');
					var delTag = $('#bm' + item.station_id).children('.delBtn');
					// 빈 별 아이콘(추가버튼)을 숨기고 색깔있는 별 아이콘(삭제버튼)을 보여준다.
					$(addTag).addClass('w3-hide');
					$(delTag).removeClass('w3-hide');
					// 색깔있는 별 아이콘(삭제버튼)에 즐겨찾기 고유번호를 아이디값으로 준다.
					$(delTag).attr('id', item.bmno);
				});
			}
		},
		error:function(request, error){
			alert('### 통신 실패 ###');
//			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	
	});
}

$(document).ready(function(){

	$(window).scrollTop(0);

	
	if($('#id').val() != '') {
		findBookmark();
	}

	// 마이페이지 즐겨찾기에서, 정류소 + 노선 버튼을 클릭할 경우 실행되는 이벤트
	var station_id = $('#stationid').val();
	if(station_id != undefined && station_id != 0 && station_id != '') {
		var tag = $('#' + station_id).parent().parent();
		$(tag).addClass('w3-pale-blue');
		var pos = tag.offset();
		
		$('html').stop().animate({scrollTop: pos.top - 100}, 500);
		$('#stationid').removeAttr('value');
		history.replaceState({}, null, '/clc/search/busdetail.clc?route_id=' + $('#routeid').val());
	}

});


$(document).ready(function(){
	// 스크롤탑 버튼 이벤트
    $(window).scroll(function() {
        if ($(this).scrollTop() > 500) {
            $('#scrollTopBtn').fadeIn();
        } else {
            $('#scrollTopBtn').fadeOut();
        }
    });
    
    $("#scrollTopBtn").click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 400);
        return false;
    });


	// 노선 유형에 따라 버스 정보 영역의 배경색을 달리 한다.
	var route_tp = $('#busInfo').children().first().text();
	var color;
//	alert(route_tp);
	if(route_tp == '일반형공항버스') {
		color = colors[7];
	} else if(route_tp == '좌석형공항버스') {
		color = colors[6];
	} else if(route_tp == '리무진공항버스' || route_tp == '공항') {
		color = colors[5];
	} else if(route_tp == '고속형시외버스') {
		color = colors[4];
	} else if(route_tp == '마을버스' || route_tp == '순환') {
		color = colors[8];
	} else if(route_tp == '좌석형시내버스' || route_tp == '일반형농어촌버스' || route_tp == '간선') {
		color = colors[2];
	} else if(route_tp == '따복형시내버스' || route_tp == '마을') {
		color = colors[3];
	} else if(route_tp == '일반형시내버스' || route_tp == '일반형시외버스' || route_tp == '지선') {
		color = colors[1];
	} else {
		color = colors[0];
	}
	$('#busInfo').addClass(color);		
	$('#sBtn').addClass(color);
	$('#opeInfo').children().find('header').addClass(color);
	// 방향 데이터가 없는 노선의 경우, 정방향 역방향 버튼을 없앤다.
	var len = $('.rDirect').toArray();
	if(len == 0){
		$('#diBtn').css('display', 'none');
	}
	
	// 정방향, 역방향 이벤트 처리
	$('#sBtn').click(function(){
		var pos = $('.sDirect').first().offset();
		$('#rBtn').removeClass(color);
		$('#sBtn').addClass(color);
		$('html').stop().animate({scrollTop: pos.top - 50}, 500);
	});
	$('#rBtn').click(function(){
		var pos = $('.rDirect').first().offset();
		$('#sBtn').removeClass(color);
		$('#rBtn').addClass(color);
		$('html').stop().animate({scrollTop: pos.top - 50}, 500);
	});
	
	// 버스 정보 조회(모달창) 이벤트
	$('#infoBtn').click(function(){
		$('#opeInfo').css('display', 'block');
	});
	
	// 정류소 버튼 클릭 시 해당 정류소 상세페이지 이동 이벤트
	$('.routeBtn').click(function(){
		var station_id = $(this).attr('id');
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
	});
	

	// 즐겨찾기 추가 이벤트
	$('.addBtn').click(function(){
		// 0. 로그인한 상태가 아니면 리턴
		if(!$('#id').val()){
			alert('로그인 후 이용해 주세요.');
			$(location).attr('href', '/clc/member/login.clc');
			return;
		}
		var valType = '';
		var routeid = '';
		var stationid = '';
		var addBtn = '';
		var delBtn = '';
		var district = '';
		
		// 1. 관할구역과 노선아이디 정보를 가져온다.
		district = $('#district_cd').val();
		routeid = $('#routeid').val();
		
		// 2. 즐겨찾기 유형을 검사한다.(버스인가?/ 버스+정류소인가?)
		var innerText = $(this).text();
		if(!innerText) {
			// 버튼에 글자가 없는 경우(별모양 아이콘)는 버스+정류소 추가
			valType = 'busstation';
			stationid = $(this).parent().attr('id').substring(2);
			// 현재 클릭한 객체(addBtn)과 형제 태그(delBtn)을 각각 변수에 담는다.
			addBtn = $(this);
			delBtn = $(this).next();
		} else {
			// 버튼에 글자가 있는 경우(추가)는 버스만 추가
			valType = 'bus'
		}
		// 3. json 형식으로 만들어준다.
		var data = {route_id: routeid, station_id: stationid, type: valType, area: district};
//		alert(data.type + ',' + data.station_id + ',' + data.route_id);
		
		// 4. ajax 처리한다.
		$.ajax({
			url: '/clc/member/bookaddproc.clc',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8;',
			data: JSON.stringify(data),
			success:function(obj){
				if(obj.result == 'BUSADD'){
					// 버스 즐겨찾기 추가하는 경우
					alert('추가되었습니다.');
					location.reload();
				} else if(obj.result == 'BUSSTAADD') {
					// 버스 + 해당 정류소 즐겨찾기 추가하는 경우
					alert('추가되었습니다.');
//					$(addBtn).addClass('w3-hide');
//					$(delBtn).removeClass('w3-hide');
					location.reload();
				} else if(obj.result == 'LOGIN') {
					alert('로그인 후 이용해 주세요.');
					$(location).attr('href', '/clc/member/login.clc');
					return;
				} else {
					alert('등록에 실패하였습니다. 잠시 후 이용해주세요.');
					return;
				}
			},
			error:function(){
				alert('### 통신 오류 ###');
			}
		});
		
	});
	
	// 즐겨찾기 삭제 이벤트
	$('.delBtn').click(function(){
		var addBtn = '';
		var delBtn = '';
		var bmno = '';
		var valType = '';
		
		// 1. 클릭한 버튼의 즐겨찾기 유형을 검사한다.(버스인가?/ 버스+정류소인가?)
		var innerText = $(this).text();
		if(!innerText) {
			// 버튼에 글자가 없는 경우는 버스+정류소 추가
			valType = 'busstation';
			bmno = $(this).attr('id');
			// 현재 클릭한 객체(addBtn)과 형제 태그(delBtn)을 각각 변수에 담는다.
			delBtn = $(this);
			addBtn = $(this).next();
		} else {
			// 버튼에 글자가 있는 경우는 버스만 추가
			valType = 'bus';
			bmno = $(this).attr('id');
		}
//		alert(bmno + " - " + valType);
		// 2. json 형식으로 만들어준다.
		var data = {bmno: bmno};
		
		// 3. ajax 처리한다.
		$.ajax({
			url: '/clc/member/bookdelprocAjax.clc',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8;',
			data: JSON.stringify(data),
			success:function(obj){
				if(obj.result == 'OK'){
					if(valType == 'bus') {
						alert('삭제되었습니다.');
						location.reload();
					} else if(valType == 'busstation') {
						alert('삭제되었습니다.');
//						$(delBtn).addClass('w3-hide');
//						$(addBtn).removeClass('w3-hide');
						location.reload();
					}
				} else if(obj.result == 'LOGIN') {
					alert('로그인 후 이용해 주세요.');
					$(location).attr('href', '/clc/member/login.clc');
					return;
				} else {
					alert('삭제를 실패하였습니다. 잠시 후 다시 이용해주세요.');
					return;
				}
			},
			error:function(){
				alert('### 통신 오류 ###');
			}
		});
	});

});