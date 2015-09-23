Parse.initialize("rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR", "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg");

var GameScore = Parse.Object.extend("GameScore");
var query = new Parse.Query(GameScore);

query.destroy({
  success: function(query) {
    // The object was deleted from the Parse Cloud.
	alert('object deleted with objectId: ' + gameScore.id);
  },
  error: function(myObject, error) {
    // The delete failed.
    // error is a Parse.Error with an error code and message.
	alert('object retrieved with objectId: ' + gameScore.id);
  }
});