package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
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
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value="/pegawai")
	private String viewPegawai(@RequestParam(value="pegawaiNip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByNip(nip).get();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanPegawai", jabatanPegawai);
		return "view-pegawai";
		
	}
	
	@RequestMapping("/pegawai/tambah")
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
		
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

}
