/**
 * 
 */
 
 /**
 * button click 해서 내 위치 조회하는 기능
 */
 
 const myLocation = document.getElementById("myLocation");
 const lat = document.getElementById("lat");
 const lnt = document.getElementById("lnt");
 
 function onGeo(position) {
	
	const latitude = position.coords.latitude;
	const longitude = position.coords.longitude;
	
	alert("위치 조회에 성공하였습니다.");
	
	lat.value =`${latitude}`;
	lnt.value = `${longitude}`;
	
}

function onGeoErr() { alert("Can't find you. try again.'") };

function geoHandler(event) {
	event.preventDefault();
	navigator.geolocation.getCurrentPosition(onGeo, onGeoErr);
}

myLocation.addEventListener("click", geoHandler);
