$(document).ready( function () {
	 var table = $('#jabatanTable').DataTable({
			"sAjaxSource": "/alljabatan",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nama"},
		      { "mData": "deskripsi" },
				  { "mData": "gaji_pokok" },
			]
	 })
});