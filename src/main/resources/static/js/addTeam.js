var selectedPlayers = {};

function init() {
  console.log("entered here...");
  for (var idx = 1; idx < 12; idx++) {
    var playerValue = $("#player" + idx)
      .find("option:selected")
      .val();
    console.log(playerValue);
    if (playerValue != "0") {
      var id = "player" + idx;
      selectedPlayers[id] = playerValue;
    }
  }
  console.log(selectedPlayers);
  for (var j = 1; j < 12; j++) {
    for (var property in selectedPlayers) {
      $(
        "#player" + j + " option[value=" + selectedPlayers[property] + "]"
      ).hide();
    }
  }
}

window.onload = init;

function getSelectValue(playerValue, id) {
  var restoredPlayer = "";
  var text = $("#" + id)
    .find("option:selected")
    .text();
  if (selectedPlayers[id] != undefined || selectedPlayers[id] != "") {
    restoredPlayer = selectedPlayers[id];
    $("#manOfTheMatch option[value='" + restoredPlayer + "']").remove();
  }
  if (playerValue != "" || playerValue != "default") {
    selectedPlayers[id] = playerValue;
    $("#manOfTheMatch").append(
      '<option value="' + playerValue + '">' + text + "</option>"
    );
  }
  console.log(selectedPlayers);
  for (var j = 1; j < 12; j++) {
    for (var property in selectedPlayers) {
      $(
        "#player" + j + " option[value=" + selectedPlayers[property] + "]"
      ).hide();
      if (restoredPlayer != "") {
        $("#player" + j + " option[value=" + restoredPlayer + "]").show();
      }
    }
  }
}
