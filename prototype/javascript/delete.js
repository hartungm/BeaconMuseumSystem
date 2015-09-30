function myDelete() {
	Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

	var GameScore = Parse.Object.extend("GameScore");
	var query = new Parse.Query(GameScore);
	//var objID = "Fs5aa9IvYv"; //objID not in database, for testing bad ids
	//var objID = "4UaFvxLRhO";

	query.equalTo("playerName", "Jim Garfiel");
	query.find({
		success : function (results) {
			alert("Successfully retrieved " + results.length + " scores.");
			// Do something with the returned Parse.Object values
			
		},
		error : function (error) {
			alert("Error: " + error.code + " " + error.message);
		}
	});

	/*
	query.get(objID, {
		success : function (gameScore) {
			// The object was retrieved successfully.
			alert('object retrieved with objectId: ' + gameScore.id);
			/*
			query.destroy({
			success : function (gameScore) {
			// The object was deleted from the Parse Cloud.
			alert('object deleted with objectId: ' + gameScore.id);
			},
			error : function (object, error) {
			// The delete failed.
			// error is a Parse.Error with an error code and message.
			alert('object retrieved with objectId: ' + gameScore.id);
			}
			});
		
		},
		error : function (object, error) {
			// The object was not retrieved successfully.
			// error is a Parse.Error with an error code and message.
			alert('Failed to retrieve object, with error code: ' + error.message);
		}

	}); */

};
