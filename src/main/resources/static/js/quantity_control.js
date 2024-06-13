$(document).ready(function (){
    $(".minusButton").on("click", function (evt){
        evt.preventDefault();
        let restaurantId = $(this).attr("data-rid");
        let dishId = $(this).attr("data-pid");
        let qtyInput = $("#quantity" + dishId + restaurantId);

        let newQty = parseInt(qtyInput.val())-1;
    if (newQty>0) qtyInput.val(newQty);
    });

    $(".plusButton").on("click", function (evt){
        evt.preventDefault();
        let restaurantId = $(this).attr("data-rid");
        let dishId = $(this).attr("data-pid");
        let qtyInput = $("#quantity" + dishId + restaurantId);

        let newQty = parseInt(qtyInput.val())+1;
        if (newQty<10) qtyInput.val(newQty);
    });
})
