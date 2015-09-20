Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

// Create the object.
var GameScore = Parse.Object.extend("GameScore");
var gameScore = new GameScore();
var query = new Parse.Query(GameScore);

query.get("Fs5aa9IvYv", {
	success : function (gameScore) {
		// The object was retrieved successfully.
		alert('object retrieved with objectId: ' + gameScore.id);

		query.save(null, {
			success : function (gameScore) {
				// Now let's update it with some new data. In this case, only cheatMode and score
				// will get sent to the cloud. playerName hasn't changed.
				alert('about to modify object with objectId: ' + gameScore.id);
				gameScore.set("cheatMode", true);
				gameScore.set("score", 654);
				gameScore.save();
				document.getElementById("str").innerHTML = "update success";
			}
		});
	},
	error : function (object, error) {
		// The object was not retrieved successfully.
		// error is a Parse.Error with an error code and message.
		alert('Failed to retrieve object, with error code: ' + error.message);
	}
});