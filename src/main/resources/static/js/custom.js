let index = 1;
$(document).ready(function () {
    addNewAttribute();
});

function addNewAttribute() {
    var template = Handlebars.compile($("#newAttributeTemplate").html());
    var context = {
        index: index++
    };
    $('#newAttributesSection').append(template(context));
}

