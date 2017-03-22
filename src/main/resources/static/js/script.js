function doSearch() {  
            $.getJSON ("https://api.themoviedb.org/3/search/movie?api_key=e34f926e66fa7ffcaaea86c905cf10de",
            			{query: $('#searchBox2').val()},
            			
            			function(data) {
            			
            				$('#movieTitle').text('');
            			
						    $(data.results).each(function(id, title){
						    	$('#movieTitle').append('<option> ' + this.title + "</option>");		
						    });
						   
							
        				});
        					
        				
        }
function prepareSubmit() {  
            $.getJSON ("https://api.themoviedb.org/3/search/movie?api_key=e34f926e66fa7ffcaaea86c905cf10de",
            			{query: $('#searchBox2').val()},
            			
            			function(data) {
            			    //prep loop function
            			    var idLocal;
            				$('#idSubmit').text('');
            				$('#finalList').text('');
            			
						    $(data.results).each(function(id, title, overview){
						    	//do things
						    	$('#finalList').append('<input type = "radio" name = "a movie of the same name" value = "' + this.id + '"><br/><div value = "' + this.title + '"></div><br/><div value = "' + this.overview + '"></div></input>');		
						    });
						    $('#idSubmit').append( idLocal );
							
        				});
        					
        				
        }        
        
function fillDataByTitle(finput) {
        	//var queryURL = "https://api.themoviedb.org/3/search/movie?api_key=e34f926e66fa7ffcaaea86c905cf10de%26query="
        	//queryURL += "braveheart";
        	
        	$.getJSON ("https://api.themoviedb.org/3/search/movie?api_key=e34f926e66fa7ffcaaea86c905cf10de&query=" + finput,
        	
        				//{query: $(finput)},
        				
        				function(data) {
        				    $(data.results).each(function(id, title){
						    	console.log("hey" + this.title);
						    	console.log("this is the id from javascript: " + this.id);		
        					
        				}); //close function
	                    });//close getjson close fillDataByTitle
        }
        
function movieObjectPopulate(tmdbid){
            //This function creates a movie object for user while displaying
            //a movie through an invite entry where all that is passed to 
            //the front end it the tmdbid.  It should work similar to a movie
            //service object. 
            var baseUrl = "https://api.themoviedb.org/3/movie/";
            var apiKey = "e34f926e66fa7ffcaaea86c905cf10de";
            var midUrl = "?api_key=";
            var url = baseUrl + tmdbid + midUrl + apiKey;
            $.getJSON (url,
                function(data) {
                    $(data).each(function(id, title, poster_path, overview, release_date, runtime, status, backdrop_path){
                        $
                        console.log("the title: " + this.title);
                    });
                });
            
}

function movieArrayToObjectManager(){
            //This function handles a movie object that is passed from the 
            //backend to the front end with the intent of displaying 
}
        
function test(stringthis){
    console.log(stringthis + " is a string");
    
}
