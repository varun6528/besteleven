function computeScore(id, value, selector) {
  console.log(value);
  if (isNaN(value)) {
    alert("Please enter a number");
    return;
  }
  value = parseInt(value);
  var multiplier = computeMultiplier(selector);
  console.log("The multiplier of selected::: " + multiplier);
  valueScore = parseInt(multiplier) * value;
  console.log("The valueScore::: " + valueScore);
  var otherValueId = fetchIdOfOtherValue(id);
  var otherValue = $("#" + otherValueId).val();
  if (otherValue == undefined || otherValue == "" || isNaN(otherValue)) {
    otherValue = 0;
  }
  var otherMultiplier = computeOtherMultiplier(multiplier);
  var otherValueScore = parseInt(otherValue) * otherMultiplier;
  console.log("The otherValueScore::: " + otherValueScore);
  var score = valueScore + otherValueScore;
  console.log("The score::: " + score);
  var scoreId = fetchIdOfScore(id);
  $("#" + scoreId).val(score);
  $("#" + scoreId).text(score);
}

function computeMultiplier(selector) {
  if (selector == "Runs") {
    return 1;
  } else {
    return 25;
  }
}

function computeOtherMultiplier(multiplier) {
  if (multiplier == 1) {
    return 25;
  }
  return 1;
}

function fetchIdOfOtherValue(id) {
  if (id.includes("Runs")) {
    return id.replace("Runs", "Wicts");
  } else {
    return id.replace("Wicts", "Runs");
  }
}

function fetchIdOfScore(id) {
  if (id.includes("Runs")) {
    return id.replace("Runs", "Score");
  } else {
    return id.replace("Wicts", "Score");
  }
}
