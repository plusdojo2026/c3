window.addEventListener("DOMContentLoaded", () => {

    let draggedItem = null;


    // すべてのバンドをドラッグ可能にする

    function enableDrag() {
        document.querySelectorAll(".btn.btn-primary").forEach(btn => {
            btn.setAttribute("draggable", "true");

            btn.addEventListener("dragstart", function () {
                draggedItem = this;
            });

            btn.addEventListener("dragend", function () {
                draggedItem = null;
            });
        });
    }

    enableDrag();


    // バンド配列から交互構造を構築

    function rebuildFromBandList(bandList) {
        const area = document.querySelector(".button");
        const convertTime = document.getElementById("convertTime").value;

        area.innerHTML = "";

        bandList.forEach(band => {
            const convert = document.createElement("button");
            convert.className = "btn btn-secondary";
            convert.textContent = `転換（${convertTime}分）`;
            convert.dataset.info = `転換時間：${convertTime}分`;

            area.appendChild(convert);
            area.appendChild(band);
        });

        enableDrag();
    }



    // drop を受け付けるエリア（.button と .info-box）

    const dropAreas = document.querySelectorAll(".button, .info-box, .box");

    dropAreas.forEach(area => {

        area.addEventListener("dragover", e => {
            e.preventDefault();
        });

        area.addEventListener("drop", function (e) {
            e.preventDefault();
            if (!draggedItem) return;

            if (e.target.classList.contains("btn-secondary")) {
    return;
}

            // info-box に落とした場合 → そのまま移動
            if (this.classList.contains("info-box")) {
                this.appendChild(draggedItem);
                return;
            }

            // タイムテーブルに落とした場合
            if (this.classList.contains("button")) {

                const mouseY = e.clientY;
                const bands = [...this.querySelectorAll(".btn.btn-primary")];
                const bandList = [...bands];

                // info-box から来た場合は末尾に追加
                if (!bandList.includes(draggedItem)) {
                    bandList.push(draggedItem);
                }

                // swap 対象を探す
                let targetBand = null;

                for (const band of bandList) {
                    if (band === draggedItem) continue;

                    const rect = band.getBoundingClientRect();
                    if (mouseY >= rect.top && mouseY <= rect.bottom) {
                        targetBand = band;
                        break;
                    }
                }

                // swap 対象が見つからない場合
                if (!targetBand) {
                    bandList.push(draggedItem);
                } else {
                        const i1 = bandList.indexOf(draggedItem);
                        const i2 = bandList.indexOf(targetBand);

                        const temp = bandList[i1];
                        bandList[i1] = bandList[i2];
                        bandList[i2] = temp;
                 }

                // swap 後に交互構造を構築
                rebuildFromBandList(bandList);
            }
        });
    });



    // 転換時間の一括変更

    document.getElementById("applyConvert").addEventListener("click", () => {
        const area = document.querySelector(".button");
        const bandList = [...area.querySelectorAll(".btn.btn-primary")];
        rebuildFromBandList(bandList);
    });



    // 初期ロード時に交互構造を作る

    const initialBands = [...document.querySelectorAll(".button .btn.btn-primary")];
    rebuildFromBandList(initialBands);

});

/* モーダル表示 */
function showBandDetail(name){

    const data = bandDetails[name];

    let html = `<h2>${name}</h2>`;

    data.forEach((s,i)=>{
        html += `
            <div class="song">
                <h3>${i+1}曲目</h3>
                <p>曲名：${s.song}</p>
                <p>照明：${s.light}</p>
                <p>音響：${s.sound}</p>
                <p>備考：${s.note}</p>
            </div>
        `;
    });

    document.getElementById("modalBody").innerHTML = html;
    document.getElementById("modal").style.display = "block";
}

/* モーダル閉じる */
function closeModal(){
    document.getElementById("modal").style.display = "none";
}

