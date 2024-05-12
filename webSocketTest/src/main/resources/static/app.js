var stompClient = null;
var subscriptionChannel = null;
var currentNickname = null;
var userSessionId = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#enter_room").prop("disabled", !connected); // 이동하기 버튼
    $("#send").prop("disabled", !connected); // 보내기 버튼
    $("#nickname").prop("disabled", !connected); // 닉네임 설정 버튼

    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {

    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        subscribeToChannel(); // 클라이언트가 연결되면 구독 채널에 자동으로 구독합니다.
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function subscribeToChannel() {
    var channel = prompt("구독할 채널을 입력하세요."); // 최초에 사용자로부터 구독할 채널을 입력받습니다.

    enter_channel(channel);
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

        console.log("messageContent : " + messageContent)
        console.log("chatMessage.roomName : " + chatMessage.channelName)
        console.log("chatMessage.name : " + chatMessage.sender)
        console.log("chatMessage.sessionId : " + chatMessage.sessionId)
        console.log("chatMessage.content : " + chatMessage.content)
        stompClient.send("/pub/chat", {}, JSON.stringify(chatMessage));
        $("#message").val('');
    }
}

function enter_channel(channel) {
    if (channel && channel.trim() !== "") {
        subscriptionChannel = channel;
        stompClient.subscribe('/sub/channel/' + subscriptionChannel, function (message) {
            console.log("message : " + message.body);
            showMessage(JSON.parse(message.body));
        });
        $("#channel_name").html("채널명: " + subscriptionChannel);
        alert("채팅방 '" + subscriptionChannel + "'에 입장했습니다.");
        console.log("Subscribed to channel: " + subscriptionChannel);
    } else {
        console.log("Invalid channel. Subscription cancelled.");
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
    console.log("sender : " + sender);
    console.log("content : " + content);
    $("#messages").append("<tr><td>" + sender + " : " + content + "</td></tr>");
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
        currentNickname = $("#name").val();
        userSessionId = crypto.randomUUID();
        console.log("currentNickname : " + currentNickname);
        if (currentNickname === null || currentNickname === undefined || currentNickname === "") {
            alert("닉네임이 제대로 입력되지 않았습니다.(\"\", \" \" 등 빈 값은 입력될 수 없습니다.)")
        } else {
            $("#user_name").html("닉네임: " + currentNickname);
            alert("닉네임이 '" + currentNickname + "'(으)로 변경되었습니다.");
        }
    });

    // 메시지 보내기
    $( "#send" ).click(function() { sendMessage(); });
});
