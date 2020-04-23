function getDataByURL(url, datos, callback) {
    console.log(url, datos, callback);
    $.ajax({
      url: url,
      data: datos,
      success: function(response) {
        callback(response); 
      },
      error: function(xhr, ajaxOptions, thrownError) {
        if (xhr.status == 404)
            callback(xhr.responseText);
        console.error(xhr.status + ' - ' + thrownError + '\n' + xhr.responseText);    
      }
    });
}

function markActions() {
    $(".acciones a:not(.no-toggable)").filter(":not(.has-listener)")
        .addClass("has-listener")
        .click(function () {
            $(this).toggleClass("accion-activa");
        });
};

function replyPost() {
    $(".acciones a.accion-reply").filter(":not(.has-listener)")
        .addClass("has-listener")
        .click(function () {
            var cardFooter = $(this).parents(".post").children(".card-footer");
            cardFooter.toggleClass("is-hidden");
            cardFooter.find(".textarea").focus();
        });
};

function showResponses () {
    $(".acciones a.post-responses").filter(":not(.has-listener)")
        .addClass("has-listener")
        .click(function () {
            var post = $(this).parents(".card");
            post.children(".responses").toggleClass("is-hidden");
            post.find(".post-responses").toggleClass("is-hidden");});

    $(".acciones a.load-responses")
    .removeClass("load-responses")
    .one('click', function () {
        getDataByURL(window.location.url + "/responses", 
                     {post_id: "foo"}, //El ID del post nos será útil posteriormente
                     function (e) { document.getElementById("responses").innerHTML += e; loadAllEvents(); } );
    })
};

function countChars() {
    $('.card-footer textarea').filter(":not(.has-listener)")
        .addClass("has-listener")
        .on('input', function () {
            var text, counter, remaining;
            text = this.value;
            counter = 280 - text.length;
            remaining = $(this).parents(".card-footer").find(".characters-remaining");
            remaining.text(counter + "/280");
            if (counter < 1) {
                remaining.removeClass("has-text-warning");
                remaining.addClass("has-text-danger");
            } else if (counter < 21) {
                remaining.removeClass("has-text-danger");
                remaining.addClass("has-text-warning");
            } else {
                remaining.removeClass("has-text-danger has-text-warning");
            }
        });
};

function tellUsSomething(){
    $("#in-post textarea")
        .focus(function(){$(this).parents(".card")
                                 .find(".characters-remaining")
                                 .removeClass("is-hidden")});
    
    $("#in-post textarea")
        .blur(function(){$(this).filter(function() {return !this.value.length;})
                                .parents(".card")
                                .find(".characters-remaining")
                                .addClass("is-hidden")})
}

function checkPasswords(){
    $("#passwordRepeat").on('input', function() {
        var pwd = $("#password").val();
        var rpwd = $("#passwordRepeat").val();
        if((pwd == rpwd) && (rpwd !== "") && (rpwd.length >= 8)){
            $("#passwordRepeat").removeClass("is-danger");
            $("#passwordRepeat").addClass("is-success");
            $("#checkPasswordRepeat").removeClass("is-hidden");
            $("#formButton").prop("disabled", false);
        }else{
            $("#passwordRepeat").removeClass("is-success");
            $("#formButton").prop("disabled", true);
            if(rpwd !== ""){
                $("#passwordRepeat").addClass("is-danger");
                $("#checkPasswordRepeat").addClass("is-hidden");
            }else{
                $("#passwordRepeat").removeClass("is-danger");
            }
        } 
    });

    $("#password").on('input', function() {
        var pwd = $("#password").val();
        if((pwd !== "") && (pwd.length >= 8)){
            $("#password").removeClass("is-danger");
            $("#password").addClass("is-success");
            $("#checkPassword").removeClass("is-hidden");
        }else{
            $("#password").removeClass("is-success");
            if(pwd !== ""){
                $("#password").addClass("is-danger");
                $("#checkPassword").addClass("is-hidden");
            }else{
                $("#password").removeClass("is-danger");
            }
        }
    });
}

function loadAllEvents() {
    markActions();    
    replyPost();
    showResponses();
    autosize($('.card-footer textarea'));
    $(".datos>.fecha").text(function () {return timeago.format($(this).attr("timestamp"));});
    countChars();
    tellUsSomething();
    checkPasswords();
};

$(document).ready(loadAllEvents());
