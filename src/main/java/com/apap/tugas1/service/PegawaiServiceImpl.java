package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.*;

import java.sql.Date;
import java.util.List;
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

	@Override
	public PegawaiModel pegawaiTertua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> listPegawai = PegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listPegawai.get(0);
	}

	@Override
	public PegawaiModel pegawaiTermuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> listPegawai = PegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listPegawai.get(listPegawai.size()-1);
	}

	@Override
	public String generateNip(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		String nip = "";
		nip += Pegawai.getInstansi().getId();
		Date date = Pegawai.getTanggal_lahir();
		String[] tgl = (""+date).split("-");
		for (int i = tgl.length-1; i >= 0; i--) {
			int ukuranTgl = tgl[i].length();
			nip += tgl[i].substring(ukuranTgl-2, ukuranTgl);
		}
		nip += Pegawai.getTahun_masuk();
		List<PegawaiModel> listPegawai = PegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(date, Pegawai.getTahun_masuk(), Pegawai.getInstansi());
		if(listPegawai.size() < 10) {
			nip += "0"+listPegawai.size();
		}
		else {
			nip += listPegawai.size();
		}
		Pegawai.setNip(nip);
		System.out.println(nip);
		return nip;
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel pegawaiUpdate = PegawaiDb.getOne(pegawai.getId());
		pegawaiUpdate.setNama(pegawai.getNama());
		pegawaiUpdate.setNip(pegawai.getNip());
		pegawaiUpdate.setTahun_masuk(pegawai.getTahun_masuk());
		pegawaiUpdate.setTanggal_lahir(pegawai.getTanggal_lahir());
		pegawaiUpdate.setTempat_lahir(pegawai.getTempat_lahir());
		pegawaiUpdate.setJabatan(pegawai.getJabatan());
		pegawaiUpdate.setInstansi(pegawai.getInstansi());
		PegawaiDb.save(pegawaiUpdate);
	}

//	@Override
//	public JabatanModel getAllJabatan() {
//		// TODO Auto-generated method stub
//		return PegawaiDb.;
//	}

}
