$('.exhibit').on('click', function() {
	var name = $(this).find('.name h4').first().html();
	var collection = $(this).find('.collection h4').first().html();
	var objectID = $(this).find('input[name="objectID"]').val();
	$('input[name="exhibitname"]').val(name);
	$('input[name="collectionname"]').val(collection);
	$('input[name="currentObjectID"]').val(objectID);
});