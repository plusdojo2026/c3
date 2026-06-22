// ---------------------------
// バンド詳細データ（★ここに追加）
// ---------------------------
const bandDetails = {
    "バンドA": [
        { song: "曲A1", light: "赤", sound: "強め", note: "特になし" },
        { song: "曲A2", light: "青", sound: "弱め", note: "MCあり" },
        { song: "曲A3", light: "青", sound: "弱め", note: "MCあり" },
        { song: "曲A4", light: "青", sound: "弱め", note: "MCあり" }
    ],
    "バンドB": [],
    "バンドC": [],
    "バンドD": []
};

// ---------------------------
// グローバル変数
// ---------------------------
let prepBands = [
    { name: "バンドA", time: 20 },
    { name: "バンドB", time: 25 },
    { name: "バンドC", time: 30 },
    { name: "バンドD", time: 15 }
];

let tableBands = [];
let convertTime = 10;


/* ---------------------------
   モーダル表示
---------------------------- */
function showBandDetail(name) {

    const data = bandDetails[name] || [];

    let html = `<h2>${name}</h2>`;

    data.forEach((s, i) => {
        html += `
            <div class="song">
                <h3>${i + 1}曲目</h3>
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

/* ---------------------------
   モーダル閉じる
---------------------------- */
function closeModal() {
    document.getElementById("modal").style.display = "none";
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
    const tooltip = document.getElementById("tooltip");
    tooltip.style.opacity = 0;
}

/* ---------------------------
   転換時間設定
---------------------------- */
function applyConvertTime() {
    let value = parseInt(document.getElementById("convertTime").value);

    // マイナス値を防止
    if (value < 0 || isNaN(value)) {
        value = 0;
        document.getElementById("convertTime").value = 0;
    }

    convertTime = value;
    renderTimetable();
}


/* ---------------------------
   準備情報ボックス描画
---------------------------- */
function renderPrepList() {
    const prep = document.getElementById("prepList");
    prep.innerHTML = "";

    prepBands.forEach((band, index) => {
        const btn = document.createElement("div");
        btn.className = "btn btn-primary";
        btn.style.width = "350px";
        btn.style.margin = "2px auto";
        btn.textContent = band.name;

        btn.dataset.area = "prep";
        btn.dataset.index = index;
        btn.setAttribute("draggable", "true");

        // ツールチップ
        btn.addEventListener("mousemove", (e) => {
            showTooltip(e, `${band.name}（${band.time}分）`);
        });
        btn.addEventListener("mouseout", hideTooltip);

        // D&D
        btn.addEventListener("dragstart", handleDragStart);
        btn.addEventListener("dragover", handleDragOver);
        btn.addEventListener("drop", handleDrop);
        btn.addEventListener("dragend", handleDragEnd);
        
        //モーダル
        btn.addEventListener("click", () => {
    		showBandDetail(band.name);
		});


        prep.appendChild(btn);
    });
}

/* ---------------------------
   タイムテーブル描画
---------------------------- */
function renderTimetable() {
    const list = document.getElementById("timetableList");
    list.innerHTML = "";

    const times = calculateTimes();

    tableBands.forEach((band, index) => {

        const wrapper = document.createElement("div");
        wrapper.className = "button";

        const mainBtn = document.createElement("div");
        mainBtn.className = "btn btn-primary";

        const timeText = times[index]
            ? `【${times[index].start} - ${times[index].end}】`
            : "";

        mainBtn.textContent = `${index + 1}. ${band.name}（${band.time}分） ${timeText}`;

        mainBtn.dataset.area = "table";
        mainBtn.dataset.index = index;
        mainBtn.setAttribute("draggable", "true");

        // ツールチップ
        mainBtn.addEventListener("mousemove", (e) => {
            showTooltip(e, `${band.name}（${band.time}分）`);
        });
        mainBtn.addEventListener("mouseout", hideTooltip);

        // D&D
        mainBtn.addEventListener("dragstart", handleDragStart);
        mainBtn.addEventListener("dragover", handleDragOver);
        mainBtn.addEventListener("drop", handleDrop);
        mainBtn.addEventListener("dragend", handleDragEnd);
        
        // モーダル
        mainBtn.addEventListener("click", () => {
    		showBandDetail(band.name);
		});


        const convertBtn = document.createElement("div");
        convertBtn.className = "btn btn-secondary";
        convertBtn.textContent = `転換 ${convertTime} 分`;

        wrapper.appendChild(convertBtn);
        wrapper.appendChild(mainBtn);

        list.appendChild(wrapper);
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

    const dropArea = this.dataset.area;
    const dropIndex = Number(this.dataset.index);

    if (dragSrcArea === dropArea && dragSrcIndex === dropIndex) return;

    if (dragSrcArea === "prep" && dropArea === "table") {
        const moved = prepBands.splice(dragSrcIndex, 1)[0];
        tableBands.splice(dropIndex, 0, moved);
    }
    else if (dragSrcArea === "table" && dropArea === "prep") {
        const moved = tableBands.splice(dragSrcIndex, 1)[0];
        prepBands.splice(dropIndex, 0, moved);
    }
    else if (dragSrcArea === "table" && dropArea === "table") {
        const temp = tableBands[dragSrcIndex];
        tableBands[dragSrcIndex] = tableBands[dropIndex];
        tableBands[dropIndex] = temp;
    }

    renderPrepList();
    renderTimetable();
}

function handleDragEnd() {
    this.style.opacity = "1";
}

/* ---------------------------
   空白ドロップ（末尾追加）
---------------------------- */
function handleTableAreaDrop(e) {
    e.preventDefault();

    if (
        e.target.classList.contains("btn-primary") ||
        e.target.classList.contains("btn-secondary")
    ) {
        return;
    }

    let moved = null;

    if (dragSrcArea === "prep") {
        moved = prepBands.splice(dragSrcIndex, 1)[0];
        if (moved) tableBands.push(moved);
    } 
    else if (dragSrcArea === "table") {
        moved = tableBands.splice(dragSrcIndex, 1)[0];
        if (moved) prepBands.push(moved);
    }

    renderPrepList();
    renderTimetable();
}

function handleTableAreaDragOver(e) {
    e.preventDefault();
}

/* ---------------------------
   保存（Servlet に送信）
---------------------------- */
function exportTimetable() {

    // ★ 保存確認ダイアログ
    if (!confirm("保存しますか？")) {
        return; // キャンセルされたら何もしない
    }

    fetch("TableCreateServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            convertTime: convertTime,
            tableBands: tableBands,
            prepBands: prepBands
        })
    })
    .then(res => res.text())
    .then(msg => alert("保存しました"))
    .catch(err => alert("保存に失敗しました"));
}


/* ---------------------------
   時間計算
---------------------------- */
function calculateTimes() {
    const startInput = document.getElementById("startTime").value;
    if (!startInput) return [];

    let [hour, minute] = startInput.split(":").map(Number);
    let current = hour * 60 + minute;

    const result = [];

    tableBands.forEach(band => {
        const start = current;
        const end = current + band.time;

        result.push({
            name: band.name,
            start: formatTime(start),
            end: formatTime(end)
        });

        current = end + convertTime;
    });

    return result;
}

function formatTime(totalMinutes) {
    const h = Math.floor(totalMinutes / 60);
    const m = totalMinutes % 60;
    return `${String(h).padStart(2, "0")}:${String(m).padStart(2, "0")}`;
}


/*　削除ボタン */

function cancelPage() {
    if (confirm("変更を破棄してリセットしますか？")) {
        location.reload();
    }
}


/* 初期表示 */
renderPrepList();
renderTimetable();

