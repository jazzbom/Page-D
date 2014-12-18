/**
 * 
 */

$(document).ready(function(){
	

		var selectedModels;
	
		var Territory = Backbone.Model.extend({});

		var Territories = Backbone.Collection.extend({
		  model: Territory,
		  url: "https://api.mongolab.com/api/1/databases/m/collections/pages?apiKey=UeKTseM8zrDXfBUgyECcEI5VXMOacVrd"
		});

		var territories = new Territories();
		
		var columns = [{

		    // name is a required parameter, but you don't really want one on a select all column
		    name: "",

		    // Backgrid.Extension.SelectRowCell lets you select individual rows
		    cell: "select-row",

		    // Backgrid.Extension.SelectAllHeaderCell lets you select all the row on a page
		    headerCell: "select-all",

		  },{
		    name: "name",
		    label: "Name",
		    // The cell type can be a reference of a Backgrid.Cell subclass, any Backgrid.Cell subclass instances like *id* above, or a string
		    cell: "string" // This is converted to "StringCell" and a corresponding class in the Backgrid package namespace is looked up
		  }, {
		    name: "status",
		    label: "Status",
		    cell: "String" // An integer cell is a number cell that displays humanized integers
		  
		},{
		    name: "url",
		    label: "URL",
		    // The cell type can be a reference of a Backgrid.Cell subclass, any Backgrid.Cell subclass instances like *id* above, or a string
		    cell: "uri" // This is converted to "StringCell" and a corresponding class in the Backgrid package namespace is looked up
		  }];

		// Initialize a new Grid instance
		var grid = new Backgrid.Grid({
		  columns: columns,
		  collection: territories
		});

		// Render the grid and attach the root to your HTML document
		$("#list").append(grid.render().el);

		
		territories.fetch({reset: true});
		
		setInterval(function(){ 
		// Fetch some countries from the url
		territories.fetch({reset: true});
		console.log("job");
	
		
		}, 30000);
	//ervy 72hrs 259200000
		
		
	
		$( "#update" ).click(function() {
			  
			 this.selectedModels = grid.getSelectedModels();
				_.each(this.selectedModels, function(model){
					
					//console.log(model.get("name"));
					var n = model.get("name");
					var u = model.get("url");
					
					  $.ajax({
						     url: "DoUpdate",              
						     type: "POST",
						     data: { name : n,uri : u },
						     success: function(data){
						      //If you want to return anything in jsp.
						    	 console.log("data sent to DoUpdate");
						    	 territories.fetch({reset: true});
						       } 
						     });
					
					
					model.destroy();
				});
		
			});

		$( "#remove" ).click(function() {
			  
			 this.selectedModels = grid.getSelectedModels();
				_.each(this.selectedModels, function(model){
					
					//console.log(model.get("name"));
					var n = model.get("name");
					var u = model.get("url");
					
					  $.ajax({
						     url: "DoRemove",              
						     type: "POST",
						     data: { name : n,uri : u },
						     success: function(data){
						      //If you want to return anything in jsp.
						    	 console.log("data sent to DoUpdate");
						    	 territories.fetch({reset: true});
						       } 
						     });
					
					
					model.destroy();
				});
		
			});
		


		
	
	  
}); 