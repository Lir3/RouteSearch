	
function getTeikiPrice(route, period) {
	if (route.Price && route.Price.length > 0) {
		var teikiSummary = route.Price.find(price => price.kind === period + 'Summary');
		if (teikiSummary) {
			return parseInt(teikiSummary.Oneway);
		}
	}
	return '不明';
}

function showRoute(routeIndex) {
    var routeContainers = document.querySelectorAll('.route-card');
    routeContainers.forEach(function (container, index) {
        if (routeIndex === -1) {
            container.style.display = 'block';
        } else {
            container.style.display = (index === routeIndex) ? 'block' : 'none';
        }
    });
}

function downloadRouteAsTxt(routeIndex) {
    var routes = /*[[${course.ResultSet.Course}]]*/[];
    if (routeIndex >= 0 && routeIndex < routes.length) {
        var route = routes[routeIndex];
        var content = "経路詳細\n";
        content += "所要時間: " + ('交通機関' + route.Route.timeOnBoard + '分・徒歩' + route.Route.timeWalk + route.Route.timeOther) + "分\n";
        content += "乗換回数: " + route.Route.transferCount + "回\n";
        content += "運賃: " + getFare(route) + "円\n\n";
        content += "経路:\n";

        route.Route.Point.forEach((point, index) => {
            content += point.Station.Name + "\n";
            if (index < route.Route.Point.length - 1) {
                var line = Array.isArray(route.Route.Line) ? route.Route.Line[index] : route.Route.Line;
                if (line) {
                    content += "↓ " + line.Name + " (" + line.timeOnBoard + "分)\n";
                }
            }
        });

        var blob = new Blob([content], {type: "text/plain;charset=utf-8"});
        var link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "route_" + (routeIndex + 1) + ".txt";
        link.click();
    }
}

function getFare(route) {
    if (route.Price && route.Price.length > 0) {
        var fareSummary = route.Price.find(price => price.kind === 'FareSummary');
        if (fareSummary) {
            return fareSummary.Oneway;
        }
        return route.Price[0].Oneway;
    }
    return '不明';
}

function saveRoute(routeIndex) {
    var routeCards = document.querySelectorAll('.route-card');
    if (routeIndex < 0 || routeIndex >= routeCards.length) {
        alert("無効な経路インデックスです");
        return;
    }

    var routeCard = routeCards[routeIndex];
    var nearestStationElement = document.querySelector('.station-name');
    var arrivalStationElement = routeCard.querySelector('.route-list li:last-child span');
    var nearestStation = nearestStationElement ? nearestStationElement.innerText.trim() : '不明';
    var arrivalStation = arrivalStationElement ? arrivalStationElement.innerText.trim() : '不明';
    var transportTimeElement = routeCard.querySelector('.travel-time-transportation');
    var walkTimeElement = routeCard.querySelector('.travel-time-walk');
    var transferCountElement = routeCard.querySelector('.transfer-count');
    var fareElement = routeCard.querySelector('.fare');
    var teiki1Element = routeCard.querySelector('.teiki1');
    var teiki3Element = routeCard.querySelector('.teiki3');
    var teiki6Element = routeCard.querySelector('.teiki6');

    var transportTime = transportTimeElement ? transportTimeElement.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var walkTime = walkTimeElement ? walkTimeElement.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var transferCount = transferCountElement ? transferCountElement.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var fare = fareElement ? fareElement.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var teiki1 = teiki1Element ? teiki1Element.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var teiki3 = teiki3Element ? teiki3Element.innerText.replace(/[^0-9]/g, '').trim() : '0';
    var teiki6 = teiki6Element ? teiki6Element.innerText.replace(/[^0-9]/g, '').trim() : '0';

    var routeList = routeCard.querySelectorAll('.route-list li');
    var routeInfo = Array.from(routeList).map(li => li.innerText.split('\n')[0].trim()).join(" → ") || '経路情報なし';

    var requestData = {
        username: sessionStorage.getItem('username'),  // セッションから取得
        nearest_station: nearestStation,
        arrival_station: arrivalStation,
        travelTimeTransportation: parseInt(transportTime, 10),
        travelTimeWalk: parseInt(walkTime, 10),
        transferCount: parseInt(transferCount, 10),
        fare: parseInt(fare, 10),
        teiki1: parseInt(teiki1, 10),
        teiki3: parseInt(teiki3, 10),
        teiki6: parseInt(teiki6, 10),
        routeInfo: routeInfo
    };

    console.log("送信データ:", requestData);

    fetch('/saveRoute', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestData)
    })
    .then(response => response.text())
    .then(data => alert(data))
    .catch(error => console.error('保存エラー:', error));
}


document.addEventListener('DOMContentLoaded', function () {
    showRoute(0);
});

