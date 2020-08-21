$(document).ready(function() {
    $(".toggle-button").click(function(){
        console.log("Burger clicked");
        $(".nav-links").toggleClass('active');
    })
    $('.nav-links ul li').click(function() {
        $(this).siblings().removeClass('active');
        $(this).toggleClass('active');
    })
});
