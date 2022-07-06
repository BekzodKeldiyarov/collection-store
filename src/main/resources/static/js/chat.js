const url = "http://localhost:8080";
let stompClient;
let itemId = $('#itemId').val();

function connectToChat() {
    console.log("connecting to chat");
    let socket = new SockJS(url + "/chat");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("Connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + itemId, function (response) {
            let data = JSON.parse(response.body);
            render(data.text, data.user.username, data.user.id);
        })
    })
}


function sendMsg(text) {
    stompClient.send("/app/chat/" + itemId, {}, JSON.stringify({
        text: text
    }))
}

connectToChat();


