/**
 * 
 */

/*Chatbot*/

//챗봇 ID
let chatbotButtonImg;
let chatbotBody;
let chatbotContent;
let chatbotKeyword;

let chatbotKey;

//웹소켓 STOMP
let stompClient = null;

//챗봇 토글 버튼
function toggleChatbotButton() {
	if (chatbotBody.classList.contains('none')) {
		enableChatbot();
	} else {
		disableChatbot();
	}
}

//챗봇 활성화
function enableChatbot() {
	chatbotButtonImg.src = "/img/chatbot/chatbot-close-0.png";
	
	chatbotBody.classList.remove("none");
	chatbotBody.classList.remove("hide");
	chatbotBody.classList.add("show");
	
	connectChatbot();
}

//챗봇 비활성화
function disableChatbot() {
	chatbotButtonImg.src = "/img/chatbot/chatbot-bot.png";
	
	chatbotBody.classList.remove("show");
	chatbotBody.classList.add("hide");
	
	//애니메이션 처리 0.25초 후 실행
	setTimeout(function() {
		chatbotBody.classList.add("none");
	}, 250);
	
	chatbotContent.innerHTML = "";
	
	disconnectChatbot();
}

//챗봇 웹소켓 연결
function connectChatbot() {
	stompClient = Stomp.over(new SockJS("/chatbot"));
	stompClient.connect({}, frame => {
		chatbotKey = new Date().getTime();
		
		stompClient.subscribe(`/topic/request/${chatbotKey}`, responseData => {
			var message = responseData.body;
			
			showChatbotMessage(message);
		});
		
		let data = {
			key: chatbotKey,
			name: "노원 행복마당",
			content: "인사"
		}
		
		stompClient.send("/message/chatbot", {}, JSON.stringify(data));
	});
}

//챗봇 웹소켓 연결 해제
function disconnectChatbot() {
	stompClient.disconnect(function() {
		console.log("Disconnected");
	});
}

//챗봇 메시지 표시
function showChatbotMessage(message) {
	chatbotContent.insertAdjacentHTML('beforeend', message);
	
	chatbotContent.scrollTop = chatbotContent.scrollHeight;
}

//유저 메시지 표시
function showUserMessage(keyword) {
	let time = formatTime();
	
	return `
		<div id="user-message-wrap" class="message-wrap flex end">
			<div id="message-direction-user" class="message-direction"></div>
			<div id="user-message">
				<div id="user-message-box" class="message-box">
					<span>${keyword}</span>
				</div>
				<div class="chatbot-time">
					<span>${time}</span>
				<div>
			</div>
		</div>
	`;
}

//입력값 전송 버튼
async function sendChatbotButton() {
	let keyword = chatbotKeyword.value.trim();
	
	if (keyword.length < 2) {
		return;
	}
	
	let data = {
		key: chatbotKey,
		name: "노원 행복마당",
		content: keyword
	}
	
	switch (keyword) {
		case "일간 박스오피스":
			await showDailyBoxOffice();
			
			showUserMessage(keyword);
			showChatbotMessage(dailyBoxOfficeResult.innerHTML);
			
			chatbotKeyword.value = "";
			
			return;
		case "주간 박스오피스":
			await showWeeklyBoxOffice();
			
			showUserMessage(keyword);
			showChatbotMessage(weeklyBoxOfficeResult.innerHTML);
			
			chatbotKeyword.value = "";
			
			return;
		default:
			break;
	}
	
	stompClient.send("/message/chatbot", {}, JSON.stringify(data));
	
	let message = showUserMessage(keyword);
	showChatbotMessage(message);
	
	chatbotKeyword.value = "";
}

//입력값 전송 엔터 키
function checkEnterKey(event) {
	let keyCode = event.keyCode;
	
	if (keyCode === 13) {
		sendChatbotButton();
	}
}

//현재 시간(오후/오전) 구분
function formatTime() {
	let now = new Date();
	let period = (now.getHours() > 11) ? "오후" : "오전";
	let hour = now.getHours() % 12;
	
	if (hour == 0) hour = 12;
	
	return `${period} ${hour}:${formatMinutes(now.getMinutes())}`;
}

//현재 시간(분) 형식 조정
function formatMinutes(minutes) {
	return minutes < 10 ? '0' + minutes:'' + minutes;
}

/*Movie API*/

//영화 ID
let dailyBoxOfficeResult;
let weeklyBoxOfficeResult;
let movieDetailResult;
let lastSundayResult;

let movieKey = "c413e5112acb82c71d8de6f54d92d909";

