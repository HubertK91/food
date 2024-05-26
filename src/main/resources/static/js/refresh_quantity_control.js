$(document).ready(function (){
    loadQuantitiesFromLocalStorage();
    $(".minusButton").on("click", function (evt){
        evt.preventDefault();
        let dishId = $(this).attr("data-pid");
        let qtyInput = $("#quantity" + dishId);

        let newQty = parseInt(qtyInput.val())-1;
        if (newQty>0) qtyInput.val(newQty);
        saveQuantityToLocalStorage(dishId, newQty);
    });

    $(".plusButton").on("click", function (evt){
        evt.preventDefault();
        let dishId = $(this).attr("data-pid");
        let qtyInput = $("#quantity" + dishId);

        let newQty = parseInt(qtyInput.val())+1;
        if (newQty<10) qtyInput.val(newQty);
        saveQuantityToLocalStorage(dishId, newQty);
    });
})

function saveQuantityToLocalStorage(dishId, quantity) {
    localStorage.setItem('quantity' + dishId, quantity);
}

function loadQuantitiesFromLocalStorage() {
    $("input[id^='quantity']").each(function () {
        let $input = $(this);
        let dishId = $input.attr('id').replace('quantity', '');
        let storedQuantity = localStorage.getItem('quantity' + dishId);
        if (storedQuantity) {
            $input.val(storedQuantity);
        }
    });
}
