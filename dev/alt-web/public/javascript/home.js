$('.exhibit').on('click', function() {
	var name = $(this).find('.name h4').first().html();
	var collection = $(this).find('.collection h4').first().html();
	var objectID = $(this).find('input[name="objectID"]').val();
	var text = $(this).find('input[name="text"]').val();
	$('input[name="exhibitname"]').val(name);
	$('input[name="collectionname"]').val(collection);
	$('input[name="currentObjectID"]').val(objectID);
	$('textarea[name="text"]').val(text);
});