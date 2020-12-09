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
	
	$('#idCk').click(function(){
		$('#findType').val('idCk');
		
		$('#frm').attr('action', '/clc/member/findpage.clc');
		$('#frm').submit();
	});
	
	$('#pwCk').click(function(){
		$('#findType').val('pwCk');
		
		$('#frm').attr('action', '/clc/member/findpage.clc');
		$('#frm').submit();
	});
})