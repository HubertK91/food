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
    // Sprawdzenie restauracji po załadowaniu
    checkSameRestaurant();

    // Dezaktywuj przycisk, jeśli koszyk jest pusty
    toggleSubmitButton();
});

function removeFromCart(link) {
    let url = link.attr("href");

    // Logowanie URL, aby upewnić się, że jest prawidłowy
    console.log("URL to remove from cart: ", url);

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(crsfHeaderName, crsfValue);
        }
    }).done(function (response) {
        console.log("AJAX Success, response: ", response);  // Logowanie odpowiedzi z serwera
        link.closest('tr').remove();  // Usuwanie wiersza tabeli
        alert(response);  // Wyświetlenie alertu z odpowiedzią

        // Odśwież stronę niezależnie od odpowiedzi
        location.reload();  // Natychmiastowe odświeżenie strony

    }).fail(function () {
        console.error("AJAX Error during removal");
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error");
        $("#myModal").modal();
    });
}


function removeDish(rowNumber){
    rowId = "row" + rowNumber;
    $("#" + rowId).remove();
    updateTotal();
    checkSameRestaurant(); // Sprawdzenie restauracji po usunięciu produktu
    toggleSubmitButton(); // Sprawdzenie stanu przycisku po usunięciu produktu
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
        checkSameRestaurant(); // Sprawdzenie restauracji po zmianie ilości
        toggleSubmitButton();
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

// Funkcja sprawdzająca czy wszystkie restaurantId są takie same
function checkSameRestaurant() {
    let restaurantIds = [];

    // Pobieramy wszystkie restaurantId z koszyka
    $('.restaurant-id').each(function () {
        restaurantIds.push($(this).val());
    });

    // Sprawdzamy czy wszystkie są takie same
    const allSame = restaurantIds.every(id => id === restaurantIds[0]);

    // Aktywujemy lub dezaktywujemy przycisk
    if (allSame) {
        $('#submitOrderButton').removeAttr('disabled'); // Aktywuj przycisk
        $('#restaurantWarning').hide(); // Ukryj ostrzeżenie
    } else {
        $('#submitOrderButton').attr('disabled', 'disabled'); // Dezaktywuj przycisk
        $('#restaurantWarning').show(); // Pokaż ostrzeżenie
    }
}



// Funkcja sprawdzająca, czy koszyk jest pusty
function toggleSubmitButton() {
    const cartItemsCount = $('table tbody tr').length;

    if (cartItemsCount === 0) {
        $('#submitOrderButton').attr('disabled', 'disabled'); // Dezaktywuj przycisk
    } else {
        checkSameRestaurant(); // Sprawdź restauracje, gdy koszyk nie jest pusty
    }
}


