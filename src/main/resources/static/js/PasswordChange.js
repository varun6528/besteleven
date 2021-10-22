function checkVerifyPassword(newPassword) {
  var oldPassword = $("#newPassword").val();
  if (newPassword == oldPassword) {
    $("#verifyPassword")
      .addClass(" border-3 border-success")
      .removeClass("border-danger bounce");
    checkIfReadyForSubmission();
  } else {
    $("#verifyPassword").addClass(" border-3 border-danger bounce");
    $("#submit").prop("disabled", true);
  }
}

function checkPassword(passwordValue, passwordId) {
  if (passwordValue == undefined || passwordValue == "") {
    $("#" + passwordId).addClass(" border-3 border-danger bounce");
    $("#submit").prop("disabled", true);
  } else {
    $("#" + passwordId)
      .addClass(" border-3 border-success")
      .removeClass("border-danger bounce");
    checkIfReadyForSubmission();
  }
}

function checkNewPassword(passwordValue, passwordId) {
  if (passwordValue == undefined || passwordValue == "") {
    $("#" + passwordId).addClass(" border-3 border-danger bounce");
  } else {
    var verifyPassword = $("#verifyPassword").val();
    if (passwordValue == $("#oldPassword").val()) {
      $("#" + passwordId).addClass(" border-3 border-danger bounce");
      alert("Old password cannot be the same as new password!!!");
      $("#submit").prop("disabled", true);
    } else if (
      checkUndefined(verifyPassword) &&
      passwordValue != verifyPassword
    ) {
      $("#" + passwordId)
        .addClass(" border-3 border-success")
        .removeClass("border-danger bounce");
      $("#verifyPassword").addClass(" border-3 border-danger bounce");
      $("#submit").prop("disabled", true);
    } else {
      $("#" + passwordId)
        .addClass(" border-3 border-success")
        .removeClass("border-danger bounce");
      checkIfReadyForSubmission();
    }
  }
}

function removeBorders(id) {
  $("#" + id).removeClass("border-3 border-success border-danger bounce");
}

function checkIfReadyForSubmission() {
  console.log("entered here...");
  var newPassword = $("#newPassword").val();
  var oldPassword = $("#oldPassword").val();
  var verifyPassword = $("#verifyPassword").val();

  if (
    checkUndefined(oldPassword) &&
    checkUndefined(newPassword) &&
    checkUndefined(verifyPassword)
  ) {
    if (oldPassword != newPassword && newPassword == verifyPassword) {
      $("#oldPassword")
        .addClass(" border-3 border-success")
        .removeClass("border-danger bounce");
      $("#newPassword")
        .addClass("border-3 border-success")
        .removeClass("border-danger bounce");
      $("#verifyPassword")
        .addClass(" border-3 border-success")
        .removeClass("border-danger bounce");
      $("#submit").prop("disabled", false);
    } else {
      console.log("Not equal Reached here....");
      $("#submit").prop("disabled", true);
    }
  } else {
    console.log("undefined Reached here....");
    $("#submit").prop("disabled", true);
  }
}

function checkUndefined(password) {
  return password != undefined && password != "";
}