//일간 박스오피스
async function showDailyBoxOffice() {
	let url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";
	
	let previousDay = new Date();
	previousDay.setDate(previousDay.getDate() - 1);
	
	let targetDt = previousDay.getFullYear() +
		((previousDay.getMonth() + 1) > 9 ? (previousDay.getMonth() + 1).toString() : + "0" + (previousDay.getMonth() + 1).toString()) +
		(previousDay.getDate() > 9 ? previousDay.getDate().toString() : "0" + previousDay.getDate().toString());
		
		
	await $.ajax({
		method: "GET",
		url: url + "key=" + movieKey + "&targetDt=" + targetDt,
		data: {
			itemPerPage: "10"
		}
	}).done(function(message) {
		let result = "";
		
		for (let i = 0; i < 10; i++) {
			let shortUrl = message.boxOfficeResult.dailyBoxOfficeList[i];
			
			result += "<p>영화 코드: " + shortUrl.movieCd + "</p>";
			result += "<p>영화 제목: " + shortUrl.movieNm + "</p>";
			result += "<p>관객 수: " + shortUrl.audiCnt + "</p>";
			result += "<p>개봉일: " + shortUrl.openDt + "</p><hr><br>";
		}
		
		dailyBoxOfficeResult.innerHTML += result;
	}); 
}

//주간 박스오피스
async function showWeeklyBoxOffice() {
	let url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?";
	
	let previousDay = new Date();
	previousDay.setDate(previousDay.getDate() - 7);
	
	let targetDt = previousDay.getFullYear() +
		((previousDay.getMonth() + 1) > 9 ? (previousDay.getMonth() + 1).toString() : + "0" + (previousDay.getMonth() + 1).toString()) +
		(previousDay.getDate() > 9 ? previousDay.getDate().toString() : "0" + previousDay.getDate().toString());
		
	let weekGb = "0";
		
	await $.ajax({
		method: "GET",
		url: url + "key=" + movieKey + "&targetDt=" + targetDt + "&weekGb=" + weekGb,
		data: {
			itemPerPage: "10"
		}
	}).done(function(message) {
		let result = "";
		
		for (let i = 0; i < 10; i++) {
			let shortUrl = message.boxOfficeResult.weeklyBoxOfficeList[i];
			
			result += "<p>영화 코드: " + shortUrl.movieCd + "</p>";
			result += "<p>영화 제목: " + shortUrl.movieNm + "</p>";
			result += "<p>관객 수: " + shortUrl.audiCnt + "</p>";
			result += "<p>개봉일: " + shortUrl.openDt + "</p><hr><br>";
		}
		
		weeklyBoxOfficeResult.innerHTML += result;
	}); 
}

//영화 상세정보
function movieDetailBtn() {
	let url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";
	
	let movieCd = document.getElementById("movie-code").value;
	
	$.ajax({
		method: "GET",
		url: url + "key=" + movieKey + "&movieCd=" + movieCd,
	}).done(function(message) {
		let result = "";
		let shortUrl = message.movieInfoResult.movieInfo;
		let acts = shortUrl.actors;
		let gnrs = shortUrl.genres;
		let drts = shortUrl.directors;
		
		result += "<p>영화 제목: " + shortUrl.movieNm + "</p>";
		
		result += "<p>장르: "
		for (let i = 0; i < gnrs.length; i++) {
			result += "<span>" + gnrs[i]['genreNm'] + ", </span>";
		}
		result = result.substring(0, result.length-9);
		result += "</span></p>"
		
		result += "<p>감독: "
		for (let j = 0; j < drts.length; j++) {
			result += "<span> " + drts[j]['peopleNm'] + ", </span>";
		}
		result = result.substring(0, result.length-9);
		result += "</span></p>"
		
		result += "<p>배우: "
		for (let k = 0; k < acts.length; k++) {
			result += "<span>" + acts[k]['peopleNm'] + ", </span>";
		}
		result = result.substring(0, result.length-9);
		result += "</span></p>"
		
		result += "<p>상영 시간: " + shortUrl.showTm + "분</p>";
		result += "<p>개봉일: " + shortUrl.openDt + "</p>";
		
		movieDetailResult.innerHTML += result;
	});
}

//문서 전체 읽은 후 스크립트 실행을 위한 이벤트 리스너
document.addEventListener("DOMContentLoaded", function() {
	chatbotButtonImg = document.getElementById("chatbot-button-img");
	chatbotBody = document.getElementById("chatbot-body-wrap");
	chatbotContent = document.getElementById("chatbot-content");
	chatbotKeyword = document.getElementById("chatbot-keyword");
	
	dailyBoxOfficeResult = document.getElementById("dailyBoxOffice-result");
	weeklyBoxOfficeResult = document.getElementById("weeklyBoxOffice-result");
	movieDetailResult = document.getElementById("movie-detail-result");
	lastSundayResult = document.getElementById("lastSunday-result");
});