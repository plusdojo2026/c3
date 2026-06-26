drawSchedule();

function drawSchedule() {

	const schedule = document.getElementById("schedule");
	schedule.innerHTML = "";

	// ★毎回リセット（ここ重要）
	const timeschedule_hour = document.getElementById("begin_date_hour");
	const timeschedule_min = document.getElementById("begin_date_min");
	
	let time = {};
	
		if (timeschedule_hour != null && timeschedule_min != null) {
		console.log("テスト");
		time= {
			hour: timeschedule_hour.value,
			minute: timeschedule_min.value
		};
	}

	bands.forEach((band, index) => {

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

		if (index !== bands.length - 1) {

			let changeStart = end;

			addMinute(time, band.changeTime);

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

function addMinute(time, min) {
	
	time.hour = Number(time.hour);
	time.minute = Number(time.minute);
	const addMin = Number(min);

	time.minute += addMin;

	while (time.minute >= 60) {
		time.hour++;
		time.minute -= 60;
	}
}

function format(h, m) {
	return String(h).padStart(2, "0") + ":" + String(m).padStart(2, "0");
}

