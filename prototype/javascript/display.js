function display() {
	//Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");
	var key = "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg";
	//var key = $('parsekey').val();
	Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", key);

	var GameScore = Parse.Object.extend("GameScore");
	var query = new Parse.Query(GameScore);
	//var objID = "Fs5aa9IvYv"; //objID not in database, for testing bad ids
	var objID = "kQB7XmwMKL";

	query.get(objID, {
		success : function (gameScore) {
			// The object was retrieved successfully.
			//alert('object retrieved with objectId: ' + gameScore.id);

			var playerName = gameScore.get("playerName");
			var score = gameScore.get("score");
			var cheat = gameScore.get("cheatMode");

			document.getElementById("nspan").innerHTML = playerName;
			document.getElementById("sspan").innerHTML = score;
			document.getElementById("cspan").innerHTML = cheat;

		},
		error : function (object, error) {
			// The object was not retrieved successfully.
			alert('Failed to retrieve object, with error code: ' + error.message);
		}
	});
};
