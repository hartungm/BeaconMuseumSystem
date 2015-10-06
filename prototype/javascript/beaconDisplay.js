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
      alert('selected objectID: ' + object.id);
      $('#table tr:last').after('<tr><td>' + object.id + '</td><td>' + object.get('BeaconID') + '</td></tr>');
    });

  // why is this executing first?
  alert("number of objects that matched query: ");
}