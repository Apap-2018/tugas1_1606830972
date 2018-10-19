package com.apap.tugas1.controller;
import com.apap.tugas1.model.*;
import com.apap.tugas1.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value="/instansi", method=RequestMethod.GET)
	public @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value= "provinsiId", required=true) Long provinsiId){
		List<InstansiModel> listInstansi = provinsiService.getProvinsiById(provinsiId).get().getListInstansi();
		return listInstansi;
	}

}
