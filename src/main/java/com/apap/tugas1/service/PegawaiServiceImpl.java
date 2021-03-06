package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.*;

import java.sql.Date;
import java.util.ArrayList;
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
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;

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
	public void updatePegawai(PegawaiModel pegawaiLama, PegawaiModel pegawaiBaru) {
		// TODO Auto-generated method stub
		pegawaiBaru.setNama(pegawaiLama.getNama());
		pegawaiBaru.setNip(pegawaiLama.getNip());
		pegawaiBaru.setTahun_masuk(pegawaiLama.getTahun_masuk());
		pegawaiBaru.setTanggal_lahir(pegawaiLama.getTanggal_lahir());
		pegawaiBaru.setTempat_lahir(pegawaiLama.getTempat_lahir());
//		pegawaiUpdate.setJabatan(pegawai.getJabatan());
		pegawaiBaru.setInstansi(pegawaiLama.getInstansi());
		int jumlahList = pegawaiLama.getJabatan().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiLama.getJabatan().get(i).setJabatan(pegawaiBaru.getJabatan().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiBaru.getJabatan().size(); i++) {
			pegawaiBaru.getJabatan().get(i).setPegawai(pegawaiLama);
			jabatanPegawaiDb.save(pegawaiBaru.getJabatan().get(i));
		}
		PegawaiDb.save(pegawaiBaru);
	}

	@Override
	public List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiInstansi = instansi.getListPegawai();
		List<PegawaiModel> pencarian = new ArrayList<>();
		long idJabatan = jabatan.getId();
		
		for(PegawaiModel peg : pegawaiInstansi) {
			for(JabatanPegawaiModel jab: peg.getJabatan()) {
				if(jab.getJabatan().getId() == idJabatan) {
					pencarian.add(peg);
				}
			}
		}
		return pencarian;
	}

	@Override
	public List<PegawaiModel> findPegawaiByProvinsiAndJabatan(List<PegawaiModel> pegawaiProvinsi,
			JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pencarian = new ArrayList<>();
		
		for(PegawaiModel peg: pegawaiProvinsi) {
			for(JabatanPegawaiModel jab: peg.getJabatan()) {
				if(jab.getJabatan().getId() == jabatan.getId()) {
					pencarian.add(peg);
				}
			}
		}
		return pencarian;
	}

//	@Override
//	public JabatanModel getAllJabatan() {
//		// TODO Auto-generated method stub
//		return PegawaiDb.;
//	}

}
