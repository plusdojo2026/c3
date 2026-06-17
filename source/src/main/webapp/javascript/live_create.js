'use strict';

let live_create = document.getElementById('live_create');
let rowIndex = document.querySelectorAll("#bands_info_row.band_info_row").length;

// 入力欄を増やす
function addRow() {
    // 対象テーブルを取得
    const table = document.getElementById("bands_info_row");
    const newRow = table.insertRow(-1);
    const cell_band = newRow.insertCell(0);
    const cell_time = newRow.insertCell(1);
    const cell_btn = newRow.insertCell(2);
    newRow.className = "band_info_row";
    
    cell_band.innerHTML = `
        <input type="text" name="bandname[${rowIndex}]" placeholder="バンド名">
    `;
    cell_time.innerHTML = `
        <input type="text" name="time[${rowIndex}]" placeholder="持ち時間">
    `;
    cell_btn.innerHTML = `
        <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
        <button type="button" onclick="removeRow(this)"><img src="img/delete.svg" alt=""></button>
    `;
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

// 空欄エラー
live_create.onsubmit = function(event) {
    let blank = false;
    let setBandName;
    let setBandTime;
    for (let i = 0; i < rowIndex; i++) {
        setBandName = `bandname[${i}]`;
        setBandTime = `time[${i}]`;

        const bandName = live_create.querySelector('input[name="' + setBandName + '"]');
        const bandTime = live_create.querySelector('input[name="' + setBandTime + '"]');

        if (bandName && bandTime){
            if (bandName.value === '' || bandTime.value === '') {
                blank = true;
                break;
            } else {
                blank = false;
            }
        }
    }
    console.log('blank = ' + blank);

    if (live_create.live_name.value === '' || live_create.begin_date.value === '' || live_create.end_date.value === '' || blank) {
        document.getElementById('blank_alert').textContent = '未入力の項目があります。';
        event.preventDefault();
    } else if(document.querySelectorAll("#bands_info_row.band_info_row").length === 0) {
        document.getElementById('blank_alert').textContent = '参加バンドを最低1つ登録してください。';
        event.preventDefault();
	} else {
        document.getElementById('blank_alert').textContent = '';
        window.alert('一件のライブ情報を登録しました');
    }
};

