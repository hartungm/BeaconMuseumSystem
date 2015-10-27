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
      //console.log(imgFile);
      var imgURL = imgFile.url();
      //console.log(imgURL);

      /*$("#container").append("<div class='row'>" + object.id + " " + object.get('BeaconID') + " " + object.get('Title') + " " + object.get('Text') + "</div>"); */
      $("#container").append("<div class='row'><span>" 
        + object.id + " </span><span> " 
        + object.get('BeaconID') + " </span><span> " 
        + object.get('Title') + " </span><span> " + object.get('Text') 
        + "</span><span id='hover'> imagename </span><button onclick='myEdit()'> edit </button> <button onclick='myDelete()'> delete </button></div>");
      //$("#container").append("<div class='imgRow'><img src='" + imgURL + "' alt='misplaced photo' style='visibility:hidden;' id='img'> </div>");

      //$("#container").append("<a href='" + imgURL + "' onmouseover='" + img.style.visibility + " ='visible'; img.src="earth.jpg";'> image link </a>");
      //$("#container").append("<div class='imgRow'><img src='" + imgURL + "' alt='misplaced photo'> </div>");
      // OR all in same line?
      /*
      $("#container").append("<div class='row'><span> " + object.id + " </span><span> " 
        + object.get('BeaconID') + " </span><span> " + object.get('Title') + " </span><span> " 
        + object.get('Text') + " </span><span> " + "<img src='" + imgFile.url() 
        + "' alt='misplaced photo'> </span></div>");
*/
});

  //https://www.parse.com/questions/retrieve-image-using-javascript-api
  //https://linkpeek.com/blog/display-image-on-hover-using-html-javascript-and-css.html
  //http://www.webdeveloper.com/forum/showthread.php?255807-mouse-over-links-show-images
}

function myEdit(){
  alert( "Handler for edit click called." );
}

function myDelete(){
  alert( "Handler for delete click called." );
}