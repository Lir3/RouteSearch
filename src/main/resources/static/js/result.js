// 経路をテキストファイルとしてダウンロードする関数
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

		var blob = new Blob([content], { type: "text/plain;charset=utf-8" });
		var link = document.createElement("a");
		link.href = URL.createObjectURL(blob);
		link.download = "route_" + (routeIndex + 1) + ".txt";
		link.click();
	}
}

// 修正: 運賃を取得する関数を追加
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
