      /*  1曲分の入力欄（曲セット）を生成する関数*/
function createMusicSet() {

    const div = document.createElement("div");
    div.className = "music-set";

    div.innerHTML = `
        <div>
            <label>曲順</label>
            <input type="text" name="each_setlist[]" class="order" readonly>
        </div>

        <div>
            <label class="required">曲名</label>
            <input type="text" name="each_name[]">
        </div>

        <div>
            <label class="required">音響</label>
            <input type="text" name="se[]">
        </div>

        <div>
            <label class="required">照明</label>
            <input type="text" name="light_req[]">
        </div>

        <div>
            <label>備考</label>
            <input type="text" name="memo[]">
        </div>

        <div class="btn-area">
            <button type="button" class="removeBtn">削除</button>
            <button type="button" class="addBtn">追加</button>
        </div>
    `;

    div.querySelector(".removeBtn").onclick = () => {
        div.remove();
        updateOrderNumbers();
    };

    div.querySelector(".addBtn").onclick = () => {
        const newSet = createMusicSet();
        div.after(newSet);
        updateOrderNumbers();
    };

    return div;
}

/*曲順を 1曲目・2曲目… と自動採番する関数 */
function updateOrderNumbers() {
    const orders = document.querySelectorAll('.order');
    orders.forEach((input, index) => {
        input.value = (index + 1);
    });
}

document.addEventListener("DOMContentLoaded", () => {

    /*ページ読み込み時：最初の1曲を追加*/
    document.getElementById("musicSets").appendChild(createMusicSet());
    updateOrderNumbers();

    /*＋追加ボタン：曲セットを追加 */
    document.getElementById("addSetBtn").onclick = () => {
        document.getElementById("musicSets").appendChild(createMusicSet());
        updateOrderNumbers();
    };

    /*フォーム送信時の必須チェック*/
    let formObj = document.getElementById('prepar_form');
    let errorMessageObj = document.getElementById('error_message');

    formObj.onsubmit = function (event) {

        let musicSets = document.getElementsByClassName("music-set");
        if (musicSets.length === 0) {
            errorMessageObj.textContent = '※曲を1曲以上登録してください。';
            event.preventDefault();
            return;
        }

        if (formObj.entrance_music.value === '') {
            errorMessageObj.textContent = '※入場曲を入力してください。';
            event.preventDefault();
            return;
        }


        let names = document.getElementsByName("each_name[]");
        let ses = document.getElementsByName("se[]");
        let lights = document.getElementsByName("light_req[]");

        for (let i = 0; i < names.length; i++) {

            if (names[i].value === '') {
                errorMessageObj.textContent = `※${i + 1}曲目：曲名を入力してください。`;
                event.preventDefault();
                return;
            }

            if (ses[i].value === '') {
                errorMessageObj.textContent = `※${i + 1}曲目：音響を入力してください。`;
                event.preventDefault();
                return;
            }

            if (lights[i].value === '') {
                errorMessageObj.textContent = `※${i + 1}曲目：照明を入力してください。`;
                event.preventDefault();
                return;
            }
        }

        if (!confirm("入力内容を送信します、本当によろしいですか？")) {
            event.preventDefault();
        }
    };

    formObj.onreset = function () {
        errorMessageObj.textContent = '';
    };

});
