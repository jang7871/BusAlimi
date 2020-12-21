$(document).ready(function(){
	$('.pagebtn').click(function(){
		var str = $(this).text();
		var pcode = str.charCodeAt(str);
		
		var sPage = '';
		
		if(pcode == 171){
			sPage = $(this).attr('id');
		} else if(pcode == 187){
			sPage = $(this).attr('id');
		} else {
			sPage = str;
		}
		
		/*
		// 1. GET 방식 전송
		$(location).attr('href', '/cls/reBoard/reBoardList.cls?nowPage=' + sPage);
		*/
		
		// 2. POST 방식 전송
		// 파라미터 셋팅 부터 하고
		$('#nowPage').val(sPage);
		$('#pfrm').submit();
	});
	
	$('#delbtn').click(function(){
		$('#selectAll').removeClass('w3-hide');
		$('.ckbox').removeClass('w3-hide');
		$('#redelbtn').removeClass('w3-hide');
		$('#deletebtn').removeClass('w3-hide');
		$(this).addClass('w3-hide');
	});
	
	// 취소버튼
	$('#redelbtn').click(function(){
		$('#selectAll').addClass('w3-hide');
		$('.ckbox').addClass('w3-hide');
		$('.ckbox').prop('checked', false);
		$('#delbtn').removeClass('w3-hide');
		$('#deletebtn').addClass('w3-hide');
		$(this).addClass('w3-hide');
	});
	
	
	// 최종 삭제 버튼
	$('#deletebtn').click(function(){
		
		var check = confirm('삭제하시겠습니까?');
		if(!check){
			return;
		}
		
		$('#frm').attr('action', '/clc/member/bookdelproc.clc');
		$('#frm').submit();
	});

	// 모두 선택 버튼
	$('#selectAll').click(function(){
		$('.ckbox').prop('checked', true);
	});
	
	// 정류소 버튼 클릭 시 정류소 상세검색 페이지로 이동
	$('.sBtn').click(function(){
		if($('#deletebtn').hasClass('w3-hide')) {
			var station_id = $(this).children().first().attr('id');
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
	});
	
	// 버스 버튼 클릭 시 버스 상세검색 페이지로 이동
	$('.rBtn').click(function(){
		if($('#deletebtn').hasClass('w3-hide')) {
			var route_id = $(this).children().first().attr('id');
			var region = $(this).children().children().first().text().substring(0, 2);
			var district_cd = '';
			if(region == '서울') {
				district_cd = 1;
			} else if(region == '경기') {
				district_cd = 2;	
			} else if(region == '인천') {
				district_cd = 3;
			}
			
			let rfrm = $(document.createElement('form'));
			$(rfrm).attr('method', 'GET');
			$(rfrm).attr('action', '/clc/search/busdetail.clc');
			
			let rinput = $(document.createElement('input'));
			$(rinput).attr('type', 'hidden');
			$(rinput).attr('name', 'route_id');
			$(rinput).val(route_id);
			
			let dinput = $(document.createElement('input'));
			$(dinput).attr('type', 'hidden');
			$(dinput).attr('name', 'district_cd');
			$(dinput).val(district_cd);
			
			var station_id = $(this).children().children().first().attr('id');
			if(station_id != 0) {
				let sinput = $(document.createElement('input'));
				$(sinput).attr('type', 'hidden');
				$(sinput).attr('name', 'station_id');
				$(sinput).val(station_id);
				$(rfrm).append(sinput);
			}
			
			$(rfrm).append(rinput);
			$(rfrm).append(dinput);
			$('body').prepend(rfrm);
			
			$(rfrm).submit();
			
		}
	});
});