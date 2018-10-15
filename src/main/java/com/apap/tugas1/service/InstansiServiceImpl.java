package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb InstansiDb;

	@Override
	public Optional<InstansiModel> getIntansiById(Long id) {
		// TODO Auto-generated method stub
		return InstansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return InstansiDb.findAll();
	}

	
	

}
