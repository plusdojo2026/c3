let bands = [
    { name: "バンドA", time: 20 },
    { name: "バンドB", time: 25 },
    { name: "バンドC", time: 30 },
    { name: "バンドD", time: 15 }
];

let convertTime = 10;

let dragSrcIndex = null;
let dragSrcArea = null; // "prep" or "table"

// 転換時間設定
function applyConvertTime() {
    convertTime = parseInt(document.getElementById("convertTime").value);
    renderTimetable();
}

/* ---------------------------
   タイムテーブル描画
---------------------------- */
function renderTimetable() {
    const list = document.getElementById("timetableList");
    list.innerHTML = "";

    bands.forEach((band, index) => {
        const div = document.createElement("div");
        div.className = "button";
        div.dataset.index = index;
        div.dataset.area = "table";
        div.setAttribute("draggable", "true");

        div.addEventListener("dragstart", handleDragStart);
        div.addEventListener("dragover", handleDragOver);
        div.addEventListener("drop", handleDrop);
        div.addEventListener("dragend", handleDragEnd);

        div.innerHTML = `
            <div class="btn btn-primary"
                 onmouseover="showTooltip(event, '${band.name}：${band.time}分')"
                 onmouseout="hideTooltip()">
                ${index + 1}. ${band.name}（${band.time}分）
            </div>

            <div class="btn btn-secondary">
                転換 ${convertTime} 分
            </div>
        `;

        list.appendChild(div);
    });
}

/* ---------------------------
   準備情報ボックス描画
---------------------------- */
function renderPrepList() {
    const prep = document.getElementById("prepList");
    prep.innerHTML = "";

    bands.forEach((band, index) => {
        const btn = document.createElement("div");
        btn.className = "btn btn-primary";
        btn.style.width = "350px";
        btn.style.margin = "5px auto";
        btn.textContent = band.name;

        btn.dataset.index = index;
        btn.dataset.area = "prep";
        btn.setAttribute("draggable", "true");

        btn.addEventListener("dragstart", handleDragStart);
        btn.addEventListener("dragover", handleDragOver);
        btn.addEventListener("drop", handleDrop);
        btn.addEventListener("dragend", handleDragEnd);

        prep.appendChild(btn);
    });
}

/* ---------------------------
   ドラッグ＆ドロップ共通処理
---------------------------- */
function handleDragStart(e) {
    dragSrcIndex = Number(this.dataset.index);
    dragSrcArea = this.dataset.area;
    this.style.opacity = "0.4";
}

function handleDragOver(e) {
    e.preventDefault();
}

function handleDrop(e) {
    e.preventDefault();

    const dropIndex = Number(this.dataset.index);
    const dropArea = this.dataset.area;

    // 同じ場所・同じ要素なら何もしない
    if (dragSrcIndex === dropIndex && dragSrcArea === dropArea) return;

    // ★ 入れ替え（swap）方式 ★
    const temp = bands[dragSrcIndex];
    bands[dragSrcIndex] = bands[dropIndex];
    bands[dropIndex] = temp;

    renderPrepList();
    renderTimetable();
}

function handleDragEnd() {
    this.style.opacity = "1";
}

/* ---------------------------
   ツールチップ
---------------------------- */
function showTooltip(e, text) {
    const tooltip = document.getElementById("tooltip");
    tooltip.textContent = text;
    tooltip.style.opacity = 1;
    tooltip.style.left = e.pageX + 10 + "px";
    tooltip.style.top = e.pageY + 10 + "px";
}

function hideTooltip() {
    document.getElementById("tooltip").style.opacity = 0;
}

/* ---------------------------
   その他
---------------------------- */
function exportTimetable() {
    alert("タイムテーブルを出力します（処理は後で実装）");
}

function clearAll() {
    if (confirm("すべて削除しますか？")) {
        bands = [];
        renderTimetable();
        renderPrepList();
    }
}

/* 初期表示 */
renderPrepList();
renderTimetable();
