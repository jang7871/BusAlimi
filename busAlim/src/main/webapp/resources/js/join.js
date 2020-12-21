$(document).ready(function(){
	
	$('#id').focusout(function(){
		var sid = $(this).val();
		if(!sid){
			return;	
		};
		
		$.ajax({
			url:'/clc/member/idCheck.clc',
			type:'post',
			dataType:'text',
			data:{
				id:sid
			},
			success:function(data){
				if(data == 'OK'){
					$('#id').removeClass('w3-pale-red');
					$('#id').addClass('w3-teal');
				} else {
					$('#id').removeClass('w3-teal');
					$('#id').addClass('w3-pale-red');
				}
			},
			error:function(){
				alert('##에러당');
			}
		});
	});
	
	// 비밀번호 정규식 검사
	$('#pw').keyup(function(){
		// 비밀번호는 영문대소문자 각 1개씩 숫자 1개 특수문자(#@!$%&-_) 1개
		// 가 반드시 한개씩 포함되는 형식을 사용하기로 한다.
		
		// 입력내용 알아내고
		var spw = $(this).val();
		
		// 정규식 패턴 만들고
		var pattern = /^.*(?=^[a-zA-Z0-9#@!$%&-_*]{8,})(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@!$%&-_*]).*$/;
		// 검사하고
		var result = pattern.test(spw);
		// 결과로 처리하고
		if(result){
			$(this).removeClass('w3-pale-red');
			$(this).addClass('w3-teal');
		} else {
			$(this).removeClass('w3-teal');
			$(this).addClass('w3-pale-red');
		}
	});
	
	// 비밀번호 확인
	$('#pwconf').keyup(function() {
		var spw = $('#pw').val();
		var scpw = $('#pwconf').val();
		
		if((spw == scpw)) {
			$(this).removeClass('w3-pale-red');
			$(this).addClass('w3-teal');
			if(scpw == '') {
				$(this).removeClass('w3-teal');
				$(this).addClass('w3-pale-red');
			}
		} else {
			$(this).removeClass('w3-teal');
			$(this).addClass('w3-pale-red');
		}
	});
	
	$('#name').focusout(function(){
		var sname = $(this).val();
		var pattern = /^[가-힣]{2,10}$/;
		var result = pattern.test(sname);
		if(result){
				$('#name').removeClass('w3-pale-red');
				$('#name').addClass('w3-teal');
		}else{
				$('#name').removeClass('w3-teal');
				$('#name').addClass('w3-pale-red');
			
		};
	});
	$('#mail').keyup(function(){
		
		var mail = $(this).val();
		
		// 정규식 패턴 만들고
		var pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		// 검사하고
		var result = pattern.test(mail);
		// 결과로 처리하고
		if(result){
			$(this).removeClass('w3-pale-red');
			$(this).addClass('w3-teal');
		} else {
			$(this).removeClass('w3-teal');
			$(this).addClass('w3-pale-red');
		}
	});
	
	// 성별 선택시 아바타 보여주는 함수
	$('#gen').change(function() {
		$('#avttt').css('display', '');
		var sgen = $('#gen').val();
		
		if(sgen == 'M') {
			$('.genW').stop().slideUp(100);
			$('.genM').stop().slideDown(300);
		} else if(sgen == 'W') {
			$('.genM').stop().slideUp(100);
			$('.genW').stop().slideDown(300);
		}
	});
	
	
	$('#confirm').click(function() {
		var sid = $('#id').val();
		var spw = $('#pw').val();
		var scpw = $('#pwconf').val();
		var sname = $('#name').val();
		var smail = $('#mail').val();
		var sgen = $('#gen').val();
		var savt = $('#avatar:checked').val();
		var sqt = $('#qt').val();
		var sans = $('#ans').val();
		
		if(!(sid && spw && sname && smail && sgen && savt && sqt && sans)) {
			alert('입력창에 빠진 내용이 있습니다.')
			return;
		} else if(spw != scpw) {
			alert('비밀번호가 다릅니다.')
			return;
		}
		
		$.ajax({
			url:'/clc/member/mailConfirm.clc',
			type: 'post',
			dataType: 'text',
			async: false,
			data:{
				mail: smail
			},
			success:function(obj){
				alert(obj);
				if(obj == 'OK') {
					var time = target_time();
					$('#authKey').slideDown(300, function(){
						$('#confirm').slideUp(100, function(){
							$('#submit').slideDown(300, function(){
								setInterval(function(){getCountdown(time);}, 1000);
								pagestart();
							});
						});
					});
				} else {
					alert('## 접속 에러');
				}
			},
			error:function(){
				alert('### 비동기 통신 에러 ###');
			}
		});
	});

	$('#submit').click(function() {
		var sid = $('#id').val();
		var spw = $('#pw').val();
		var scpw = $('#pwconf').val();
		var sname = $('#name').val();
		var smail = $('#mail').val();
		var sgen = $('#gen').val();
		var savt = $('#avatar:checked').val();
		var sqt = $('#qt').val();
		var sans = $('#ans').val();
		
		if(!(sid && spw && sname && smail && sgen && savt && sqt && sans)) {
			return;
		} else if(spw != scpw) {
			return;
		}
		
		$('#frm').attr('method','POST');
		$('#frm').attr('action','/clc/member/joinProc.clc');
		$('#frm').submit();
	});
	
	
//	var countdown = document.getElementById("tiles"); // get tag element
	
	function target_time() {		
		var target_date = new Date().getTime() + (1000*60*3); // set the countdown date
		return target_date;
	}
	function getCountdown(time){

		var minutes, seconds; // variables for time units
	
		// find the amount of "seconds" between now and target
		var current_date = new Date().getTime();
		var seconds_left = (time - current_date) / 1000;
	   
		minutes = pad( parseInt(seconds_left / 60) );
		seconds = pad( parseInt( seconds_left % 60 ) );
	
		// format countdown string + set tag value
		$('#tiles').html("<span>" + minutes + "</span><span>" + seconds + "</span>"); 
/*		countdown.innerHTML = "<span>" + minutes + "</span><span>" + seconds + "</span>"; */
		
	};
	
	function pad(n) {
		return (n < 10 ? '0' : '') + n;
	};
	
	
});
