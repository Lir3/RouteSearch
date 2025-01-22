// 経由地店のテキストボックスを追加する
document.getElementById("addvia-button").addEventListener("click", function(event) {
    event.preventDefault();

    const container = document.getElementById("textBoxContainer");
    const textBoxCount = container.getElementsByTagName("input").length;

    // テキストボックスが5個未満のときに追加
    if (textBoxCount < 5) {
        const newTextBox = document.createElement("input");
        newTextBox.type = "text";
        newTextBox.placeholder = `経由 ${textBoxCount + 1}`;
        container.appendChild(newTextBox);
    } else {
        alert("経由は最大5つまで追加可能です。");
    }
});

// 最後に追加したテキストボックスを削除する
document.getElementById("removevia-button").addEventListener("click", function(event) {
    event.preventDefault();

    const container = document.getElementById("textBoxContainer");
    const textBoxes = container.getElementsByTagName("input");

    // テキストボックスがある場合のみ最後のものを削除
    if (textBoxes.length > 0) {
        container.removeChild(textBoxes[textBoxes.length - 1]);
    } else {
        alert("削除するテキストボックスがありません。");
    }
});