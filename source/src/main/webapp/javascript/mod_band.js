'use strict';

let band_member = document.getElementById('band_member');
let rowIndex = document.querySelectorAll("#bands_info_row.band_info_row").length;

if (rowIndex === 0) {
	rowIndex = 1;
}

// 入力欄を増やす
function addRow() {
    // 対象テーブルを取得
    const table = document.getElementById("members_info_row");
    const template = document.getElementById("band_row_template");
    
    // テンプレートをコピー
    const clone = template.content.cloneNode(true);
    
    // コピーした要素のname属性を現在のrowIndexで上書き
    clone.querySelector('input[name="membername_temp"]').name = `member_name[${rowIndex}]`;
    clone.querySelector('input[name="parts_temp"]').name = `parts[${rowIndex}]`;
    
    table.appendChild(clone);
    
    const memberNum = table.querySelector('input[name="member_num"]');
    if (memberNum) {
        memberNum.value = rowIndex;
    }
    rowIndex++;
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