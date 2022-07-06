var index = 1;
if (true) {
    function a() {
    };var index = 1;
}
console.log(index)

function addNewAttribute() {
    var template = Handlebars.compile($("#newAttributeTemplate").html());
    var context = {
        index: index++
    };
    $('#newAttributesSection').append(template(context));
}


let $chatHistory;
let $button;
let $textarea;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('#comments-list');
    $button = $('#messageSendBtn');
    $textarea = $('#messageBody');
}

function render(message, username, id) {
    var templateResponse = Handlebars.compile($("#comment-template").html());
    var contextResponse = {
        message: message, username: username, id: id
    };
    $chatHistory.append(templateResponse(contextResponse));

}

function sendMessage(text) {
    sendMsg(text);
    $textarea.val('');

}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();

