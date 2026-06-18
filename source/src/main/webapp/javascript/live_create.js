'use strict';

let live_create = document.getElementById('live_create');
let rowIndex = document.querySelectorAll("tr.band_info_row").length;

if (rowIndex === 0 || rowIndex === null) {
	rowIndex = 1;
}

// 入力欄を増やす
function addRow() {
    // 対象テーブルを取得
    const table = document.getElementById("bands_info_row");
    const template = document.getElementById("band_row_template")

	// テンプレートをコピー
    const clone = template.content.cloneNode(true);
    
    // コピーした要素のname属性を書き換える
    clone.querySelector('select[name="band_infos_temp"]').name = `band_infos[${rowIndex}]`;
    clone.querySelector('input[name="time_temp"]').name = `time[${rowIndex}]`;
    
    table.appendChild(clone);
    
    console.log(rowIndex);
    const bandNum = table.querySelector('input[name="band_num"]');
    if (bandNum) {
        bandNum.value = rowIndex;
        rowIndex++;
    }
}

// 入力欄を消す
function removeRow(btn) {
    let row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

// 空欄エラー,入力エラー
live_create.onsubmit = function(event) {
    let blank = true;
    let setBandName;
    let setBandTime;
    for (let i = 0; i < rowIndex; i++) {
        setBandName = `band_infos[${i}]`;
        setBandTime = `time[${i}]`;

        const bandName = live_create.querySelector('select[name="' + setBandName + '"]');
        const bandTime = live_create.querySelector('input[name="' + setBandTime + '"]');

        if (bandName !=null && bandTime !=null){
			console.log(bandName.value);
            if (bandName.value === 0 || bandTime.value === 0) {
                blank = true;
                break;
            } else {
                blank = false;
            }
        }
    }
    
    console.log('blank = ' + blank);
    console.log(live_create.live_name.value);
    console.log(live_create.begin_date.value);
    console.log(live_create.end_date.value);

	if (live_create.live_name.value === null || live_create.live_name.value === '' || live_create.begin_date.value === null || live_create.end_date.value === null || blank) {
        document.getElementById('blank_alert').textContent = '未入力の項目があります。';
        event.preventDefault();
    } else if (live_create.begin_date.value >= live_create.end_date.value) {
		document.getElementById('blank_alert').textContent = '終了日時は開始日時より後に設定してください。';
		event.preventDefault();
	} else if (document.querySelectorAll("tr.band_info_row").length === 0) {
        document.getElementById('blank_alert').textContent = '参加バンドを最低1つ登録してください。';
        event.preventDefault();
	}  else {
        document.getElementById('blank_alert').textContent = '';
        window.alert('一件のライブ情報を登録しました');
   }	

};