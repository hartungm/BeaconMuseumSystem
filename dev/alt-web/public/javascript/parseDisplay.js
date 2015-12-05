$(function(){
  // appID and key for MuseBeacon's Parse Database
  var appID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
  var javascriptKey = "PPQqXCZRwyBstXvoMtF2M9hwZecwZjqivq98w7dB";
  
  Parse.initialize(appID, javascriptKey);

  var DefaultTemplate = Parse.Object.extend("DefaultTemplate");
  var query = new Parse.Query(DefaultTemplate);

  query.each(
    function myF(object){
      var exhibitText = object.get('Text');
      console.log(exhibitText);
      $(".sidebar").append("<div class='row sidebar-content exhibit'>"
        + "<input type='hidden' name='objectID' value=" + object.id + ">"
        + "<input type='hidden' name='text' value='" + exhibitText + "'>"
        + "<div class='col-md-6 name'><h4>" + object.get('Title') + "</h4></div>"
        + "<div class='col-md-6 collection'><h4>" + object.get('Collection') + "</h4></div></div>");
    });
});