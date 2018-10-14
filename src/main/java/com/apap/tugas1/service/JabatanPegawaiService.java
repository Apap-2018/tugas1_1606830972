package com.apap.tugas1.service;

import com.apap.tugas1.model.*;
import java.util.List;
import java.util.Optional;

public interface JabatanPegawaiService {
	Optional<List<JabatanPegawaiModel>> getJabatanByNip(String nip);

}
