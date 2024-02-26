$(function() {
			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				//풀캘린더 부분 설정
				fixedWeekCount: false,
				initialView: 'dayGridMonth',
				slotMinTime: '08:00',
				slotMaxTime: '20:00',
				editable: true,
				selectable: true,
				dayMaxEvents: true,
				displayEventTime: true,
				locale: 'ko',

				//풀캘린더 헤더 부분쪽 설정
				headerToolbar: {
					left: 'prev,next,today',
					center: 'title',
					right: 'dayGridMonth,timeGridWeek,timeGridDay'
				},
				buttonText: {
					today: '오늘',
					month: '월간',
					week: '주간',
					day: '일간',
					list: '리스트'
				},
				events: [
					{
						title: '노원구 야시장',
						start: '2024-02-05',
						end: '2024-02-07'
					},
					{
						title: '노원 힐링 걷기',
						start: '2024-02-14',
						end: '2024-02-17'
					},
					{
						title: '야시장',
						start: '2024-02-22',
						end: '2024-02-25'
					},
					{
						title: '정월대보름행사',
						start: '2024-02-24',
						end: '2024-02-24'
					}
				],
				//날짜 정보 수정
				titleFormat: function(date) {
					year = date.date.year;
					month = date.date.month + 1;
					return year + "년 " + month + "월";
				},
				dayCellContent: function(arg) {
					var date = arg.date;
					var dayOfMonth = date.getDate();
					return dayOfMonth;
				},

			});
			calendar.render();
	});