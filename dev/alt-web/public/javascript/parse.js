// needs paramater to determine this object
// update in home.jade
function submitChanges(beaconID) {
	var name = document.getElementsByName('exhibitname');
	var collectionName = document.getElementsByName('collectionname');
	var description = document.getElementsByName('text');
	
	var BeaconEntry = Parse.Object.extend("DefaultTemplate");
	var BeaconEntry = new DefaultTemplate();

	// better to compare with parse and see if a change was made? 
	// or just resend the data anyways?
	beaconEntry.set('Title', name);
	beaconEntry.set('Collection', collectionName);
	beaconEntry.set('Text', description);
	beaconEntry.save();
}