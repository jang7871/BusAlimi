$(document).ready(function(){
	// Get the Sidebar
	var mySidebar = document.getElementById("mySidebar");
	
	// Get the DIV with overlay effect
	var overlayBg = document.getElementById("myOverlay");
	
	// Toggle between showing and hiding the sidebar, and add overlay effect
	function w3_open() {
	  if (mySidebar.style.display === 'block') {
	    mySidebar.style.display = 'none';
	    overlayBg.style.display = "none";
	  } else {
	    mySidebar.style.display = 'block';
	    overlayBg.style.display = "block";
	  }
	};
	
	// Close the sidebar with the close button
	function w3_close() {
	  mySidebar.style.display = "none";
	  overlayBg.style.display = "none";
	};
	
	// 수정
	$('#editbtn2').click(function() {
		var oMail = $('#email').parent().prev().children().html();
		var nMail = $('#email').val();
		
		if(oMail == nMail) {
			alert('동일한 이메일은 변경할 수 없습니다.');
			$('#email').next().val('');
			return;			
		}
		$('#frm').submit();
	});
	
	// 삭제
	$('#delbtn').click(function() {
		$('#dfrm').submit();
	});
	
});