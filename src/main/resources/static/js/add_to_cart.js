$(document).ready(function () {
        $(".buttonAdd2Cart").on("click", function (e) {
            e.preventDefault();
            addToCart(this);

        });
    }
)
;

function addToCart(button) {
    const $button = $(button);
    const dishId = $button.data('dishid');
    const restaurantId = $button.data('restaurantid')
    const contextPath = $button.data('contextpath');
    const crsfHeaderName = $button.data('csrfheadername');
    const crsfValue = $button.data('csrfvalue');

    const quantity = $(`#quantity${dishId}${restaurantId}`).val(); // Use dishId here

    const url = `${contextPath}cart/add/${restaurantId}/${dishId}/${quantity}`;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(crsfHeaderName, crsfValue);
        }
    }).done(function (response) {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text(response);
        $("#myModal").modal();
    }).fail(function () {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error");
        $("#myModal").modal();
    });
}
