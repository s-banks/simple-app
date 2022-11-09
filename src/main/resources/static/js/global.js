"use strict";
(function () {
	$('#Modal').on('show.bs.modal', function (event) {
		let button = $(event.relatedTarget);
		let tr = button.parent().parent();
		let empId = button.data('empid');
		let fName = tr[0].cells[0].textContent;
		let lName = tr[0].cells[1].textContent;
		let email = tr[0].cells[2].textContent;
		let modal = $(this);
		modal.find('.modal-title').text('Edit employee: ' + fName + " " + lName);
		modal.find('#edit-id').val(empId);
		modal.find('#edit-fname').val(fName);
		modal.find('#edit-lname').val(lName);
		modal.find('#edit-email').val(email);
	})

}())
