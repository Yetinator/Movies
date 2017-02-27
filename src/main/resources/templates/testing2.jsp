<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Loose Search</title>
        //bring in jquery (maven may do this for me...
        <script type="text/javascript" src="jquery-3.1.1.jar"/>
    
    
        //do search function    
        <script type="text/javascript"> 
        
            function doSearch() {
                //make request to server (don't write it myself)
                //jquery is a good interface here
                
                $getJSON ("looseSearch.do",
                			{CHARS: $('#searchBox').val()},
                			
                			function(data) {
                				alert("response received " + data);
            				};
            }
            
        </script>
    
    </head>
    
    <body>
    
        <h1>Loose Search</h1>
        
        <input type="text" onKeyUp="doSearch();" id="searchBox"/>
        
        <div id="results">
            Results will appear here...
        </div>
        
    </body>
</html>