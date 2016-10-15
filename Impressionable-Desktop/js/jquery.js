//TODO make JQuery functions to avoid repeating code
$(document).ready(() => {
  $('.loginBackArrow').click(() => {
    $('body div div:nth-child(2) .enableJQuery').hide();
    setTimeout(() => {$('body div div:nth-child(2) .enableJQuery').fadeIn("slow")}, 200);
  });
  $('#signInButton').click(() => {
    $('body div div:nth-child(3) .enableJQuery').hide();
    setTimeout(() => {$('body div div:nth-child(3) .enableJQuery').fadeIn("slow")}, 200);
  });
  $('#signUpButton').click(() => {
    $('body div div:nth-child(4) .enableJQuery').hide();
    setTimeout(() => {$('body div div:nth-child(4) .enableJQuery').fadeIn("slow")}, 200);
  });
  $('#loginButton').click(() => {
    $('body div:nth-child(3)').hide();
    setTimeout(() => {$('body div:nth-child(3)').fadeIn("slow")}, 200);
  });
  $('#logoutImage').click(() => {
    $('body div:nth-child(2)').hide();
    setTimeout(() => {$('body div:nth-child(2)').fadeIn("slow")}, 200);
  });
  $('.icon').click(() => {
    $('#mainBody').hide();
    setTimeout(() => {$('#mainBody').fadeIn("slow")}, 200);
  });
});
