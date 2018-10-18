package com.apap.tugas1.service;

import com.apap.tugas1.repository.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb JabatanDb;
	
	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		// TODO Auto-generated method stub
		return JabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanDb.save(jabatan);
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanDb.delete(jabatan);
	}

	@Override
	public List<JabatanModel> getAll() {
		// TODO Auto-generated method stub
		return JabatanDb.findAll();
	}

	@Override
	public void updateJabatan(JabatanModel jabatan, Long jabatanId) {
		// TODO Auto-generated method stub
		JabatanModel jabatanUpdate = JabatanDb.getOne(jabatanId);
		jabatanUpdate.setNama(jabatan.getNama());
		jabatanUpdate.setGaji_pokok(jabatan.getGaji_pokok());
		jabatanUpdate.setDeskripsi(jabatan.getDeskripsi());
		JabatanDb.save(jabatanUpdate);
	}

}
