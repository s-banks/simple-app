"use strict";
(function () {
	$('#Modal').on('show.bs.modal', function (event) {
		let button = $(event.relatedTarget); // Button that triggered the modal
		let tr = button.parent().parent();
		let empId = button.data('empid'); // Extract info from data-* attributes
		let fName = tr[0].cells[0].textContent;
		let lName = tr[0].cells[1].textContent;
		let email = tr[0].cells[2].textContent;
		// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		let modal = $(this);
		modal.find('.modal-title').text('Edit employee: ' + fName + " " + lName);
		modal.find('#edit-id').val(empId);
		modal.find('#edit-fname').val(fName);
		modal.find('#edit-lname').val(lName);
		modal.find('#edit-email').val(email);
	})

}())
