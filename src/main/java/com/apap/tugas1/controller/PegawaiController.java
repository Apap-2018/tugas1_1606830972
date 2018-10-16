package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAll();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value="/pegawai")
	private String viewPegawai(@RequestParam(value="pegawaiNip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByNip(nip).get();
		double gaji = 0.0;
		for(JabatanPegawaiModel jabatans : jabatanPegawai) {
			if (jabatans.getJabatan().getGaji_pokok() > gaji) {
				gaji=jabatans.getJabatan().getGaji_pokok();
			}
		}
		gaji += pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * gaji;
		model.addAttribute("gaji", (long)gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanPegawai", jabatanPegawai);
		return "view-pegawai";
		
	}
	
	@RequestMapping("/pegawai/tambah")
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		return "add-pegawai";
		
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawai.setNip("");
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add";
		
	}
	
	@RequestMapping(value="/pegawai/ubah")
	private String ubahPegawai(@RequestParam(value="pegawaiNip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		if (pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			return "update-pegawai";
		}
		return "not-found";
		
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		return "update";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua")
	private String viewPegawaiTermudaTertua(@RequestParam(value="instansiId") String id, Model model) {
		InstansiModel instansi = instansiService.getIntansiById(Long.parseLong(id)).get();
		PegawaiModel pegawaiTertua = pegawaiService.pegawaiTertua(instansi);
		PegawaiModel pegawaiTermuda = pegawaiService.pegawaiTermuda(instansi);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "view-pegawai-ter";
	}

}
