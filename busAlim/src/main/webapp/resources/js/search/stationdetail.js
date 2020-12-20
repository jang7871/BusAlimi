var colors = ['w3-text-red', 'w3-text-green', 'w3-text-blue', 'w3-text-pink', 'w3-text-indigo', 'w3-text-purple', 'w3-text-amber', 'w3-text-lime', 'w3-text-yellow'];
function getColor(route_tp) {
	var color;
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
	return color;
}

var findBookmark = function(){
	// 1. 즐겨찾기를 찾기 위한 정류소 아이디 값을 가져온다.
	var station_id = $('#stationid').val();

	// 2. json 형식으로 만들어준다.
	var data = {data: station_id, type: 'station'};
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
					var addTag = $('#bm' + item.route_id).children('.addBtn');
					var delTag = $('#bm' + item.route_id).children('.delBtn');
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
	
	if($('#id').val() != '') {
		findBookmark();
	}
		
//	alert($('.routenm').eq(0).text());
	var len = $('.rDirect').toArray();
	for(var i = 0; i < len.length; i++) {
		var route_tp = $('.routetp').eq(i).text();
//		alert(route_tp);
		var color = getColor(route_tp);
		$('.routenm').eq(i).addClass(color);
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
		
		// 1. 관할구역과 정류소 아이디 정보를 가져온다.
		stationid = $('#stationid').val();
		if($('#staregion').text() == '서울'){
			district = 1;		
		} else if($('#staregion').text() == '경기') {
			district = 2;					
		} else if($('#staregion').text() == '인천') {
			district = 3;
		}
		
		// 2. 즐겨찾기 유형을 검사한다.(정류소인가?/ 버스+정류소인가?)
		var innerText = $(this).text();
		if(!innerText) {
			// 버튼에 글자가 없는 경우(별모양 아이콘)는 버스+정류소 추가
			valType = 'busstation'
			routeid = $(this).parent().attr('id').substring(2);
			// 현재 클릭한 객체(addBtn)과 형제 태그(delBtn)을 각각 변수에 담는다.
			addBtn = $(this);
			delBtn = $(this).next();
		} else {
			// 버튼에 글자가 있는 경우(추가)는 정류소만 추가
			valType = 'station'
		}
		// 3. json 형식으로 만들어준다.
		if(valType == 'busstation') {
			// 이 때 버스 + 정류소 추가할 경우에는, 버스의 관할구역을 기준으로 즐겨찾기 추가한다.
			var region = $(this).parent().siblings().eq(1).children().first().children().children().last().text();

			district = '';
			if(region == '( 서울 )') {
				district = 1;
			} else if(region == '( 경기 )') {
				district = 2;	
			} else if(region == '( 인천 )') {
				district = 3;
			}
		}
		var data = {route_id: routeid, station_id: stationid, type: valType, area: district};
		
		// 4. ajax 처리한다.
		$.ajax({
			url: '/clc/member/bookaddproc.clc',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8;',
			data: JSON.stringify(data),
			success:function(obj){
				if(obj.result == 'STAADD'){
					// 정류소 즐겨찾기 추가하는 경우
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
		
		// 1. 클릭한 버튼의 즐겨찾기 유형을 검사한다.(정류소인가?/ 버스+정류소인가?)
		var innerText = $(this).text();
		if(!innerText) {
			// 버튼에 글자가 없는 경우는 버스+정류소 추가
			valType = 'busstation';
			bmno = $(this).attr('id');
			// 현재 클릭한 객체(addBtn)과 형제 태그(delBtn)을 각각 변수에 담는다.
			delBtn = $(this);
			addBtn = $(this).next();
		} else {
			// 버튼에 글자가 있는 경우는 정류소만 추가
			valType = 'station';
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
					if(valType == 'station') {
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
	
	// 버스 버튼 클릭 시 해당 버스 상세페이지 이동 이벤트
	$('.rDirect').click(function(){
		var route_id = $(this).attr('id');
		var region = $(this).children().first().children().children().last().text();
		var district_cd = '';
		if(region == '( 서울 )') {
			district_cd = 1;
		} else if(region == '( 경기 )') {
			district_cd = 2;	
		} else if(region == '( 인천 )') {
			district_cd = 3;
		}
		
		let rfrm = $(document.createElement('form'));
		$(rfrm).attr('method', 'POST');
		$(rfrm).attr('action', '/clc/search/busdetail.clc');
		
		let rinput = $(document.createElement('input'));
		$(rinput).attr('type', 'hidden');
		$(rinput).attr('name', 'route_id');
		$(rinput).val(route_id);
		
		let dinput = $(document.createElement('input'));
		$(dinput).attr('type', 'hidden');
		$(dinput).attr('name', 'district_cd');
		$(dinput).val(district_cd);
		

		$(rfrm).append(rinput);
		$(rfrm).append(dinput);
		$('body').prepend(rfrm);
		
		$(rfrm).submit();
	});
	

	$('.refresh').click(function(){
		$('#frm').attr('action','/clc/search/stationdetail.clc');
		$('#frm').submit();
	});

});