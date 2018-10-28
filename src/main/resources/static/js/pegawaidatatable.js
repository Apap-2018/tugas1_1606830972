$(document).ready( function () {
	console.log($(location).attr("href"))
	var url = new URL($(location).attr("href"));
	var idProvinsi = url.searchParams.get("idProvinsi")
	console.log(idProvinsi)
	var idInstansi = url.searchParams.get("idInstansi")
	console.log(idInstansi)
	
	var table = $('#pegawaiTable').DataTable({
			"sAjaxSource": "/pegawai/carifilter?idProvinsi="+ idProvinsi + "&idInstansi="+ idInstansi,
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nip"},
		      { "mData": "nama" },
				  { "mData": "tempat_lahir" },
				  { "mData": "tanggal_lahir" },
				  { "mData": "tahun_masuk" },
				  { "mData": "instansi.nama"},
				  { "mData": "jabatan.nama"},
				  
				  
			]
	 })
});