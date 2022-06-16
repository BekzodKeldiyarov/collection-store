let index = 1;

function addNewAttribute() {
    var template = Handlebars.compile($("#newAttributeTemplate").html());
    var context = {
        index: index++
    };
    $('#newAttributesSection').append(template(context));

    // $title.val('');
    // tinymce.get("messageBody").setContent('');
}