package hulearnSideProject.com.hulearn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.dto.patient.ImageDto;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.mapper.PatientMapper;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {

	private final TuserPatiImgInfRepository imgRepository;
	private final TuserPatiBasRepository patiRepository;
	
	private final String uploadDir = "C:/uploads";

	/**
	 * @content 이미지 업로드 화면
	 * @param patiNo
	 * @param model
	 * @return
	 */
	@GetMapping("/api/images/upload/{patiNo}")
	public String uploadForm(@PathVariable Integer patiNo, Model model) {
		model.addAttribute("patiNo", patiNo);
		return "upload";
	}
	
	
	/**
	 * @content 이미지조회
	 * @param patiNo
	 * @return
	 */
	@GetMapping("/api/images/{patiNo}")
	public ResponseEntity<List<ImageDto>> getImagesByPatient(@PathVariable Integer patiNo) {
		TuserPatiBas patient = patiRepository.findById(patiNo)
			.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));
		List<ImageDto> result = patient.getImageList()
			.stream()
			.map(PatientMapper::toDto)
			.toList();
		return ResponseEntity.ok(result);
	}
	
	
	/**
	 * @content 이미지 업로드
	 * @param patiNo
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/api/images/upload/{patiNo}")
	public ResponseEntity<?> uploadImage(@PathVariable Integer patiNo,
										 @RequestParam("file") MultipartFile file) throws IOException {

		TuserPatiBas patient = patiRepository.findById(patiNo)
				.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

		if (file.isEmpty()) {
			throw new RuntimeException("파일이 없습니다");
		}

		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String originalFilename = file.getOriginalFilename();
		String newFileName = UUID.randomUUID() + "_" + originalFilename;
		String filePath = uploadDir + File.separator + newFileName;

		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

		String imageUrl = "http://localhost:8080/uploads/" + newFileName;

		TuserPatiImgInf image = TuserPatiImgInf.builder()
				.pati(patient)
				.imgUrl(imageUrl)
				.imgNm(originalFilename)
				.delYn("N")
				.regrNo("KIMJAEWOO")
				.regPgmUrl("/api/images/upload")
				.regDts(LocalDate.now())
				.modrNo("KIMJAEWOO")
				.modPgmUrl("/api/images/upload")
				.modDts(LocalDate.now())
				.build();

		imgRepository.save(image);

		return ResponseEntity.ok(Map.of(
			"message", "업로드 성공",
			"imageUrl", imageUrl
		));
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
	        img.setDelYn("Y");
	        img.setModDts(LocalDate.now());
	        imgRepository.save(img);
	    }

	    return "redirect:/patients/view/" + patiNo;
	} 
}