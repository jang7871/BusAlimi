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
	
	$('#srcstation').click(function() {
		// 검색한 내용이 없으면 리턴
		var stationid = $('#stationid').val();
		var stationnm = $('#stationnm').val();
		var x = $('#x').val();
		var y = $('#y').val();
		var mobile = $('#mobile').val();
		var retion = $('#region').val();
		if(!stationid){
			alert('검색할 내용을 입력하세요.');
			return;
		}
		// 정류소 검색 버튼을 누르면 정류소 상세 페이지로 이동
		$('#stationfrm').attr('action', '/clc/search/stationdetail.clc');
		$('#stationfrm').submit();
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
});