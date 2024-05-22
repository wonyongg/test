var stompClient = null;
var subscriptionChannel = null;
var currentNickname = null;
var exNickname = null;
var userSessionId = null;
var currentUsers = [];

function connect() {

    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);

    // 유저 닉네임, 세션 아이디 생성 후 서버 등록
    currentNickname = generateRandomString();
    $("#user_name").html("닉네임: " + currentNickname);

    var headers = {
        "nickname" : currentNickname
    }

    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/sub/user-list', function (message) {
            const response = JSON.parse(message.body)
            if (response.message && response.message === "DUPLICATED") {
                $("#name").val('');
                if (response.sessionId === userSessionId) {
                    $("#user_name").html("닉네임: " + exNickname);
                    alert("이미 등록된 닉네임입니다.");
                }
            } else {
                $("#current_user_list").empty();
                currentUsers = [];
                Object.keys(response).forEach((key) => {
                    $("#current_user_list").append("<li>" + key +"</li>");
                    currentUsers.push(key);
                    if (key === currentNickname) {
                        userSessionId = response[key];
                    }
                });
            }
        });

        stompClient.subscribe('/sub/user-count', function (message) {
            let count = JSON.parse(message.body);
            $("#user-count").empty();
            $("#user-count").append("접속 중인 유저 수 : " + count);
        });

        get_channel();
    });
}

function generateRandomString() {
    // 숫자와 알파벳을 포함하는 문자열 정의
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';
    const charactersLength = characters.length;
    for (let i = 0; i < 6; i++) {
        // 문자열에서 무작위로 문자를 선택하여 결과에 추가
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("소켓 연결 해제");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#enter_room").prop("disabled", !connected); // 이동하기 버튼
    $("#send").prop("disabled", !connected); // 보내기 버튼
    $("#nickname").prop("disabled", !connected); // 닉네임 설정 버튼

    if (connected) {
        // 연결 시 버튼 활성화
        $('.form-group-hidden').css('display', 'block');
    }
    else {
        // 연결 시 버튼 활성화
        $('.form-group-hidden').css('display', 'none');
        $("#user-count").empty();
    }
    $("#messages").html("");
}

function get_channel() {

    stompClient.subscribe("/sub/channel-list", function (channel_list) {
        // channel_list.body를 JSON 배열로 파싱
        const channelList = JSON.parse(channel_list.body);
        // 파싱된 데이터가 배열인지 확인
        if (Array.isArray(channelList)) {
            $("#activated_channel_list").empty();
            // 배열의 각 요소(채널)를 순회하며 <li> 태그로 추가
            channelList.forEach((channel) => {
                // 채널 목록에 클릭 이벤트 리스너를 추가하는 부분
                var channelElement = $("<li>" + channel + "</li>").click(function() {
                    if (subscriptionChannel === channel) {
                        alert("이미 접속 중인 채널입니다.");
                    } else if (confirm("'" + channel + "' 채널로 이동하시겠습니까?")) {
                        enter_channel(channel);
                    }
                });
                $("#activated_channel_list").append(channelElement);
            });
        }
    });
    console.log("채널 목록 구독하기");
}

function enter_channel(channel) {
    if (channel && channel.trim() !== "") {
        subscriptionChannel = channel;
        stompClient.subscribe('/sub/channel/' + subscriptionChannel, function (message) {
            showMessage(JSON.parse(message.body));
        });
        $("#channel_name").html("채널명: " + subscriptionChannel);
        alert("채팅방 '" + subscriptionChannel + "'에 입장했습니다.");
        console.log("구독중인 채널: " + subscriptionChannel);

        stompClient.send("/pub/channel-list", {}, channel);
        $("#room_name").val('');
    } else {
        console.log("유효하지 않은 채널로 구독 취소");
    }
}

function changeChannel() {
    var channel = $("#room_name").val();

    enter_channel(channel);
}

function sendMessage() {
    if (subscriptionChannel === null) {
        alert("구독할 채널을 먼저 설정해야 합니다."); // 구독 채널이 설정되지 않았을 경우 알림을 표시합니다.
        return;
    }

    if (currentNickname === null || currentNickname === undefined || currentNickname === "") {
        alert("닉네임을 입력해주세요.");
        return;
    }

    var messageContent = $("#message").val();
    if (messageContent && stompClient) {
        var chatMessage = {
            channelName: subscriptionChannel,
            sender: currentNickname,
            sessionId: userSessionId,
            content: messageContent
        };
        stompClient.send("/pub/chat", {}, JSON.stringify(chatMessage));
        $("#message").val('');
    }
}

function showMessage(message) {
    var sender;
    if (message.sessionId === userSessionId) {
        sender = "나";
    } else {
        sender = message.sender;
    }
    var content = message.content;
    $("#messages").append("<tr><td>" + sender + " : " + content + "</td></tr>");

    // 자동 스크롤을 위한 코드
    var chatMessages = $(".chat-messages");
    chatMessages.scrollTop(chatMessages.prop("scrollHeight"));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    // 연결
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });

    // 채널 변경
    $("#enter_room").click(function() {
        changeChannel();
    });

    // 닉네임 설정
    $("#nickname").click(function() {

        if (currentUsers.includes($("#name").val())) {
            alert("이미 등록된 닉네임입니다. 다시 입력해주세요.")
            return;
        }

        exNickname = currentNickname;
        currentNickname = $("#name").val();
        console.log("currentNickname : " + currentNickname);
        if (currentNickname === null || currentNickname === undefined || currentNickname === "") {
            alert("닉네임이 제대로 입력되지 않았습니다.(\"\", \" \" 등 빈 값은 입력될 수 없습니다.)")
        } else {
            $("#user_name").html("닉네임: " + currentNickname);

            var enroll = {
                exNickname: exNickname,
                changeNickname: currentNickname,
                sessionId: userSessionId
            }

            stompClient.send("/pub/enroll", {}, JSON.stringify(enroll));

            $("#name").val('');
            alert("닉네임을 '" + currentNickname + "'(으)로 변경합니다.");
        }
    });

    // 메시지 보내기
    $( "#send" ).click(function() { sendMessage(); });
    $('#message').keypress(function(event) {
        // 엔터 키의 keycode는 13입니다.

        if (event.which == 13) {
            // preventDefault를 호출하여 폼 제출을 방지합니다.
            event.preventDefault();
            // #send 버튼의 클릭 이벤트를 트리거합니다.
            sendMessage();
        }
    });
});