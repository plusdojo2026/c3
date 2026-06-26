'use strict';

let live_create = document.getElementById('live_create');
let rowIndex = document.querySelectorAll("tr.band_info_row").length;
console.log("rowIndex:" + rowIndex);

if (rowIndex === 0 || rowIndex === null) {
	rowIndex = 1;
}

// begin_date, end_dateの最低値設定
console.log("日付を設定します");
const date = new Date();
const localDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString().slice(0, 16);
console.log("date:" + localDate);
document.getElementById("begin_date").min = localDate;
document.getElementById("end_date").min = localDate;

// 検索付きセレクトボックスの初期化
function initSearchSelect(rowElement) {
	const searchInput = rowElement.querySelector('.search_input');
	const optionsList = rowElement.querySelector('.options_list');
	const realSubmitValue = rowElement.querySelector('.real_submit_value');
	
	if (searchInput === null || optionsList === null || realSubmitValue === null) {
		return;
	}
	
	const options = optionsList.getElementsByTagName('li');
	
	// リストに表示
	searchInput.addEventListener('focus', () => {
		// 一旦リストを閉じてから再度開く
		document.querySelectorAll('.options_list.show').forEach(el => el.classList.remove('show'));
		optionsList.classList.add('show');
	});
	
	// 絞り込み検索
	searchInput.addEventListener('input', () => {
		const filter = searchInput.value.toLowerCase();
		let hasMatch = false;
		
		const existingNoResult = optionsList.querySelector('.no-result');
		if (existingNoResult) existingNoResult.remove();
		
		for (let i = 0; i < options.length; i++) {
			// 左側が空なら右側の値を使う
			const text = options[i].textContent || options[i].innerText;
			if (text.toLowerCase().indexOf(filter) > -1) {
				options[i].style.display = "";
				hasMatch = true;
			} else {
				options[i].style.display = "none";
			}
		}
		
		if (!hasMatch) {
			const noResultLi = document.createElement('li');
			noResultLi.className = 'no-result';
			noResultLi.textContent = '見つかりません';
			optionsList.appendChild(noResultLi);
		}
	});
	
	// 選択肢クリック時の処理
	optionsList.addEventListener('click', (e) =>{
		if (e.target && e.target.nodeName === 'LI' && !e.target.classList.contains('no-result')) {
			searchInput.value = e.target.textContent;
			//	'data-value'の値をreal_submit_valueクラスが付与された部分に挿入する
			realSubmitValue.value = e.target.getAttribute('data-value');
			optionsList.classList.remove('show');
		}
	});
}
	
	// リストを閉じる
	document.addEventListener('click', (e) => {
		const containers = document.querySelectorAll('.search_select_container');
		containers.forEach(container => {
			if (!container.contains(e.target)) {
				const optionsList = container.querySelector('.options_list');
				const searchInput = container.querySelector('.search_input');
				const realSubmitValue = container.querySelector('.real_submit_value');
				
				if (optionsList)
					optionsList.classList.remove('show');
				
				if (realSubmitValue && realSubmitValue.value === "" && searchInput) {
					searchInput.value = "";
				}
			}
		})
	});

// 入力欄を増やす
function addRow() {
    // 対象テーブルを取得
    const table = document.getElementById("bands_info_row");
    const template = document.getElementById("band_row_template")

	// テンプレートをコピー
    const clone = template.content.cloneNode(true);
        
    // コピーした要素のname属性を書き換える
   	clone.querySelector('input[name="band_infos_temp"]').name = `band_infos[${rowIndex}]`;
   	clone.querySelector('input[name="time_temp"]').name = `time[${rowIndex}]`;
    clone.querySelector('input[name="band_input_temp"]').name = `band_input[${rowIndex}]`;
    
    table.appendChild(clone);
    
    // 検索機能を有効にする
    const addedRows = table.querySelectorAll("tr.band_info_row");
    const newRow = addedRows[addedRows.length -1];
    if (newRow) {
	initSearchSelect(newRow);
}
    
    console.log("rowIndex:" + rowIndex);
    const bandNum = live_create.querySelector('input[name="band_num"]');
    if (bandNum != null) {
        bandNum.value = rowIndex;
        rowIndex++;
    }
    console.log("bandNum.value:" + bandNum.value);
}

// 最初から画面に存在する行を一括で初期化する
document.querySelectorAll('tr.band_info_row').forEach(row => {
	initSearchSelect(row);
});

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
    let setBandInput;
    band_member.querySelector('input[name="band_num"]').value = rowIndex;
    for (let i = 0; i < rowIndex; i++) {
        setBandName = `band_infos[${i}]`;
        setBandTime = `time[${i}]`;
        setBandInput = `band_input[${i}]`;
        
        const bandName = live_create.querySelector('input[name="' + setBandName + '"]');
        const bandTime = live_create.querySelector('input[name="' + setBandTime + '"]');
        const bandInput = live_create.querySelector('input[name="' + setBandInput + '"]');
        console.log("name:" + live_create.live_name.value);
	    console.log("begin_date:" + live_create.begin_date.value);
 	    console.log("end_date:" + live_create.end_date.value);

        if (bandName !=null && bandTime !=null){
			console.log("bandName:" + bandName.value);
			console.log("bandTimes:" + bandTime.value);
			console.log("bandInput:" + bandInput.value);
            if (!bandName.value || !bandTime.value || bandName.value === "0" || bandTime.value === "0" || !bandInput.value) {
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

	if (!live_create.live_name.value || !live_create.begin_date.value || !live_create.end_date.value || blank) {
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