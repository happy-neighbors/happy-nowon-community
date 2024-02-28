/**
 * 
 */
//var stompClient=null; 

$(function(){
	$("#btn-bot").click(btnBotClicked);
});
function btnCloseClicked(){
	$("#bot-container").hide();
	//대화창 리셋
	$("#chat-content").html("");
	disconnect();
	
}
function btnBotClicked(){
	//1. 소켓 접속
	$("#bot-container").show();
	connect()
}

//1~9 -> 01~09 변환하는 함수
function fomatNumber(number){
	return number<10? '0'+number:''+number;
}

// 시간출력 ex) 오전 9:08
function formatTime(){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var hour=now.getHours()%12;
	if(hour==0)hour=12;
	return `${ampm} ${hour}:${fomatNumber(now.getMinutes())}`;
}

function userTag(text){
	var time=formatTime();
	return `
	<div class="msg user flex">
		<div class="message">
			<div class="part">
				<p>${text}</p>
			</div>
			<div class="time">${time}</div>
		</div>
	</div>
	`;
}



function showMessage(tag){
	$("#chat-content").append(tag);
	//스크롤을 제일 아래로
	$("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}
let key;
var data={
	division: "weather",
}
function connect(){
	//var socket=new SockJS("/green-bot")
	stompClient=Stomp.over(new SockJS("/green-bot"));
	stompClient.connect({},(frame)=>{
		key=new Date().getTime();
		//접속이 완료되면 인사말수신-구독
		stompClient.subscribe(`/topic/request/${key}`,(answerData)=>{
			var msg=answerData.body
			
			//console.log(msg);
			
			/////////////////////////////
			showMessage(msg);
		})
		
		data.key=key;
		data.order=0;
		
		//인사말 보내줘
		stompClient.send("/message/weather",{},JSON.stringify(data));
	});
}



function disconnect() {
    stompClient.disconnect(function(){
		console.log("Disconnected");	
	});
    
}
function checkEnterKey(event){
	var keyCode=event.keyCode;
	if(keyCode===13){
		btnMsgSendClicked();
	}
}

function btnMsgSendClicked(){
	var question=$("#question").val().trim();//질문에대한답
	if(question.length <2 ){
		alert("질문은 2글자 이상이어야 합니다.");
		return;
	}
	
	
	var forms=$(".form");
	var form=$(forms[forms.length-1]);
	var order=form.find(".order").val()
	//order 추가
	data.order=order;
	console.log("00question", question)
	console.log("00order", order)
	var weather={
		message:question,
		order:order,
		areaInfo:form.find(".areaInfo").val(),
		nx:form.find(".nx").val(),
		ny:form.find(".ny").val(),
		fcstDate:form.find(".fcstDate").val(),
		fcstTime:form.find(".fcstTime").val(),
	};
	
	//order가 1일때 question은 조회할 지역입니다. 좌표가 필요
	
	if(order == 1) {
	    getPositionOfAddress(weather)
		    .then(weather => {
				//날씨정보 세팅
				console.log("----->", weather)
		        data.weather=weather;
				//소켓으로 전송
				stompClient.send("/message/weather",{},JSON.stringify(data));
	    	})
		    .catch(error => {
		        // 에러 처리
		        console.error(error);
		    });
	}else{
		console.log("----->", weather)
		data.weather=weather;
		stompClient.send("/message/weather",{},JSON.stringify(data));
	}
	
	
	//인사말 보내줘
	//stompClient.send("/message/weather",{},JSON.stringify(data));
	var tag=userTag(question);
	showMessage(tag);
	$("#question").val("");
}


//좌표는 비동기로 얻어지므로 Promise 사용함
function getPositionOfAddress(weather){
	// 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // Promise를 반환합니다
    return new Promise((resolve, reject) => {
        geocoder.addressSearch(weather.message, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var rs = dfs_xy_conv("toXY", result[0].y, result[0].x);
                console.log("x:" + rs.x);
                console.log("y:" + rs.y);
                weather.nx = rs.x;
                weather.ny = rs.y;
                weather.areaInfo = weather.message;

                if(rs.x === 0) {
                    reject("잘못입력된 장소입니다. 다시 입력해주세요");
                } else {
                    resolve(weather);
                }
            } else {
                reject("주소 검색에 실패했습니다");
            }
        });
    });
	
}


