const bands = [
    { name:"Aバンド", playTime:30 },
    { name:"Bバンド", playTime:30 },
    { name:"Cバンド", playTime:40 },
    { name:"Dバンド", playTime:30 }
];

const changeTime = 10;

/* ダミー詳細データ */
const bandDetails = {
    "Aバンド":[
        { song:"Song A", light:"青", sound:"通常", note:"MCあり" },
        { song:"Song B", light:"赤", sound:"強め", note:"ギターソロ" }
    ],
    "Bバンド":[
        { song:"Rock", light:"白", sound:"低音", note:"" }
    ],
    "Cバンド":[
        { song:"Summer", light:"黄", sound:"通常", note:"" }
    ],
    "Dバンド":[
        { song:"Final", light:"紫", sound:"エコー", note:"ラスト" }
    ]
};

drawSchedule();

function drawSchedule(){

    const schedule = document.getElementById("schedule");
    schedule.innerHTML = "";

    // ★毎回リセット（ここ重要）
    let time = { hour:13, minute:0 };

    bands.forEach((band,index)=>{

        let start = format(time.hour, time.minute);

        addMinute(time, band.playTime);

        let end = format(time.hour, time.minute);

        schedule.innerHTML += `
            <div class="band" data-name="${band.name}"
            onclick="showBandDetail('${band.name}')">
                <span>${band.name}</span>
                <span class="time">${start}～${end}</span>
            </div>
        `;

        if(index !== bands.length - 1){

            let changeStart = end;

            addMinute(time, changeTime);

            let changeEnd = format(time.hour, time.minute);

            schedule.innerHTML += `
                <div class="change">
                    転換時間
                    <span class="time">${changeStart}～${changeEnd}</span>
                </div>
            `;
        }
    });

    makeSortable();
}

function addMinute(time, min){

    time.minute += min;

    while(time.minute >= 60){
        time.hour++;
        time.minute -= 60;
    }
}

function format(h,m){
    return String(h).padStart(2,"0") + ":" + String(m).padStart(2,"0");
}

/* 並び替え */
function makeSortable(){

    new Sortable(document.getElementById("schedule"),{

        animation:150,
        filter:".change",

        onEnd:function(){

            const bandElements = document.querySelectorAll(".band");

            let newBands = [];

            bandElements.forEach(el=>{

                let name = el.innerText.split("\n")[0].trim();

                let band = bands.find(b=>b.name===name);

                newBands.push(band);
            });

            bands.length = 0;
            bands.push(...newBands);

            drawSchedule();
        }
    });
}

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
}/**
 * 
 */