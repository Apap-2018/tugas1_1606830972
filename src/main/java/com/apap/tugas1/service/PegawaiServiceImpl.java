package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb PegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiByNIP(Long NIP) {
		// TODO Auto-generated method stub
		return PegawaiDb.findByNip(String.valueOf(NIP));
	}

	@Override
	public void addPegawai(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		PegawaiDb.save(Pegawai);
	}

}
