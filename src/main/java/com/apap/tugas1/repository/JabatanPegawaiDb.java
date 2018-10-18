package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanPegawaiModel;

@Repository
public interface JabatanPegawaiDb extends JpaRepository<JabatanPegawaiModel,Long>{
	Optional<List<JabatanPegawaiModel>> findAllByPegawai_Nip(String nip);

	List<JabatanPegawaiModel> findAllByJabatan_Id(Long id);
	
	/*List<JabatanPegawaiModel> findAllByJabatan();*/
}
