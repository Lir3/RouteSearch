// 現在時刻を出発時刻フィールドに設定
//出発時刻欄　現在時刻取得
function departuresetCurrentTime() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('departure-time').value = `${hours}:${minutes}`;
}

//到着時刻欄　現在時刻取得
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
    removeButton.onclick = function() {
        container.removeChild(inputRow);
    };

    inputRow.appendChild(newInput);
    inputRow.appendChild(removeButton);
    container.appendChild(inputRow);
}
