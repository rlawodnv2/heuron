package heuron.com.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import heuron.com.entity.pati.TuserPatiImgInf;
import heuron.com.service.ImageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	/**
	 * @content 이미지 업로드 화면
	 * @param patiNo
	 * @param model
	 * @return
	 */
	@GetMapping("/images/upload/{patiNo}")
	public String uploadForm(@PathVariable(name="patiNo") long patiNo, Model model) {
		model.addAttribute("patiNo", patiNo);
		return "upload";
	}
	
	@PostMapping("/image/delete/{imgNo}/{patiNo}")
	public String deleteImage(@PathVariable(name="imgNo") long imgNo
							, @PathVariable(name="patiNo") long patiNo) {

	    // 연관 이미지 삭제 처리
	    List<TuserPatiImgInf> images = imageService.findByPati_PatiNo(patiNo);

	    for (TuserPatiImgInf img : images) {
	    	if(img.getImgNo() != imgNo) {
	    		continue;
	    	}
	        img.setDelYn('Y');
	        img.setModDts(LocalDate.now());
	        imageService.save(img);
	    }

	    return "redirect:/patients/view/" + patiNo;
	} 
}