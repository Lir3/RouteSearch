document.addEventListener("DOMContentLoaded", function() {
	let application_date = document.getElementById("application_date");
	let user_id = document.getElementById("user_id");
	let affiliation = document.getElementById("affiliation");
	let bicycle_distance = document.getElementById("bicycle_distance");
	let bicycle_time = document.getElementById("bicycle_time");


	if (application_date) application_date.disabled = true;
	if (user_id) user_id.disabled = true;
	if (affiliation) affiliation.disabled = true;
	if (bicycle_distance) bicycle_distance.disabled = true;
	if (bicycle_time) bicycle_time.disabled = true;
});

let map, directionsService, directionsRenderer, startAutocomplete, endAutocomplete;

function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center: { lat: 35.6895, lng: 139.6917 },
		zoom: 13
	});

	directionsService = new google.maps.DirectionsService();
	directionsRenderer = new google.maps.DirectionsRenderer();
	directionsRenderer.setMap(map);

	const options = { componentRestrictions: { country: "jp" } };
	startAutocomplete = new google.maps.places.Autocomplete(document.getElementById("address"), options);
	endAutocomplete = new google.maps.places.Autocomplete(document.getElementById("end"), options);
}

// Google Maps API の読み込み完了後に initMap() を実行
window.initMap = initMap;


// calcRoute関数の修正
function calcRoute(event) {
	event.preventDefault(); // フォームが送信されるのを防ぐ

	const start = document.getElementById('address').value;
	const end = document.getElementById('end').value;

	const directionsService = new google.maps.DirectionsService();
	const directionsRenderer = new google.maps.DirectionsRenderer();

	const map = new google.maps.Map(document.getElementById('map'), {
		center: { lat: 35.6895, lng: 139.6917 },
		zoom: 13
	});
	directionsRenderer.setMap(map);

	const request = {
		origin: start,
		destination: end,
		travelMode: 'BICYCLING',
	};

	directionsService.route(request, function(response, status) {
		if (status === 'OK') {
			directionsRenderer.setDirections(response);
			displayRouteInfo(response);
		} else {
			alert('経路を計算できませんでした: ' + status);
		}
	});
}

// 経路情報を表示
function displayRouteInfo(response) {
	const route = response.routes[0].legs[0];
	const duration = route.duration.text;
	const distance = route.distance.text;

	const routesDiv = document.getElementById('routes');
	routesDiv.innerHTML = `
                <label for="bicycle_distance">自転車所要距離</label>
                <input type="text" id="bicycle_distance" name="bicycle_distance" value="${distance}" disabled>

                <label for="bicycle_time">自転車所要時間</label>
                <input type="text" id="bicycle_time" name="bicycle_time" value="${duration}" disabled>
            `;
	//あとから値を入れる
	document.getElementById('bicycle_time').value = duration;
	document.getElementById('bicycle_distance').value = distance;
}
