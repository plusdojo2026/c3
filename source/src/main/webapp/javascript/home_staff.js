drawSchedule();

function drawSchedule(){

    const schedule = document.getElementById("schedule");
    schedule.innerHTML = "";

    const start = new Date(liveStart);

    let time = {
        hour: start.getHours(),
        minute: start.getMinutes()
    };

    bands.forEach((band,index)=>{

    // そのバンドの転換時間を先に表示
    // 2組目以降だけ転換時間を表示
if(index > 0 && band.changeTime > 0){

    let changeStart = format(time.hour, time.minute);

    addMinute(time, band.changeTime);

    let changeEnd = format(time.hour, time.minute);

    schedule.innerHTML += `
        <div class="change">
            転換時間
            <span class="time">${changeStart}～${changeEnd}</span>
        </div>
    `;
}

    let start = format(time.hour, time.minute);

    addMinute(time, band.playTime);

    let end = format(time.hour, time.minute);

    schedule.innerHTML += `
        <div class="band"
             data-name="${band.name}"
             onclick="showBandDetail(${band.id})">
            <span>${band.name}</span>
            <span class="time">${start}～${end}</span>
        </div>
    `;
});

    //makeSortable();
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
/*function makeSortable(){

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
}*/

/* モーダル表示 */
function showBandDetail(bandId){

    console.log("bandId =", bandId);

    const data = bandPreparInfos[bandId];

    console.log("data =", data);

    if (!data || data.length === 0) {
        document.getElementById("modalBody").innerHTML =
            "<p>準備情報がありません。</p>";
        document.getElementById("modal").style.display = "block";
        return;
    }

    let html = `<h2>準備情報</h2>`;

    data.forEach((p,i)=>{
        html += `
            <div class="song">
                <h3>${p.setlist}番目</h3>
                <p>持ち時間：${p.time}分</p>
                <p>準備時間：${p.preparTime}分</p>
                <p>入場曲：${p.entranceMusic}</p>
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
