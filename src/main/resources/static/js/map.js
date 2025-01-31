// 出発時刻欄に現在時刻を設定
function departuresetCurrentTime() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('departure-time').value = `${hours}:${minutes}`;
}

// 到着時刻欄に現在時刻を設定
function arrivalsetCurrentTime() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('arrival-time').value = `${hours}:${minutes}`;
}

// 経由駅の入力フィールドを追加
function addViaField() {
    const container = document.getElementById('via-container');
    const inputRow = document.createElement('div');
    inputRow.className = 'input-row';

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.name = 'via[]';
    newInput.placeholder = '経由駅を入力';

    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.className = 'icon-button';
    removeButton.textContent = '-';
    removeButton.onclick = function () {
        container.removeChild(inputRow);
    };

    inputRow.appendChild(newInput);
    inputRow.appendChild(removeButton);
    container.appendChild(inputRow);
}

// 経路検索の実行
function searchRoute(event) {
    event.preventDefault(); // フォームのデフォルト送信を防ぐ

    const from = document.getElementById("from").value;
    const to = document.getElementById("to").value;
    const via = Array.from(document.querySelectorAll("input[name='via[]']"))
                     .map(input => input.value)
                     .filter(v => v.trim() !== ""); // 空の値を削除

    const departureTime = document.getElementById("departure-time").value;
    const arrivalTime = document.getElementById("arrival-time").value;

    fetchEkispertRoutes(from, to, via, departureTime, arrivalTime);
}

// Ekispert API で経路情報を取得
async function fetchEkispertRoutes(from, to, via, departureTime, arrivalTime) {
    try {
        const apiKey = "test_QMMh4JZJgnC";  // APIキー（仮）
        let viaList = via.length > 0 ? via.join(":") : "";
        let baseUrl = "https://api.ekispert.jp/v1/json/search/course/extreme";

        let params = `?key=${apiKey}&viaList=${from}:${viaList}:${to}`;

        if (departureTime) {
            const today = new Date().toISOString().slice(0, 10).replace(/-/g, '');
            params += `&date=${today}&time=${departureTime}&searchType=departure`;
        } else if (arrivalTime) {
            const today = new Date().toISOString().slice(0, 10).replace(/-/g, '');
            params += `&date=${today}&time=${arrivalTime}&searchType=arrival`;
        }

        const response = await fetch(baseUrl + params);
        const data = await response.json();

        if (data.ResultSet?.Course) {
            displayRouteDetails(data.ResultSet.Course);
        } else {
            alert("経路情報が取得できませんでした");
        }
    } catch (error) {
        console.error("Ekispert API エラー:", error);
        alert("経路検索に失敗しました");
    }
}

// 取得した経路情報を表示
function displayRouteDetails(routeData) {
    let routesHtml = "<h2>経路一覧</h2>";

    routeData.forEach((route, index) => {
        routesHtml += `
            <div class="route-card">
                <h3>パターン ${index + 1}</h3>
                <p>所要時間: ${route.Route.timeOnBoard}分・徒歩${route.Route.timeWalk}分</p>
                <p>乗換回数: ${route.Route.transferCount}回</p>
                <p>運賃: ${getFare(route)}円</p>
                <p>定期代: 1ヶ月 ${getTeikiPrice(route, 'Teiki1')}円, 3ヶ月 ${getTeikiPrice(route, 'Teiki3')}円, 6ヶ月 ${getTeikiPrice(route, 'Teiki6')}円</p>
            </div>`;
    });

    document.getElementById("routes").innerHTML = routesHtml;
}

// 運賃を取得
function getFare(route) {
    if (route.Price && route.Price.length > 0) {
        let fareSummary = route.Price.find(price => price.kind === 'FareSummary');
        return fareSummary ? fareSummary.Oneway : '不明';
    }
    return '不明';
}

// 定期代を取得
function getTeikiPrice(route, type) {
    if (route.Price && route.Price.length > 0) {
        let teikiPrice = route.Price.find(price => price.kind === type);
        return teikiPrice ? teikiPrice.Oneway : '不明';
    }
    return '不明';
}

// フォーム送信時に searchRoute を実行
document.getElementById("route-form").addEventListener("submit", searchRoute);
