package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		pegawai.setJabatan(new ArrayList<JabatanPegawaiModel>());
		JabatanPegawaiModel jp = new JabatanPegawaiModel();
		jp.setPegawai(pegawai);
		pegawai.getJabatan().add(jp);
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		return "add-pegawai";
		
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params={"addJabatan"})
	public String addRowJabatan(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiNew = pegawai;
		
		JabatanPegawaiModel jabatan = new JabatanPegawaiModel();
		jabatan.setPegawai(pegawaiNew);
		pegawaiNew.getJabatan().add(jabatan);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		
/*		System.out.println(pegawai.getJabatan().get(0));
		pegawai.getJabatan().add(new JabatanPegawaiModel());*/
		model.addAttribute("pegawai", pegawai);
		
//		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
//		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listJabatan", listJabatan);
	    return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"save"})
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		//set nip
		String nip = pegawaiService.generateNip(pegawai);
		System.out.println(nip);
		pegawai.setNip(nip);
		System.out.println(pegawai.getNip());
		
		//List jabatan
		List<JabatanPegawaiModel> listJabPegawai = pegawai.getJabatan();
		
		pegawai.setJabatan(new ArrayList<JabatanPegawaiModel>());
		pegawaiService.addPegawai(pegawai);
		
		//menambahkan jabatan pegawai ke jabatanpegawaimodel
		for(JabatanPegawaiModel jabatan : listJabPegawai) {
			jabatan.setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatan);
			
		}
		model.addAttribute("penambahan", "Pegawai");
		return "add";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add";
		
	}
	
	@RequestMapping(value="/pegawai/ubah")
	private String ubahPegawai(@RequestParam(value="pegawaiNip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		if (pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			return "update-pegawai";
		}
		return "not-found";
		
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.updatePegawai(pegawai);
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

	@RequestMapping(path="/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Optional<String> idProvinsi, Optional<String> idInstansi, Optional<String> idJabatan, Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listProvinsi",listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		
		if(idProvinsi.isPresent()) {
			ProvinsiModel provinsi = provinsiService.getProvinsiById(Long.parseLong(idProvinsi.get())).get();
			if(idInstansi.isPresent()) {
				InstansiModel instansi = instansiService.getIntansiById(Long.parseLong(idInstansi.get())).get();
				List<PegawaiModel> pegawaiInstansi = instansi.getListPegawai();
				if(idJabatan.isPresent()) {
					JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan.get())).get();
				}
				else {
					
				}
			}
			
		}
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/instansi", method = RequestMethod.GET)
	public @ResponseBody
	List<InstansiModel> findAllInstansi(
	        @RequestParam(value = "idProvinsi", required = true) Long idProvinsi) {
		ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi).get();
		List<InstansiModel> instansi = instansiService.getAllInstansiByProvinsi(provinsi);
	    return instansi;
	}
	
	@RequestMapping(value = "/pegawai/carifilter", method = RequestMethod.GET)
	public @ResponseBody
	List<PegawaiModel> findPegawaiByFilter(
			 @RequestParam(value = "idProvinsi", required = true) Long idProvinsi, @RequestParam(value = "idInstansi", required = true) Long idInstansi) {
		ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi).get();
		System.out.println(provinsi.getNama());
		InstansiModel instansi = instansiService.getIntansiById(idInstansi).get();
		System.out.println(instansi.getNama());
	    return instansi.getListPegawai();
	}

}
