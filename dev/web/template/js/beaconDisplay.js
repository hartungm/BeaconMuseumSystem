function beaconDisplay() {
  var d = new Date();
  document.getElementById("tspan").innerHTML = d.toLocaleString();

  /* ID and Key for dhoov09's Parse database (set up and some testing)
   * var appID = "rSipE1Anb7MbLOLaober2KclUkc9pG2mjJq7mqaR";
   * var javascriptKey = "oF8u9ObEujTCWQTMuA34Vr39Cx0PJNy3SOY9rMUg";
   */

  // appID and key for MuseBeacon's Parse Database
  var appID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
  var javascriptKey = "PPQqXCZRwyBstXvoMtF2M9hwZecwZjqivq98w7dB";
  
  Parse.initialize(appID, javascriptKey);

  var DefaultTemplate = Parse.Object.extend("DefaultTemplate");
  var query = new Parse.Query(DefaultTemplate);

  query.each(
    function myF(object){
      var imgFile = object.get("Image");
      console.log(imgFile);
      var imgURL = imgFile.url();
      console.log(imgURL);
      //$("Image")[0].src = imgFile.url();

      // When using plebian tables...
      //$('#table tr:last').after('<tr><td>' + object.id + '</td><td>' + object.get('BeaconID') + '</td></tr>');

      $("#container").append("<div>" + object.id + " " + object.get('BeaconID') + " " + object.get('Title') + " " + object.get('Text') + "</div>");
      $("#container").append('<div> Image: <img src="imgURL" alt="misplaced photo"> </div>');
    });

  //https://www.parse.com/questions/retrieve-image-using-javascript-api
}