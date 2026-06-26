// ---------------------------
// バンド詳細データ（★ここに追加）
// ---------------------------
let bandDetails = {};

// ---------------------------
// グローバル変数
// ---------------------------
let prepBands = [];

let tableBands = [];
let convertTime = 10;


/* ---------------------------
   モーダル表示
---------------------------- */
function showBandDetail(bandName) {

/*    // バンドIDを取得
    const band = bandInfos.find(b => b.name === bandName);
    if (!band) return;

    // 準備情報を取得
    const prep = preparInfos.find(p => p.bandId === band.id);

    let html = `<h2>${bandName}</h2>`;

    html += `<h3>準備情報</h3>`;
    html += `<p>${prep ? prep.items : "準備情報がありません"}</p>`;*/

	const data = bandDetails[bandName] || [];

    const prepInfo = prepBands.find(b => b.name === bandName);

    let html = `<h2>${bandName}</h2>`;
    
    if (prepInfo) {
        html += `
            <div class="prep-info" style="background:#f4f4f4; padding:10px; margin-bottom:15px; border-radius:4px;">
                <p><strong>持ち時間：</strong>${prepInfo.time}分</p>
                <p><strong>準備項目：</strong>${prepInfo.item || "特になし"}</p>
                <p><strong>入場曲：</strong>${prepInfo.music || "特になし"}</p>
            </div>
        `;
    }
    
    html += `<hr>`;

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
        
        btn.dataset.id = band.id; 

        // ツールチップ
        btn.addEventListener("mousemove", (e) => {
            showTooltip(e, `${band.name}（${band.time}分）`) ;
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

        mainBtn.dataset.id = band.id; 
        
        // ツールチップ
        mainBtn.addEventListener("mousemove", (e) => {
            showTooltip(e, `${band.name}（${band.time}分）`) ;
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
	
	    // 時間超過チェック
    const times = calculateTimes();
    const endTime = document.getElementById("end_date");
    
    if (times.length > 0 && endTime && endTime.value) {
		const bandEnd = times[times.length - 1].end;
		let limitTime = endTime.value;
		
		if (limitTime.includes("T")) {
			limitTime = limitTime.split("T")[1];
		}
		
		const [bandH, bandM] = bandEnd.split(":").map(Number);
		const [ltH, ltM] = limitTime.split(":").map(Number);
		console.log("バンド：", (bandH * 60 + bandM));
		console.log("決まり：", (ltH * 60 + ltM));
		
		if ((bandH * 60 + bandM) > (ltH * 60 + ltM)) {
			alert("時間が超過しています。");
			return;
		}
		
	}

    // ★ 保存確認ダイアログ
    if (!confirm("保存しますか？")) {
        return; // キャンセルされたら何もしない
    }

	const params = new URLSearchParams();
	// 転換時間をセット
	params.append("time", convertTime);
	// live_info_idをセット
	params.append("live_info_id", document.getElementById("live_info_id").value);

	// 開始時間の作成
	const originDate = document.getElementById("originDate").value;
	const startTime = document.getElementById("startTime").value;
	const localDateTimeStr = `${originDate}T${startTime}`;
	
	console.log("originDate:", originDate);
	console.log("startTime:", startTime);
	console.log("localDateTimeStr:", localDateTimeStr);
	
	params.append("start_date", localDateTimeStr);

	// 順番でprepar_info_idを得る
	tableBands.forEach(band => {
		params.append("prepar_info_id", band.id);
	})

    fetch("TableCreateServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: params.toString()
    })
	.then(res => {
		if (!res.ok) {
            throw new Error(`サーバーエラー: ${res.status}`);
        }
        return res.text();
	})
    .then(msg => {
	alert("作成しました");
	window.location.href = "HomeAdminServlet";
	})
    .catch(err => {
		console.error(err);
		alert("作成に失敗しました。:" + err.message);
});
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
        const end = current + Number(band.time);

        result.push({
            name: band.name,
            start: formatTime(start),
            end: formatTime(end)
        });

        current = end + Number(convertTime);
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

function showBandDetail(bandName) {

/*    // バンドIDを取得
    const band = bandInfos.find(b => b.name === bandName);
    if (!band) return;

    // 準備情報を取得
    const prep = preparInfos.find(p => p.bandId === band.id);

    let html = `<h2>${bandName}</h2>`;

    html += `<h3>準備情報</h3>`;
    html += `<p>${prep ? prep.info : "準備情報がありません"}</p>`;*/

	const data = bandDetails[bandName] || [];

    const prepInfo = prepBands.find(b => b.name === bandName);

    let html = `<h2>${bandName}</h2>`;
    
    if (prepInfo) {
        html += `
            <div class="prep-info" style="background:#f4f4f4; padding:10px; margin-bottom:15px; border-radius:4px;">
                <p><strong>転換時間：</strong>${prepInfo.time}分</p>
                <p><strong>準備項目：</strong>${prepInfo.item || "特になし"}</p>
                <p><strong>入場曲：</strong>${prepInfo.music || "特になし"}</p>
            </div>
        `;
    }
    
    html += `<hr>`;
    
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


/* 初期表示 */
renderPrepList();
renderTimetable();

