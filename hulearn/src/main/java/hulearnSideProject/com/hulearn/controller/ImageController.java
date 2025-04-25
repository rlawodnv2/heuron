package hulearnSideProject.com.hulearn.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final TuserPatiImgInfRepository imgRepository;
	private final TuserPatiBasRepository patiRepository;

	/**
	 * @content 이미지 업로드 화면
	 * @param patiNo
	 * @param model
	 * @return
	 */
	@GetMapping("/images/upload/{patiNo}")
	public String uploadForm(@PathVariable(name="patiNo") Integer patiNo, Model model) {
		model.addAttribute("patiNo", patiNo);
		return "upload";
	}
	
	@PostMapping("/image/delete/{imgNo}/{patiNo}")
	public String deleteImage(@PathVariable(name="imgNo") Integer imgNo
							, @PathVariable(name="patiNo") Integer patiNo) {
	    TuserPatiBas patient = patiRepository.findById(patiNo)
	    										.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

	    // 연관 이미지 삭제 처리
	    List<TuserPatiImgInf> images = imgRepository.findByPati_PatiNo(patiNo);
	    for (TuserPatiImgInf img : images) {
	    	if(img.getImgNo() != imgNo) {
	    		continue;
	    	}
	        img.setDelYn('Y');
	        img.setModDts(LocalDate.now());
	        imgRepository.save(img);
	    }

	    return "redirect:/patients/view/" + patiNo;
	} 
}