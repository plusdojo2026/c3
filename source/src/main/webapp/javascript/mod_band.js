'use strict';

let band_member = document.getElementById('band_member');
let rowIndex = 1;

// 入力欄を増やす
function addRow() {
    // 対象テーブルを取得
    const table = document.getElementById("members_info_row");
    const newRow = table.insertRow(-1);
    const cell_name = newRow.insertCell(0);
    const cell_part = newRow.insertCell(1);
    const cell_btn = newRow.insertCell(2);
    cell_name.innerHTML = `
        <input type="text" name="member_name[${rowIndex}]" placeholder="氏名">
    `;
    cell_part.innerHTML = `
        <select name="parts[${rowIndex}]" id="parts">
        <option value="">--選択してください--</option>
        </select>
    `;
    cell_btn.innerHTML = `
        <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
        <button type="button" onclick="removeRow(this)"><img src="img/delete.svg" alt=""></button>
    `;
    const memberNum = table.querySelector('input[name="member_num"]');
    if (memberNum) {
        memberNum.value = rowIndex;
        rowIndex++;
    }
}

// 入力欄を消す
function removeRow(btn) {
    let row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

// 空欄エラー
band_member.onsubmit = function(event) {
    let blank = false;
    let setMemberName;
    let setMemberParts;
    for (let i = 0; i < rowIndex; i++) {
        setMemberName = `member_name[${i}]`;
        setMemberParts = `parts[${i}]`;
        console.log(setMemberName);
        console.log(setMemberParts);

        const memberName = band_member.querySelector('input[name="' + setMemberName + '"]');
        const memberParts = band_member.querySelector('select[name="' + setMemberParts + '"]');
        console.log(memberName);
        console.log(memberParts);

        if (memberName && memberParts){
            if (memberName.value === '' || memberParts.value === '') {
                blank = true;
                break;
            } else {
                blank = false;
            }
        }
    }

    if (band_member.band_name.value === '' || blank) {
        document.getElementById('blank_alert').textContent = '未入力の項目があります。';
        event.preventDefault();
    } else {
        document.getElementById('blank_alert').textContent = '';
        window.alert('一件のバンド情報を登録しました');
    }
};