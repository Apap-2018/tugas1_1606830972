package com.apap.tugas1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public Optional<ProvinsiModel> getProvinsiById(Long id) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(id);
	}

}
