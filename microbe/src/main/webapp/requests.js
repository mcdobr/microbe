apiPath = 'http://localhost:8080/microbe/webapi/'

getAllLeagues = {
	type: 'GET',
	url: apiPath + 'leagues',
	dataType: 'json'
};

//console.log($.ajax(getAllLeagues).responseJSON);