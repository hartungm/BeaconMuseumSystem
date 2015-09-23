Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

var GameScore = Parse.Object.extend("GameScore");
var query = new Parse.Query(GameScore);
query.get("Fs5aa9IvYv", {
  success: function(gameScore) {
    // The object was retrieved successfully.
		alert('object retrieved with objectId: ' + gameScore.id);
  },
  error: function(object, error) {
    // The object was not retrieved successfully.
    // error is a Parse.Error with an error code and message.
		alert('Failed to retrieve object, with error code: ' + error.message);
  }
});