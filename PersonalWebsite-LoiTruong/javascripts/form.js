console.log("Form script works");
$(document).ready(function() {
    $('.submit').click(function(event) {
        console.log("Submit button clicked");

        var email = $('.email').val();
        var subject = $('.subject').val();
        var message = $('.message').val();
        var status = $('.status');
        status.empty();

        if(email.length > 5 && email.includes('@') && email.includes('.')) {
            status.append('<div>The email you entered is valid</div>');
        } else {
            event.preventDefault();
            status.append('<div>The email you entered is invalid</div>');
        }

        if(subject.length >= 2) {
            status.append('<div>Subject is valid</div>');
        } else {
            event.preventDefault();
            status.append('<div>Subject should have at least 2 characters</div>');
        }

        if(message.length >= 10) {
            status.append('<div>Message is valid</div>');
        } else {
            event.preventDefault();
            status.append('<div>Message should have at least 10 characters</div>');
        }
    });
})