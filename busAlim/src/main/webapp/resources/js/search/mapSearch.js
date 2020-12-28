var colors = ['w3-text-red', 'w3-text-green', 'w3-text-blue', 'w3-text-pink', 'w3-text-indigo', 'w3-text-purple', 'w3-text-amber', 'w3-text-lime', 'w3-text-yellow'];
var bgcolors = ['w3-red', 'w3-green', 'w3-blue', 'w3-pink', 'w3-indigo', 'w3-purple', 'w3-amber', 'w3-lime', 'w3-yellow'];
$(document).ready(function(){
	// 지도를 생성한다.
	// 기본 좌표는 인크레파스 융합SW교육센터
	var mapOptions = {
	    center: new naver.maps.LatLng(37.48217336674078, 126.9013480410284),
		useStyleMap: true,
	    zoom: 16,
		zoomControl: true,
        zoomControlOptions: {
            style: naver.maps.ZoomControlStyle.SMALL,
            position: naver.maps.Position.RIGHT_CENTER
        },
		mapTypeControl: true
	};
	
	var map = new naver.maps.Map('map', mapOptions);
	
	// 로드뷰 닫는 버튼 이벤트
	$('#closeRoadView').click(function(){
		pano.setVisible(false);
	});
	
	// 지도의 중심좌표가 바뀔 때마다 해당 지역 주소를 화면에 출력한다.
	naver.maps.Event.addListener(map, 'idle', function() {
	   var latlng = map.getCenter();
	   // alert(position);
		naver.maps.Service.reverseGeocode({
			coords: latlng,
			orders: naver.maps.Service.OrderType.ADDR
		}, function(status, response){
			if (status === naver.maps.Service.Status.ERROR) {
			      if (!latlng) {
			        return alert('ReverseGeocode Error, Please check latlng');
			      }
			      if (latlng.toString) {
			        return alert('ReverseGeocode Error, latlng:' + latlng.toString());
			      }
			      if (latlng.x && latlng.y) {
			        return alert('ReverseGeocode Error, x:' + latlng.x + ', y:' + latlng.y);
			      }
			      return alert('ReverseGeocode Error, Please check latlng');
			}
			
			var address = response.v2.address,
	        htmlAddresses = '';
			
		    if (address.jibunAddress !== '') {
		        htmlAddresses = address.jibunAddress;
		    }
			
			// console.log(htmlAddresses);
			$('#centerAddr').text(htmlAddresses);
			$('#centerAddr2').text(htmlAddresses);
			
		});
	});

	// 지도에 표시된 마커와 인포윈도우 객체를 저장하는 변수 선언
	var prevCurMarker = null;
	var prevCircle = null;
	var markers = [],
		infoWindows = [];
	
	// GeoLocation api를 이용해서 현재 내 위치 알아내는 이벤트	
	var getMyLoc = function(){
		$('.routePan').stop().slideUp();
		// 1. HTML5의 geolocation을 사용할 수 있는지 확인한다
		if(navigator.geolocation) {
			// 2. GeoLocation을 이용해서 접속 위치를 얻어온다.
			navigator.geolocation.getCurrentPosition(function(position) {
				// 2-1. 위치정보 제공 사용에 동의한 경우 이벤트 처리
				
				// 2-1-1. 현재 내 위치의 위도와 경도를 알아낸다
				
				var lat = position.coords.latitude;	 // 위도
				var lon = position.coords.longitude; // 경도

				// 2-1-2. 이전의 지도 중심 좌표를 알아낸다
				var prevCenter = map.getCenter();
				var prevLat = prevCenter.lat();
				var prevLon = prevCenter.lng();
				
				// 2-1-3. 현 위치가 이전 중심좌표와 같으면 이벤트를 실행하지 않는다.
				if(lat == prevLat && lon == prevLon) {
					// alert('이전과 같은 좌표');
					return;
				}
				
				// 2-1-4. 이전에 표기된 마커와 도형, 선들을 제거한다
				if(prevCurMarker != null) {					
					prevCurMarker.setMap(null);
				}
				if(prevInfoWindow != null) {					
					prevInfoWindow.setMap(null);
				} 
				if(prevMarker != null) {					
					prevMarker.setMap(null);
				} 
				if(markers.length != 0) {
					for(var i = 0; i < markers.length; i++){
						markers[i].setMap(null);
					}
					for(var j = 0; j < infoWindows.length; j++){
						infoWindows[j].setMap(null);
					}
				}
				if(prevPolyLine != null){
					prevPolyLine.setMap(null);
				}
				if(prevCircle != null){
					prevCircle.setMap(null);
				}
				// 2-1-5. 현재 내 위치를 지도의 중심좌표로 설정하고 마커로 표시한다.
				var location = new naver.maps.LatLng(lat, lon);
				
				map.setCenter(location);
				map.setZoom(16);
				
				var marker = new naver.maps.Marker({
				    position: location,
				    map: map,
					icon: {
					        url: '/clc/img/download/ico_pin.jpg', //50, 68 크기의 원본 이미지
					        size: new naver.maps.Size(25, 34),
					        scaledSize: new naver.maps.Size(25, 34),
					        origin: new naver.maps.Point(0, 0),
					        anchor: new naver.maps.Point(12, 34)
					    },
					animation: naver.maps.Animation.BOUNCE
				});
				
				// 마커 주변으로 원 생성
				var circle = new naver.maps.Circle({
				    map: map,
				    center: location,
				    radius: 500,
				    fillColor: 'crimson',
				    fillOpacity: 0.2,
					strokeColor: 'crimson'
				});
				
				prevCircle = circle;
				
				// 2-1-6. 내 위치 기준 반경 1km 내 정류소들을 비동기 통신으로 가져온다.
				var data = {lat: location.lat(), lng: location.lng()};
				$.ajax({
					url: '/clc/search/mapAroundStation.clc',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json; charset=UTF-8',
					data: JSON.stringify(data),
					success: function(obj) {
						$.each(obj, function(index, item){
							// 마커 생성
							var position = new naver.maps.LatLng(item.loc_y, item.loc_x);
							var marker = new naver.maps.Marker({
							    position: position,
							    map: map,
								title: item.station_nm,
								icon: {
								        url: '/clc/img/download/busmarker_purple.png',
								        size: new naver.maps.Size(38, 43),
								        scaledSize: new naver.maps.Size(38, 43),	// 24, 35
								        origin: new naver.maps.Point(0, 0),
								        anchor: new naver.maps.Point(12, 34)
								    }
							});
							marker.addListener('mouseover', onMouseOver);
    						marker.addListener('mouseout', onMouseOut);
							
							// 인포윈도우 생성
							var contentString = [
								'<div class="w3-padding-large">',
						        '   <div class="w3-col" style="font-size: 1.2em;"><b>'+ item.station_nm +'</b></div>',
						        '   <div class="w3-col w3-text-gray w3-margin-bottom" style="font-size: 0.8em;">'+ item.mobile_no +'<span class="w3-right w3-text-red"><b>'+ item.distance +'m</b></span></div>',
						        '   <div class="w3-cell-row w3-center w3-round w3-border"><div class="w3-cell w3-border-right w3-button roadViewBtn" id="'+ item.loc_x + ',' + item.loc_y + '"style="padding: 10px;" onclick="roadView(this)">로드뷰</div><div class="w3-cell w3-button staDetail" style="padding: 10px;" id="'+ item.station_id +'" onclick="staDetail(this)">상세보기</div></div>',
						        '</div>'

						    ].join('');
		
							var infoWindow = new naver.maps.InfoWindow({
								content: contentString,
								maxWidth: 220,
								borderColor: '#DCDCDC',
								borderWidth: 0.5,
								anchorSize: new naver.maps.Size(20, 15)
							});
							
							// 마커와 인포윈도우 객체를 배열 변수에 저장
							markers.push(marker);
							infoWindows.push(infoWindow);
							
							// 마커 클릭 시 인포윈도우 열고 닫는 이벤트 발생
							naver.maps.Event.addListener(marker, "click", function(e) {
							    if (infoWindow.getMap()) {
							        infoWindow.close();
							    } else {
							        infoWindow.open(map, marker);
							    }
							});
							
						});
					},
					error: function() {
						alert('### 통신 에러 ###');
					}
				});
				
				$('.locMsgPan').text('반경 1km 이내 정류소를 탐색합니다.');
				$('.locMsgPan').stop().fadeIn(400).delay( 3000 ).fadeOut(400);
				
				prevCurMarker = marker;
				
			}, function(){
				// 2-2. 위치 정보 제공에 동의하지 않았을 경우
				// 경고 메시지를 띄워준다.
				$('.locMsgPan').text('위치정보 제공에 동의하지 않았습니다.');
				$('.locMsgPan').stop().fadeIn(400).delay( 2000 ).fadeOut(400);
			});
			
		} else {
			// 2-3. 위치 정보를 사용할 수 없는 환경인 경우
			$('.locMsgPan').text('위치정보를 사용할 수 없는 환경입니다.');
			$('.locMsgPan').stop().fadeIn(400).delay( 2000 ).fadeOut(400);
		}
	}
	
	// 처음 지도를 로딩할 때, 내 주변 위치를 중심 좌표로 설정한다.
	getMyLoc();
	
	// 내위치 버튼을 클릭할 때마다 이벤트가 발생한다
	$('#currentPos').click(function(){
		getMyLoc();	
	});
	
	// 검색창에 포커스를 올릴 경우 검색 패널이 나타난다
	$('#searchInput').focus(function(){
		$('.searchPan').css('display', 'block');
//		$('#searchBtn').css('visibility', 'visible');
	});
	
	// 탭 키 및 특수 키(ctrl, alt 등) 입력을 막는다.
	$(window).on("keydown", function (e) {
	   if (e.keyCode == 9 || e.keyCode == 17 || e.keyCode == 18 || e.keyCode == 27 || (e.keyCode > 111 && e.keyCode < 124) || e.altKey || e.ctrlKey) {
	     return false;
	   }
	});

	// 닫기 버튼을 누르면 검색 패널이 사라진다
	$('.closeBtn').click(function(){
		$('.searchPan').css('display', 'none');		
		$('#searchBtn').css('visibility', 'hidden');
		
		if($('#scrollTopBtn').css('display') == 'block'){
			$('#scrollTopBtn').stop().fadeOut();
		}

	});
	
	// 버스 탭을 누르면 버스 검색이 활성화된다
	$('#rBtn').click(function(){
		$('#searchInput').val('');
		$('#keyList').html('');
		$('#sBtn').removeClass('w3-border-deep-purple');
		$('#sBtn').removeClass('w3-text-deep-purple');
		$('#sBtn').addClass('w3-text-gray');
		
		$('#rBtn').addClass('w3-border-deep-purple');
		$('#rBtn').addClass('w3-text-deep-purple');
		$('#rBtn').removeClass('w3-text-gray');
	});
	
	// 정류소 탭을 누르면 정류소 검색이 활성화된다
	$('#sBtn').click(function(){
		$('#searchInput').val('');
		$('#keyList').html('');
		$('#rBtn').removeClass('w3-border-deep-purple');
		$('#rBtn').removeClass('w3-text-deep-purple');
		$('#rBtn').addClass('w3-text-gray');
		
		$('#sBtn').addClass('w3-border-deep-purple');
		$('#sBtn').addClass('w3-text-deep-purple');
		$('#sBtn').removeClass('w3-text-gray');
	});
	
	// 연관검색 결과 출력 이벤트
	$('#searchInput').keyup(function(){
		if($('#searchInput').val() == '') {	
			// $('#keyList').css('display', 'none');
			$('#keyList').html('');
			return;
		}
		var searchType;
		
		// 1. 검색 유형을 구한다.(버스인가? 정류소인가?)
		if($('#sBtn').hasClass('w3-border-deep-purple')) {
			searchType = 'station';
		} else {
			searchType = 'bus';
		}
		// 2. 검색창의 키워드를 가져온다.
		var keyWord = $('#searchInput').val();
		// 3. json 형식으로 만든다.
		var data = {keyword: keyWord, type: searchType, nowPage: 1};
		// 3. 키워드를 controller에 보낸다.(ajax 처리)
		$.ajax({
			url: '/clc/search/mapRelList.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				$('#keyList').html('');
				var rcnt = Object.keys(obj).length;
				/*alert(rcnt);*/
				
				if(rcnt == 0){
					// 이때 조회된 데이터가 없으면 이 문구를 표시한다.
					$('#keyList').append('<h3 style="text-align: center; padding-top: 30px;">조회된 데이터가 없습니다.</h3>');
				}
				
				$.each(obj, function(index, item) {
					// console.log(searchType);
					var tag = '';
					var color = '';
					if(searchType == 'station') {					
						if(item.region == '서울') {
							color = colors[0];
						} else if(item.region == '경기'){
							color = colors[1];
						} else if(item.region == '인천'){
							color = colors[2];
						}
						
						tag += '<div class="w3-col w3-margin-bottom w3-hover-pale-yellow w3-border-bottom stadatalist" id="'+ item.station_id +'" style="cursor: pointer; z-index: 101; background-color: #FFFAF0;">';
						tag += '	<div class="w3-col w3-padding">';
						tag += '		<div class="w3-col w3-border-bottom w3-border-blue w3-text-gray">'+ item.region +'</div>';
						tag += '		<div class="w3-col" style="padding-top: 5px;">';
						tag += '			<div class="w3-col">';
						tag += '				<div class="w3-col m4 w3-border-right w3-border-blue" style="font-size: 40px;"><i class="fa fa-map-signs '+ color +'" aria-hidden="true"></i> '+ item.mobile_no +'</div>';
						tag += '				<div class="w3-col m8 w3-padding">';
						tag += '					<div class="w3-col w3-small" style="visibility: hidden;"><b>　　　　　　</b></div>';
						tag += '					<div class="w3-col"><b>'+ item.station_nm +'</b></div>';
						tag += '				</div>';
						tag += '			</div>';
						tag += '		</div>';
						tag += '	</div>';
						tag += '</div>';						
					} else {
						if(item.route_cd == 6 || item.route_cd == 11 || item.route_cd == 14 || item.route_cd == 16 || item.route_cd == 21 || item.route_cd == 22 || item.route_cd == 42){
							color = colors[0];
						} else if(item.route_cd == 4 || item.route_cd == 13 || item.route_cd == 43) {
							color = colors[1];
						} else if(item.route_cd == 3 || item.route_cd == 12 || item.route_cd == 23 ) {
							color = colors[2];
						} else if(item.route_cd == 2 || item.route_cd == 15 ) {
							color = colors[3];
						} else if(item.route_cd == 41) {
							color = colors[4];
						} else if(item.route_cd == 1 || item.route_cd == 51 ) {
							color = colors[5];
						} else if(item.route_cd == 52 ) {
							color = colors[6];
						} else if(item.route_cd == 53 ) {
							color = colors[7];
						} else if(item.route_cd == 5 || item.route_cd == 30){
							color = colors[8];
						}
						tag +='<div class="w3-col w3-margin-bottom w3-hover-pale-green w3-border-bottom busdatalist" id="'+ item.route_id +'" style="cursor: pointer; background-color: #FFFAF0;">';
						tag +='	<div class="w3-col w3-padding">';
						tag +='		<div class="w3-col w3-border-bottom w3-border-blue w3-text-gray">'+ item.route_tp +'</div>';
						tag +='		<div class="w3-col" style="padding-top: 5px;">';	
						tag +='			<div class="w3-col">';
						tag +='				<div class="w3-col m4 w3-border-right w3-border-blue" style="font-size: 40px;"><i class="fa fa-bus '+ color +'" aria-hidden="true"></i> '+ item.route_nm +'</div>';			
						tag +='				<div class="w3-col m8 w3-padding">';
						tag +='					<div class="w3-col w3-text-blue w3-small">'+ item.region +'</div>';
						tag +='					<div class="w3-col"><b>'+ item.st_sta_nm +' <i class="fa fa-arrows-h" aria-hidden="true"></i> '+ item.ed_sta_nm +'</b></div>';				
						tag +='				</div>';					
						tag +='			</div>';					
						tag +='		</div>';					
						tag +='	</div>';					
						tag +='</div>';
					}

					// console.log(tag);
					$('#keyList').append(tag);
				});
			},
			error:function(){
				alert('### 통신에 실패했습니다. ###');
			}
		});		
	});
	

	// 스크롤탑 버튼 이벤트
    $('#keyList').scroll(function() {
		// alert('스크롤!!');
        if ($(this).scrollTop() > 400) {
            $('#scrollTopBtn').fadeIn();
        } else {
            $('#scrollTopBtn').fadeOut();
        }
    });
    $("#scrollTopBtn").click(function() {
        $('#keyList').animate({
            scrollTop : 0
        }, 400);
        return false;
    });
	
	// 정류소 클릭할 시 해당 정류소로 이동
	var prevMarker;
	var prevInfoWindow;
	$(document).on("click", ".stadatalist", function(){
		$('.routePan').stop().slideUp();
		// 1. 정류소 아이디 값을 가져온다.
		var stationid = $(this).attr('id');
	
		/*
		if(!stationid){
			alert('검색할 내용을 입력되지 않았습니다.');
			return;
		}
		*/
		// 2. json 형식으로 만든다.
		var data = {station_id: stationid};
		
		// 3. ajax 처리한다.
		$.ajax({
			url: '/clc/search/mapStationDetail.clc',
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(data),
			contentType: 'application/json',
			success:function(obj){
				if($('#scrollTopBtn').css('display') == 'block'){
					$('#scrollTopBtn').stop().fadeOut();
				}
				// 3-0. 이전에 지도에 표시된 마커와 인포윈도우, 선들을 제거한다.
				if(markers.length != 0) {
					for(var i = 0; i < markers.length; i++){
						markers[i].setMap(null);
					}
					for(var j = 0; j < infoWindows.length; j++){
						infoWindows[j].setMap(null);
					}
				}
				if(prevMarker != null) {					
					prevMarker.setMap(null);
				} 
				if(prevInfoWindow != null) {					
					prevInfoWindow.setMap(null);
				} 
				if(prevPolyLine != null){
					prevPolyLine.setMap(null);
				}
				// 3-1. 검색 패널을 숨긴다.
				$('.searchPan').css('display', 'none');
				
				// 3-2. 해당 정류소의 좌표를 지도의 중심좌표로 설정한다.
				var location = new naver.maps.LatLng(obj[0].loc_y, obj[0].loc_x);
				map.setCenter(location);
				map.setZoom(18);
				// alert(map);
				
				// 3-3. 해당 정류소의 위치에 마커를 생성한다.
				var marker = new naver.maps.Marker({
				    position: location,
				    map: map,
					icon: {
					        url: '/clc/img/download/busmarker_purple.png', //50, 68 크기의 원본 이미지
					        size: new naver.maps.Size(38, 43),
					        scaledSize: new naver.maps.Size(38, 43),
					        origin: new naver.maps.Point(0, 0),
					        anchor: new naver.maps.Point(12, 34)
					    },
					animation: naver.maps.Animation.DROP
				});
				
				// 3-4. 나중에 삭제할 경우를 대비해서 마커 객체를 전역 변수에 저장해놓는다.
				prevMarker = marker;
				
				// 3-5. 마커에 마우스오버, 마우스아웃 이벤트를 등록한다.
				marker.addListener('mouseover', onMouseOver);
    			marker.addListener('mouseout', onMouseOut);
				
				// 3-6. 해당 정류소 정보를 담은 인포윈도우를 생성한다.
				var contentString = [
				        '<div class="w3-padding-large">',
				        '   <div class="w3-col" style="font-size: 1.2em;"><b>'+ obj[0].station_nm +'</b></div>',
				        '   <div class="w3-col w3-text-gray" style="font-size: 0.8em;">'+ obj[0].mobile_no +'</div>',
				        '   <div class="w3-col w3-margin-bottom">'+obj[0].next_station_nm +' 방면</div>',
				        '   <div class="w3-cell-row w3-center w3-round w3-border"><div class="w3-cell w3-border-right w3-button" id="'+ obj[0].loc_x + ',' + obj[0].loc_y + '" onclick="roadView(this)" style="padding: 10px;">로드뷰</div><div class="w3-cell w3-button staDetail" style="padding: 10px;" id="'+ obj[0].station_id +'" onclick="staDetail(this)">상세보기</div></div>',
				        '</div>'
				    ].join('');
				
				var infowindow = new naver.maps.InfoWindow({
				    content: contentString,
					maxWidth: 220,
					borderColor: '#DCDCDC',
					borderWidth: 0.5,
					anchorSize: new naver.maps.Size(20, 15)
				});
				
				// 3-7. 나중에 삭제할 경우를 대비해서 인포윈도우 객체를 전역 변수에 저장해 놓는다.
				prevInfoWindow = infowindow;
				
				// 3-8. 마커를 클릭할 경우 인포윈도우를 열고 닫는 이벤트가 발생한다.
				naver.maps.Event.addListener(marker, "click", function(e) {
				    if (infowindow.getMap()) {
				        infowindow.close();
				    } else {
				        infowindow.open(map, marker);
				    }
				});
				
				infowindow.open(map, marker);
				
			},
			error: function(){
				alert("### 통신 오류 ###");
			}
		});
	});
	
	// 마커 마우스 오버, 마우스 아웃 이벤트
	function onMouseOver(e) {
	    var marker = e.overlay;
	
	    marker.setIcon({
				url: '/clc/img/download/busmarker_blue.png', 
		        size: new naver.maps.Size(38, 43),
		        scaledSize: new naver.maps.Size(38, 43),
		        origin: new naver.maps.Point(0, 0),
		        anchor: new naver.maps.Point(12, 34)
		    });
	}
	
	function onMouseOut(e) {
	    var marker = e.overlay;
	
	    marker.setIcon({
	        url: '/clc/img/download/busmarker_purple.png',
	        size: new naver.maps.Size(38, 43),
	        scaledSize: new naver.maps.Size(38, 43),
	        origin: new naver.maps.Point(0, 0),
	        anchor: new naver.maps.Point(12, 34)
	    });
	}

	// 버스 클릭할 시 해당 노선 경로 보여주기
	var prevPolyLine = null;
	$(document).on("click", ".busdatalist", function(){
		$('.routePan').stop().slideUp();
		// 1. 노선아이디와 관할구역 정보를 가져온다.
		var routeid = $(this).attr('id');
		
		var region = $(this).children().children().eq(1).children().children().eq(1).children().first().text();
		var district_cd = '';
		if(region == '서울') {
			district_cd = 1;
		} else if(region == '경기') {
			district_cd = 2;
		} else if(region == '인천') {
			district_cd = 3;
		}
		if(!routeid){
			alert('검색할 내용이 입력되지 않았습니다.');
			return;
		}
		
		// 2. json 형식으로 만든다.
		var data = {route_id: routeid, district_cd: district_cd};
		
		// 3. ajax 처리한다.
		$.ajax({
			url: '/clc/search/mapRouteDetail.clc',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify(data),
			success:function(obj) {
				if($('#scrollTopBtn').css('display') == 'block'){
					$('#scrollTopBtn').stop().fadeOut();
				}
				// 3-0. 이전에 지도에 표시된 마커와 인포윈도우, 선들을 제거한다.
				if(markers.length != 0) {
					for(var i = 0; i < markers.length; i++){
						markers[i].setMap(null);
					}
					for(var j = 0; j < infoWindows.length; j++){
						infoWindows[j].setMap(null);
					}
				}
				if(prevMarker != null) {					
					prevMarker.setMap(null);
				} 
				if(prevInfoWindow != null) {					
					prevInfoWindow.setMap(null);
				} 
				if(prevPolyLine != null){
					prevPolyLine.setMap(null);
				}
				// 3-1. 검색 패널을 숨긴다.
				$('.searchPan').css('display', 'none');
				
				// 3-2. 노선 경유 정류소들 중 첫번째 지점의 좌표를 지도의 중심좌표로 설정한다.
				var location = new naver.maps.LatLng(obj[0].loc_y, obj[0].loc_x);
				map.setCenter(location);
				map.setZoom(14);
				
				
				var path = [];
				$.each(obj, function(index, item){
					// 3-3. 해당 정류소의 위치에 마커를 생성한다.
					var position = new naver.maps.LatLng(item.loc_y, item.loc_x);
					path.push(position);
					var url;
					
					if(item.direction == '정'){
						url = '/clc/img/download/busmarker_green.png';
						// pathF.push(position);
					} else if(item.direction == '역') {
						url = '/clc/img/download/busmarker_red.png';
						// pathR.push(position);
					} else if(item.direction == '무') {
						url = '/clc/img/download/busmarker_grey.png';
						// pathN.push(position);
					}
					var marker = new naver.maps.Marker({
					    position: position,
					    map: map,
						icon: {
						        url: url, 
						        size: new naver.maps.Size(30, 35), // 38, 43
						        scaledSize: new naver.maps.Size(30, 35),
						        origin: new naver.maps.Point(0, 0),
						        anchor: new naver.maps.Point(12, 34)
						    }
					});
					
					// 3-4. 해당 정류소 정보를 담은 인포윈도우를 생성한다.
					var contentString = [
					        '<div class="w3-padding-large">',
					        '   <div class="w3-col" style="font-size: 1.2em;"><b>'+ item.station_nm +'</b></div>',
					        '   <div class="w3-col w3-text-gray" style="font-size: 0.8em;">'+ item.mobile_no +'</div>',
					        '   <div class="w3-cell-row w3-center w3-round w3-border"><div class="w3-cell w3-border-right w3-button" id="'+ item.loc_x + ',' + item.loc_y + '" onclick="roadView(this)" style="padding: 10px;">로드뷰</div><div class="w3-cell w3-button staDetail" style="padding: 10px;" id="'+ item.station_id +'" onclick="staDetail(this)">상세보기</div></div>',
					        '</div>'
					    ].join('');
					
					var infowindow = new naver.maps.InfoWindow({
					    content: contentString,
						maxWidth: 220,
						borderColor: '#DCDCDC',
						borderWidth: 0.5,
						anchorSize: new naver.maps.Size(20, 15)
					});
					
					// 3-5. 나중에 삭제할 경우를 대비해서 마커와 인포윈도우 객체를 배열 변수에 저장
					markers.push(marker);
					infoWindows.push(infowindow);
												
					// 3-6. 마커를 클릭할 경우 인포윈도우를 열고 닫는 이벤트가 발생한다.
					naver.maps.Event.addListener(marker, "click", function(e) {
					    if (infowindow.getMap()) {
					        infowindow.close();
					    } else {
					        infowindow.open(map, marker);
					    }
					});
					
				});
				// 4. 노선 경로를 표시한다.
				var polyline = new naver.maps.Polyline({
					map: map,
					path: path,
					strokeWeight: 10,
					strokeLineCap: 'round',
					strokeLineJoin: 'round',
					strokeColor: '#6A5ACD',
					startIcon: naver.maps.PointingIcon.CIRCLE,
					startIconSize: 15,
					endIcon: naver.maps.PointingIcon.OPEN_ARROW,
					endIconSize: 30	
				});
				prevPolyLine = polyline;
				
				// 5. 하단에 노선 정보 박스를 띄운다.
				var route_tp = obj[0].route_tp;
				var color;
				if(route_tp == '일반형공항버스') {
					color = bgcolors[7];
				} else if(route_tp == '좌석형공항버스') {
					color = bgcolors[6];
				} else if(route_tp == '리무진공항버스' || route_tp == '공항') {
					color = bgcolors[5];
				} else if(route_tp == '고속형시외버스') {
					color = bgcolors[4];
				} else if(route_tp == '마을버스' || route_tp == '순환') {
					color = bgcolors[8];
				} else if(route_tp == '좌석형시내버스' || route_tp == '일반형농어촌버스' || route_tp == '간선') {
					color = bgcolors[2];
				} else if(route_tp == '따복형시내버스' || route_tp == '마을') {
					color = bgcolors[3];
				} else if(route_tp == '일반형시내버스' || route_tp == '일반형시외버스' || route_tp == '지선') {
					color = bgcolors[1];
				} else {
					color = bgcolors[0];
				}
				
				for(var i = 0; i < bgcolors.length; i++){
					$('.routePan').removeClass(bgcolors[i]);
				}
				$('.roadDistance').text(Math.round(polyline.getDistance()) / 1000 + 'km');	// 노선 길이
				$('.routePan').addClass(color);			// 노선 유형에 따라 배경색을 다르게
				$('.routePan').attr('id', region + obj[0].route_id);	// 상세페이지 이벤트 처리를 위한 아이디 설정
				$('.routeNm').text(obj[0].route_nm);	// 노선 이름
				$('.region').text('(' + region + ')');	// 관할구역
				$('.st_nm').text(obj[0].st_sta_nm);		// 기점
				$('.ed_nm').text(obj[0].ed_sta_nm);		// 종점
				
				$('.routePan').stop().slideDown();
			},
			error:function(){
				alert('### 통신 에러 ###');
			}
		});
		
	});
	
	// 버스 상세보기 페이지로 이동하는 이벤트
	$('.routePan').click(function(){
		var route_id = $(this).attr('id').substring(2);
		var region = $(this).attr('id').substring(0, 2);
		var district_cd = '';
		if(region == '서울') {
			district_cd = 1;
		} else if(region == '경기') {
			district_cd = 2;	
		} else if(region == '인천') {
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
});
