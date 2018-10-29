package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiByNIP(Long NIP);
	
	void addPegawai(PegawaiModel Pegawai);
	
	PegawaiModel pegawaiTertua(InstansiModel instansi);
	
	PegawaiModel pegawaiTermuda(InstansiModel instansi);
	
//	JabatanModel getAllJabatan();
	
	String generateNip(PegawaiModel pegawai);
	
	void updatePegawai(PegawaiModel pegawaiLama, PegawaiModel pegawaiBaru);
	
	List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	
	List<PegawaiModel> findPegawaiByProvinsiAndJabatan(List<PegawaiModel> pegawaiProvinsi,JabatanModel jabatan);

}
