function beaconDisplay() {
  var d = new Date();
  document.getElementById("tspan").innerHTML = d.toLocaleString();

  // appID and key for MuseBeacon's Parse Database
  var appID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
  var javascriptKey = "PPQqXCZRwyBstXvoMtF2M9hwZecwZjqivq98w7dB";
  
  Parse.initialize(appID, javascriptKey);

  var DefaultTemplate = Parse.Object.extend("DefaultTemplate");
  var query = new Parse.Query(DefaultTemplate);

  query.each(
    function myF(object){
      $("#listContainer").append("<div class='row'><span onclick='titleClick(object)'>" 
        + object.get('Title') + " </span><span> " 
        + object.get('Collection') + "</span><button onclick='myDelete()'> delete </button></div>");
    });
}

function titleClick(object){
  alert("Handler for title click called " + object.get('Title'));
  //title
  //collection
  //beaconID
  //text
  //image
  //edit button

  /*
  $('#recordContainer').append("<div> Title: " + object.get('Title')
   + " Collection: " + object.get('Collection') 
   + " BeaconID: " + object.get('beaconID') 
   + " Text: " + object.get('Text') 
   + "</div>");
  */
}

/*
function myEdit(){
  alert( "Handler for edit click called." );
}
*/

function myDelete(){
  alert( "Handler for delete click called." );
}