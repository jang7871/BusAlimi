$(document).ready(function() {
	
	$('.msg').click(function() {
		$('#msgmodal').css('display','block');
		var id = $(this).prev().html();
		$('#receiverId').html(id);
		// 메세지 초기화
		$('#message').val('');
		
		connect();
	});
	
	$('#msgclose').click(function() {
		
		disconnect();
		$('#msgmodal').css('display','none');
	})
	
	// 친구 수락 요청 모달창 닫기
	$('#resoclose').click(function() {
		$('#respmodal').css('display', 'none');
	})
	
	// 친구요청수락
	$('#ok').click(function() {
		$('#reqok').attr('action', '/clc/member/response.clc');
		$('#reqok').submit();
	})
	// 메세지 보내기 버튼
	$('#send').click(function() {
		
		send();
		$('#msgmodal').css('display','none');
		disconnect();
	});
	// 수신확인
	$('.fa-comment').click(function() {
		var msgno = $(this).next().val();
		var data = {msgno: msgno};
		$.ajax({
			url: '/clc/member/msgCheck.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				if(obj.result == 'OK') {
					var backTag = $('#'+msgno+'').parent().parent().children().eq(0);
					backTag.css('display', 'none');
					var tag = '<div class="w3-right w3-round" id="dbtn">';
					tag += '<i class="fa fa-comment-o" aria-hidden="true">&nbsp;</i>';
					tag += '</div>';
					backTag.after(tag);
				} else {	
					alert('수신확인 실패.');
				}				
			},
			error:function() {
				alert('비동기 통신 실패.');
			}
		});
	});
	
	// 메세지 삭제
	$('.fa-trash-o').click(function() {
		var tag = $(this).parent().parent().parent();
		var msgno = $(this).parent().prev().children().attr('id');
		var data = {msgno: msgno};
		$.ajax({
			url: '/clc/member/msgDel.clc',
			type: 'POST',
			datatype: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success: function(obj) {
				if(obj.result == 'OK') {
					tag.css('display', 'none');
		
				} else {
					alert('삭제 실패.')
				}
			},
			error: function() {
				alert('비동기 통신 실패.')
			}
			
		});
		
	})
	
	// websocket 연결하기
	// 메세지 아이콘 클릭하면 호출되는 함수
	var webSocket;
	function connect() {
		webSocket = new WebSocket("ws://localhost/clc/chat-clc");
		
		webSocket.onopen = onOpen;
		webSocket.onclose = onClose;
		webSocket.onerror = onError;
	}
	
	// 웹 소켓에 연결되었을 때 호출되는 함수
	function onOpen() {
		var id = $('#receiverId').html();
		webSocket.send(" / " + id + " : " + "님에게 메세지 수신 준비 완료.");
	}
	// 웹 소켓과 연결이 해제되었을 대 호출되는 함수
	function onClose(evt) {
		var code = evt.code;
		var reason = evt.reason;
		var wasClean = evt.wasClean;
		
		if(wasClean) {
			console.log($('#message').val('Connection closed normally.'));
		} else {
			console.log($('#message').val('Connection closed with message ' + reason + '(Code: '+ code + ')'));			
		}
	}
	// 웹 소켓 연결 도중 에러 발생시 호출되는 함수
	function onError(evt) {
		$('#message').val('Error: ' + evt);
	}
	// 메세지를 보냈을 때 호출되는 함수
	function send() {
		var id = $('#receiverId').html();
		var msg = $('#message').val();
		if(!msg) {
			alert('메세지를 입력하세요.');
			$('#msgmodal').css('display', 'none');
			return;
		}
		webSocket.send(id + "/" + msg);
	}
	// 메세지를 보내고 창을 닫았을 때 호출되는 함수
	function disconnect() {
		webSocket.send("웹소켓 연결 끊기 ");
		webSocket.close();
	}
	
	// 페이징 버튼
	$('.pbtn').click(function() {
		
		var page1 = $(this).text();
		var page2 = $(this).attr('id');
		
		if(!page2){
			page2 = page1;
		}
		
		$('#nowPage').val(page2);
		$('#pfrm').attr('action', '/clc/member/friendlist.clc');
		$('#pfrm').submit();
	})
	
});