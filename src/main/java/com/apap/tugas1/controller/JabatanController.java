package com.apap.tugas1.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired 
	private JabatanPegawaiService jpService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(value = "/jabatan/tambah")
	private String tambahJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model, RedirectAttributes redirectAttrs) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		redirectAttrs.addAttribute("id", jabatan.getId());
		redirectAttrs.addFlashAttribute("message", "SUKSES! Berhasil menambah jabatan");
		redirectAttrs.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/jabatan/view?jabatanId={id}";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="jabatanId") String id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		List<JabatanPegawaiModel> listPegawai = jabatan.getListPegawai();
		model.addAttribute("jumlahPegawai", listPegawai.size());
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
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model, RedirectAttributes redirectAttrs) {
		jabatanService.updateJabatan(jabatan, jabatan.getId());
		model.addAttribute("jabatan", jabatan);
		redirectAttrs.addAttribute("id", jabatan.getId());
		redirectAttrs.addFlashAttribute("message", "SUKSES! Berhasil merubah jabatan");
		redirectAttrs.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/jabatan/view?jabatanId={id}";
	}
	
	@RequestMapping(value="/jabatan/viewall")
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listJabatan", listJabatan);
		return "viewall-jabatan";
	}
	
	@RequestMapping(path="/alljabatan", method=RequestMethod.GET)
	public @ResponseBody List<JabatanModel> getAllJabatan(){
		return jabatanService.getAll();
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String hapusJabatan(String jabatanId) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(jabatanId)).get();
		System.out.println(jabatan);
		List<JabatanPegawaiModel> listPegawai = jpService.getPegawaiById(Long.parseLong(jabatanId));
		System.out.println(listPegawai);
		if(listPegawai.isEmpty()) {
			jabatanService.deleteJabatan(jabatan);
			return "delete";
		}
		else {
			return "undone-delete";
		}
	}
	
//	@RequestMapping(value="/pegawai-jabatan")
//	private String pegawaiSiJabatan(Model model) {
//		List<PegawaiModel> listJabatan = pegawaiService.
//		model.addAttribute("listJabatan", listJabatan);
//		return "list-pegawai-jabatan";
//	}
}
