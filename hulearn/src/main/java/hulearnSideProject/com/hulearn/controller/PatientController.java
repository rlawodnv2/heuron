package hulearnSideProject.com.hulearn.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hulearnSideProject.com.hulearn.config.FileSaveUtil;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.service.ImageService;
import hulearnSideProject.com.hulearn.service.PatientService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private final PatientService patientSerivce;
	
	private final ImageService imageService;
	
	@GetMapping("/patient")
	public String newPatientForm() {
		
		return "patient";
	}

	@PostMapping("/patients/save")
	@Transactional
	public String savePatient(@RequestParam(name = "patiNm") String patiNm,
							  @RequestParam(name = "age") int age,
							  @RequestParam(name = "genCd") String genCd,
							  @RequestParam(name = "diseaseYn") Character diseaseYn,
							  @RequestParam(name = "files",required = false) MultipartFile[] files,
							  Model model) throws IOException {

		
		TuserPatiBas patient = TuserPatiBas.builder()
											.patiNm(patiNm)
											.age(age)
											.genCd(genCd)
											.diseaseYn(diseaseYn)
											.delYn('N')
											.hpNo(null)
											.regrNo("KIMJAEWOO")
											.regPgmUrl("/patients/save")
											.regDts(LocalDate.now())
											.modrNo("KIMJAEWOO")
											.modPgmUrl("/patients/save")
											.modDts(LocalDate.now())
											.build();
		
		try {
			
			patientSerivce.save(patient);

			for(MultipartFile file : files) {
				String savedImageUrl = FileSaveUtil.saveFile(file);

				TuserPatiImgInf image = TuserPatiImgInf.builder()
														.pati(patient)
														.imgUrl(savedImageUrl)
														.imgNm(file.getOriginalFilename())
														.delYn('N')
														.regrNo("KIMJAEWOO")
														.regPgmUrl("/api/images/upload")
														.regDts(LocalDate.now())
														.modrNo("KIMJAEWOO")
														.modPgmUrl("/api/images/upload")
														.modDts(LocalDate.now())
														.build();

				imageService.save(image);
			}

			model.addAttribute("message", "환자 및 이미지 저장 완료!");
			
		} catch(RuntimeException e) {
			log.error("환자 및 이미지 저장시 오류 발생 :: {}",e);
			throw new RuntimeException("환자 및 이미지 저장시 오류 발생");
		}
		
		return "redirect:/patients/"+patient.getPatiNo();
	}
	
	/**
	 * @content 환자 정보 화면
	 * @param patiNo
	 * @param model
	 * @return
	 */
	@GetMapping("/patients/{patiNo}")
	public String patientSearch(@PathVariable(name="patiNo") long patiNo, Model model) {
		TuserPatiBas patient = patientSerivce.findById(patiNo);

/*		List<TuserPatiImgInf> images = imgRepository.findByPati_PatiNo(patiNo)
													.stream().filter(img -> YN.Y.equals(img.getDelYn())).collect(Collectors.toList());
*/
//		log.info("이미지 개수: {}", images.size());

		model.addAttribute("patient", patient);
//		model.addAttribute("images", images);
		return "patient";
	}
	
	/**
	 * @content 이미지 및 정보 수정
	 * @param patiNo
	 * @param patiNm
	 * @param age
	 * @param genCd
	 * @param diseaseYn
	 * @param files
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/patients/update/{patiNo}")
	@Transactional
	public String updatePatient(@PathVariable(name="patiNo") long patiNo,
								@RequestParam(name="patiNm") String patiNm,
								@RequestParam(name="age") int age,
								@RequestParam(name="genCd") String genCd,
								@RequestParam(name="diseaseYn") Character diseaseYn,
								@RequestParam(name="files",required = false) MultipartFile[] files,
								Model model) throws IOException {

		
		try {
			TuserPatiBas patient = patientSerivce.findById(patiNo);

			patient.setPatiNm(patiNm);
			patient.setAge(age);
			patient.setGenCd(genCd);
			patient.setDiseaseYn(diseaseYn);
			patient.setModDts(LocalDate.now());
			patient.setModrNo("KIMJAEWOO");
			patient.setModPgmUrl("/patients/update");

			patientSerivce.save(patient);
			
			for(MultipartFile file : files) {
				String savedImageUrl = FileSaveUtil.saveFile(file);

				TuserPatiImgInf image = TuserPatiImgInf.builder()
														.pati(patient)
														.imgUrl(savedImageUrl)
														.imgNm(file.getOriginalFilename())
														.delYn('N')
														.regrNo("KIMJAEWOO")
														.regPgmUrl("/api/images/upload")
														.regDts(LocalDate.now())
														.modrNo("KIMJAEWOO")
														.modPgmUrl("/api/images/upload")
														.modDts(LocalDate.now())
														.build();

				imageService.save(image);
			}
			
		} catch(RuntimeException e) {
			log.error("환자정보 수정오류 :: {}",e);
			throw new RuntimeException("환자정보 수정 오류");
		}
		

		return "redirect:/patients/view/" + patiNo;
	}
	
	/**
	 * @content 환자정보 삭제
	 * @param patiNo
	 * @return
	 */
	@PostMapping("/patients/delete/{patiNo}")
	public String deletePatient(@PathVariable(name="patiNo") long patiNo) {
		TuserPatiBas patient = patientSerivce.findById(patiNo);

		patient.setDelYn('Y');
		patient.setModDts(LocalDate.now());
		patientSerivce.save(patient);

		// 연관 이미지도 삭제 처리
		/*
		List<TuserPatiImgInf> images = imgRepository.findByPati_PatiNo(patiNo);
		for (TuserPatiImgInf img : images) {
			img.setDelYn("Y");
			img.setModDts(LocalDate.now());
			imgRepository.save(img);
		}
		*/
		return "redirect:/patients/list";
	}
	
	@GetMapping("/patients/list")
	public String listPatients(@RequestParam(name="patiNm",required = false) String patiNm, Model model) {
		
		List<TuserPatiBas> patients = patientSerivce.findByDelYn(patiNm);
		
		model.addAttribute("patients", patients);
		model.addAttribute("keyword", patiNm);
		return "patient-list";
	}
	
	@GetMapping("/patients/view/{patiNo}")
	public String viewPatient(@PathVariable(name="patiNo") long patiNo, Model model) {
		
		TuserPatiBas patient = patientSerivce.findById(patiNo);

		List<TuserPatiImgInf> images = imageService.findByPati_PatiNo(patiNo);

		model.addAttribute("patient", patient);
		model.addAttribute("images", images);
		return "patient-view";
	}
}
