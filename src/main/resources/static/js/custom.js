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

