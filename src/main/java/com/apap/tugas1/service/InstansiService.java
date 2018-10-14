package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface InstansiService {
	Optional<InstansiModel> getIntansiById(Long id);

}
