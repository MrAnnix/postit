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

function replyTweet() {
    $(".acciones a.accion-reply").filter(":not(.has-listener)")
        .addClass("has-listener")
        .click(function () {
            var cardFooter = $(this).parents(".tweet").children(".card-footer");
            cardFooter.toggleClass("is-hidden");
            cardFooter.find(".textarea").focus();
        });
};

function showResponses () {
    $(".acciones a.tweet-responses").filter(":not(.has-listener)")
        .addClass("has-listener")
        .click(function () {
            var tweet = $(this).parents(".card");
            tweet.children(".responses").toggleClass("is-hidden");
            tweet.find(".tweet-responses").toggleClass("is-hidden");});

    $(".acciones a.load-responses")
    .removeClass("load-responses")
    .one('click', function () {
        getDataByURL("./responses.html", 
                     {tweet_id: "foo"}, //El ID del tweet nos será útil posteriormente
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

function loadAllEvents() {
    markActions();    
    replyTweet();
    showResponses();
    autosize($('.card-footer textarea'));
    $(".datos>.fecha").text(function () {return timeago.format($(this).attr("timestamp"));});
    countChars();
};

$(document).ready(loadAllEvents());
