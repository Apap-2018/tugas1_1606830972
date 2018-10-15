package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah")
	private String tambahJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "submit-jabatan";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="jabatanId") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
		
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	private String ubahJabatan(@RequestParam(value="jabatanId") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		model.addAttribute("jabatan", jabatan);
		return "ubah-jabatan";
	}

	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "ubah";
	}
}
