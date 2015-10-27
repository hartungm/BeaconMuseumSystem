function myDelete() {
	Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

	var GameScore = Parse.Object.extend("GameScore");
	var query = new Parse.Query(GameScore);

	query.equalTo("playerName", "Jim Garfiel");
	query.find({
		success : function (results) {
			// Do something with the returned Parse.Object values
			results[0].destroy({
				success : function (gameScore) {
					// The object was deleted from the Parse Cloud.
					alert('object deleted with objectId: ' + gameScore.id);
				},
				error : function (object, error) {
					// The delete failed.
					alert('an error occured trying to delete: ' + error.message);
				}
			});
		},
		error : function (error) {
			alert("Error: " + error.code + " " + error.message);
		}
	});
};
