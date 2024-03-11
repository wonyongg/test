var stompClient = null;
var subscriptionChannel = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
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
    var channel = prompt("구독할 채널을 입력하세요."); // 사용자로부터 구독할 채널을 입력받습니다.
    if (channel && channel.trim() !== "") {
        subscriptionChannel = channel;
        stompClient.subscribe('/sub/channel/' + subscriptionChannel, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        console.log("Subscribed to channel: " + subscriptionChannel);
    } else {
        console.log("Invalid channel. Subscription cancelled.");
    }
}

function sendName() {
    if (subscriptionChannel === null) {
        alert("구독할 채널을 먼저 설정해야 합니다."); // 구독 채널이 설정되지 않았을 경우 알림을 표시합니다.
        return;
    }
    stompClient.send("/sub/" + subscriptionChannel, {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
