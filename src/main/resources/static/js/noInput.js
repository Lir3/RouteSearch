document.addEventListener("DOMContentLoaded", function() {
	let application_date = document.getElementById("application_date");
	let user_id = document.getElementById("user_id");
	let affiliation = document.getElementById("affiliation");
	let bic_move_distance = document.getElementById("bic_move_distance");
	let bic_move_time = document.getElementById("bic_move_time");
	let supportAmount = document.getElementById("supportAmount");

	if (application_date) application_date.disabled = true;
	if (user_id) user_id.disabled = true;
	if (affiliation) affiliation.disabled = true;
	if (bic_move_distance) bic_move_distance.disabled = true;
	if (bic_move_time) bic_move_time.disabled = true;
	if (supportAmount) supportAmount.disabled = true;
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

    // Kmの単位を取り除き、数値部分を取得
    const distanceValue = parseFloat(distance.replace('Km', '').trim());

    // input要素を取得
    const distanceInput = document.getElementById('bic_move_distance');
    const timeInput = document.getElementById('bic_move_time');

    // 値をセット
    distanceInput.value = distance;
    timeInput.value = duration;

    // disabledを解除し、フォーム送信時に値が送られるようにする
    distanceInput.removeAttribute("disabled");
    timeInput.removeAttribute("disabled");

    // 支給額の計算を行う
    calculateSupportAmount();
}


// 支給額計算
function calculateSupportAmount() {
    const bicMoveDistanceInput = document.getElementById("bic_move_distance");
    const supportAmountInput = document.getElementById("supportAmount");

    // `bic_move_distance` の値を取得
    const bicMoveDistanceValue = bicMoveDistanceInput.value;

    // Kmの単位を取り除き、数値部分を取得
    const distanceValue = parseFloat(bicMoveDistanceValue.replace('Km', '').trim());

    // 計算条件
    let supportAmount = 0;
    if (distanceValue >= 2 && distanceValue < 10) {
        supportAmount = 4200; // 支給額
    }

    // 計算結果を表示
    supportAmountInput.value = supportAmount + "円";
}