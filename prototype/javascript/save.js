Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

var GameScore = Parse.Object.extend("GameScore");
var gameScore = new GameScore();

gameScore.save({
  score: 560,
  playerName: "Lionel Messi",
  cheatMode: false
}, {
  success: function(gameScore) {
    // The object was saved successfully.
	alert('New object created with objectId: ' + gameScore.id);
  },
  error: function(gameScore, error) {
    // The save failed.
    // error is a Parse.Error with an error code and message.
	alert('Failed to create new object, with error code: ' + error.message);
  }
});