function beaconDisplay() {
  // appID and key for MuseBeacon's Parse Database
  // var appID = // Get from user's login?
  var appID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
  var javascriptKey = "PPQqXCZRwyBstXvoMtF2M9hwZecwZjqivq98w7dB";
  
  Parse.initialize(appID, javascriptKey);

  var DefaultTemplate = Parse.Object.extend("DefaultTemplate");
  var query = new Parse.Query(DefaultTemplate);

  query.each(
    function myF(object){
		$(".sidebar col-md-2").append("<div class='row sidebar-content exhibit'>"
			+ "<input type='hidden' name='objectID' value=" + object.id + "/>"
			+ "<input type='hidden' name='text' value=" + object.get('Text') + "/>"
			+ "<div class='col-md-6 name'><h4>" + object.get('Title') + "</h4></div>"
			+ "<div class='col-md-6 collection'><h4>" + object.get('Collection') + "</h4></div></div>");
    });
}

// needs paramater to determine this object
// update in home.jade
function submitChanges(beaconEntry) {
	var name = document.getElementsByName('exhibitname');
	var collectionName = document.getElementsByName('collectionname');
	var description = document.getElementsByName('text');
	
	// better to compare with parse and see if a change was made? 
	// or just resend the data anyways?
	beaconEntry.set('Title', name);
	beaconEntry.set('Collection', collectionName);
	beaconEntry.set('Text', description);
	beaconEntry.save();
}