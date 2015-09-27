function update() {
	Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

	var GameScore = Parse.Object.extend("GameScore");
	var query = new Parse.Query(GameScore);
	//var objID = "Fs5aa9IvYv"; //objID not in database, for testing bad ids
	var objID = "kQB7XmwMKL";

	query.get(objID, {
		success : function (gameScore) {
			// The object was retrieved successfully.
			//alert('object retrieved with objectId: ' + gameScore.id);

			// only cheatMode and score will get sent to the cloud
			// playerName hasn't changed.
			// Parse only resends 'dirty' data
			gameScore.set("score", 1324);
			gameScore.set("cheatMode", true);
			gameScore.save();
			alert('Successfully updated gamescore with id' + gameScore.id);
		},
		error : function (object, error) {
			// The object was not retrieved successfully.
			// error is a Parse.Error with an error code and message.
			alert('Failed to retrieve object, with error code: ' + error.message);
		}
	});
};
