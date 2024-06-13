$(document).ready(function () {
    $(".minusButton").on("click", function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this))

    });

    $(".plusButton").on("click", function (evt) {
        evt.preventDefault();
        increaseQuantity($(this))
    });

    $(".link-remove").on("click", function (evt){
        evt.preventDefault();
        removeFromCart($(this));
    });

    updateTotal();
});

function removeFromCart(link){
    url = link.attr("href");

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(crsfHeaderName, crsfValue);
        }
    }).done(function (response) {
        link.closest('tr').remove();
        alert(response);
        updateTotal();

        $("#modalTitle").text("Shopping Cart");
        if (response.includes("removed")) {
            $("#myModal").on("hide.bs.modal", function (e){
                rowNumber = link.attr("rowNumber");
                removeDish(rowNumber);
                updateTotal();
            });
        }
        $("#modalBody").text(response);
        $("#myModal").modal();

    }).fail(function () {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error");
        $("#myModal").modal();
    });
}

function removeDish(rowNumber){
    rowId = "row" + rowNumber;
    $("#" + rowId).remove();
    updateTotal();
}

function decreaseQuantity(link) {
    let restaurantId = link.attr("data-rid")
    let dishId = link.attr("data-pid");
    let qtyInput = $("#quantity" + dishId + restaurantId);

    let newQty = parseInt(qtyInput.val()) - 1;
    if (newQty > 0) {
        qtyInput.val(newQty);
        updateQuantity(restaurantId, dishId, newQty);
    }
}

function increaseQuantity(link){
    let restaurantId = link.attr("data-rid")
    let dishId = link.attr("data-pid");
    let qtyInput = $("#quantity" + dishId + restaurantId);

    let newQty = parseInt(qtyInput.val()) + 1;
    if (newQty < 10) {
        qtyInput.val(newQty);
        updateQuantity(restaurantId, dishId, newQty);
    }
}

function updateQuantity(restaurantId, dishId, quantity){
    const url = `${contextPath}cart/update/${restaurantId}/${dishId}/${quantity}`;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(crsfHeaderName, crsfValue);
        }
    }).done(function (newSubtotal) {
        updateSubtotal(newSubtotal, dishId);
        updateTotal();
    }).fail(function () {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error");
        $("#myModal").modal();
    });
}

function updateSubtotal(newSubtotal, dishId){
    $("#subtotal" + dishId).text(newSubtotal);
}
function updateTotal() {
    total = 0.0;
    $(".dishSubtotal").each(function (index, element) {
            total = total + parseFloat(element.innerHTML);
        }
    );

    $("#totalAmount").text("$" + total.toFixed(2))
}
