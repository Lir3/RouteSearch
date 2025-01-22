// ボタンと非表示にする要素を取得
const commuterpass = document.getElementById('commuterpass');
const commuterElement = document.getElementById('commuterElement');

// ボタンクリック時のイベントリスナーを設定
commuterpass.addEventListener('click', () => {
    // 要素の表示状態を切り替える
    if (commuterElement.style.display === 'none' || commuterElement.style.display === '') {
        commuterElement.style.display = 'block';
    } else {
        commuterElement.style.display = 'none';
    }
});

function swapText() {
    // テキストボックスの要素を取得
    var box1 = document.getElementById("startpracebox");
    var box2 = document.getElementById("arrivalpracebox");

    // テキストの内容を一時保存して入れ替える
    var temp = box1.value;
    box1.value = box2.value;
    box2.value = temp;
}

