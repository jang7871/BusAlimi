// delete 버튼
	/*맨 마지막 리스트를 삭제할 경우 document.ready안에 함수를 만들게 되면 처리가 되지 않았다
	그래서 동적요소 이벤트 처리로 해결했다 */
	$(document).on("click", '#dbtn' ,function() {
		var bool = confirm('Are you sure you hope to delete?');
		
		if(bool) {
			var bno = $(this).prev().attr('id');
			$('#body').attr('id', 'bno');
			$('#bno').attr('name', 'bno')
			$('#bno').val(bno);
			$('#frm').attr('action', '/clc/board/delproc.clc');
			$('#frm').submit();
		} else {
			alert('not delete');
			return;
		}
	});
	
$(document).ready(function() {
	// reset버튼
	$('.rbtn').click(function() {
		// 내용 리셋 
		$(this).parent().siblings().eq(1).val('');
	});
	
	// write 버튼
	$('#wbtn').click(function() {
		
		var body = $(this).parent().siblings().eq(1).val();
		
		// 내용이 없을 경우 이 함수를 끝낸다.
		if(body == '' || body == '내용을 입력하세요!') {
			alert('Please write your content.');
			return;
		}

		$('#body').val(body);
		$('#frm').attr('action', '/clc/board/writeproc.clc');
		$('#frm').submit();
	});
	
	

	// edit 버튼
	$('#ebtn').click(function() {
		
		var body = $(this).parent().siblings().eq(1).val();
		var bno = $('#dbtn').prev().attr('id');
		
		$('#body').val(body);
		$('#bno').val(bno);		
		$('#frm').attr('action', '/clc/board/editproc.clc');
		$('#frm').submit();
	});
	
	// 페이징 버튼
	$('.pbtn').click(function() {
		
		var page1 = $(this).text();
		var page2 = $(this).attr('id');
		
		if(!page2){
			page2 = page1;
		}
		
		$('#nowPage').val(page2);
		$('#pfrm').attr('action', '/clc/board/board.clc');
		$('#pfrm').submit();
	})
});