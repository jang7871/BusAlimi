$(document).ready(function(){
	
	$('#lbtn').click(function(){
		var sid = $('#id').val();
		var spw = $('#pw').val();
		
		if(!(sid&&spw)){
			return;
		}
		
		$('#frm').attr('action', '/clc/member/loginProc.clc');
		$('#frm').submit();
	});
	
})