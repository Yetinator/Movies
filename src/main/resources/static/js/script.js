function doSearch() {  
            $.getJSON ("https://api.themoviedb.org/3/search/movie?api_key=e34f926e66fa7ffcaaea86c905cf10de",
            			{query: $('#searchBox2').val()},
            			
            			function(data) {
            			
            				$('#movieTitle').text('');
            			
						    $(data.results).each(function(id, title){
						    	$('#movieTitle').append("<option> " + this.title + "</option>");		
						    });
						   
							
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
						    	console.log(this.id);		
        					
        				}); //close function
	                    });//close getjson close fillDataByTitle
        }
        
function test(stringthis){
    console.log(stringthis + " is a string");
    
}
