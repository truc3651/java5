
	console.log("hello")
	
	$(document).ready(function(){
		// show modal with information
		$('#newBtn, #editBtn').on('click', function(event){
			event.preventDefault()  
		
			$('.modal').modal("show").find(".modal-dialog").load($(this).attr("href"))
		})
		
		// show the file name
		$(".custom-file-input").on("change", function() {
		  var fileName = $(this).val().split("\\").pop();
		  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
		});
		
		$(".custom-file-input").change(function() {
            showPhoto(this);
        });
		
	})
	
	function showPhoto(fileInput){
        file = fileInput.files[0];
        reader = new FileReader();

        reader.onload = function(e) {
            console.log(e);
            $("#previewPhoto").attr("src", e.target.result);
        };
        reader.readAsDataURL(file);
	}
	
	
	
	
	
	