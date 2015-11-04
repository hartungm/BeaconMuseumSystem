function ParsePull(){
	// Function is called on form load to populate the input entries with
	// the values from the parse database.
	var d = new Date();
	document.getElementById("tspan").innerHTML = d.toLocaleString();

	var appID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
	var javascriptKey = "PPQqXCZRwyBstXvoMtF2M9hwZecwZjqivq98w7dB";

	Parse.initialize(appID, javascriptKey);

	var DefaultTemplate = Parse.Object.extend("DefaultTemplate");
	var query = new Parse.Query(DefaultTemplate);

	//query parse to get the data i want? or pass it via GET or POST?
	//query.equalTo()
	//query.find({

	//});
}