package hulearnSideProject.com.hulearn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.Gender;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private final TuserPatiImgInfRepository imgRepository;
	
	private final TuserPatiBasRepository patiRepository;

	private final String uploadDir = "C:/uploads";
	
	@GetMapping("/patient")
	public String newPatientForm() {
		
		return "patient";
	}

	@PostMapping("/patients/save")
	@Transactional
	public String savePatient(@RequestParam(name = "patiNm") String patiNm,
							  @RequestParam(name = "age") int age,
							  @RequestParam(name = "genCd") Gender genCd,
							  @RequestParam(name = "diseaseYn") YN diseaseYn,
							  @RequestParam(name = "files",required = false) MultipartFile[] files,
							  Model model) throws IOException {

		try {
			
			// 1. 환자 저장
			TuserPatiBas patient = TuserPatiBas.builder()
												.patiNm(patiNm)
												.age(age)
												.genCd(genCd)
												.diseaseYn(diseaseYn)
												.delYn(YN.N)
												.hpNo(null)
												.regrNo("KIMJAEWOO")
												.regPgmUrl("/patients/save")
												.regDts(LocalDate.now())
												.modrNo("KIMJAEWOO")
												.modPgmUrl("/patients/save")
												.modDts(LocalDate.now())
												.build();

			patiRepository.save(patient);

			// 2. 이미지 저장
			if (files != null) {
				File dir = new File(uploadDir);
				if (!dir.exists()) dir.mkdirs();

				for (MultipartFile file : files) {
					if (file.isEmpty()) continue;

					String originalFilename = file.getOriginalFilename();
					String newFileName = UUID.randomUUID() + "_" + originalFilename;
					String filePath = uploadDir + File.separator + newFileName;

					Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

					String imageUrl = "http://localhost:8080/uploads/" + newFileName;

					TuserPatiImgInf image = TuserPatiImgInf.builder()
															.pati(patient)
															.imgUrl(imageUrl)
															.imgNm(originalFilename)
															.delYn(YN.N)
															.regrNo("KIMJAEWOO")
															.regPgmUrl("/patients/save")
															.regDts(LocalDate.now())
															.modrNo("KIMJAEWOO")
															.modPgmUrl("/patients/save")
															.modDts(LocalDate.now())
															.build();

					imgRepository.save(image);
				}
			}

			model.addAttribute("message", "환자 및 이미지 저장 완료!");
			
		} catch(RuntimeException e) {
			log.error("환자 및 이미지 저장시 오류 발생 :: {}",e);
			throw new RuntimeException("환자 및 이미지 저장시 오류 발생");
		}
		
		return "patient";
	}
	
	/**
	 * @content 환자 정보 화면
	 * @param patiNo
	 * @param model
	 * @return
	 */
	@GetMapping("/patient/{patiNo}")
	public String patientSearch(@PathVariable(name="patiNo") Integer patiNo, Model model) {
		TuserPatiBas patient = patiRepository.findById(patiNo)
			.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

		model.addAttribute("patient", patient);
		return "redirect:/patient/";
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
	public String updatePatient(@PathVariable(name="patiNo") Integer patiNo,
								@RequestParam(name="patiNm") String patiNm,
								@RequestParam(name="age") int age,
								@RequestParam(name="genCd") Gender genCd,
								@RequestParam(name="diseaseYn") YN diseaseYn,
								@RequestParam(name="files",required = false) MultipartFile[] files,
								Model model) throws IOException {

		
		try {
			TuserPatiBas patient = patiRepository.findById(patiNo)
					.orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

			patient.setPatiNm(patiNm);
			patient.setAge(age);
			patient.setGenCd(genCd);
			patient.setDiseaseYn(diseaseYn);
			patient.setModDts(LocalDate.now());
			patient.setModrNo("KIMJAEWOO");
			patient.setModPgmUrl("/patients/update");

			patiRepository.save(patient);
			
			// 새 이미지 저장 (기존 이미지 유지)
			if (files != null) {
				File dir = new File(uploadDir);
				if (!dir.exists()) dir.mkdirs();

				for (MultipartFile file : files) {
					if (file.isEmpty()) continue;

					String originalFilename = file.getOriginalFilename();
					String newFileName = UUID.randomUUID() + "_" + originalFilename;
					String filePath = uploadDir + File.separator + newFileName;

					Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

					String imageUrl = "http://localhost:8080/uploads/" + newFileName;

					TuserPatiImgInf image = TuserPatiImgInf.builder()
															.pati(patient)
															.imgUrl(imageUrl)
															.imgNm(originalFilename)
															.delYn(YN.N)
															.regrNo("KIMJAEWOO")
															.regPgmUrl("/patients/update")
															.regDts(LocalDate.now())
															.modrNo("KIMJAEWOO")
															.modPgmUrl("/patients/update")
															.modDts(LocalDate.now())
															.build();

					imgRepository.save(image);
				}
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
	public String deletePatient(@PathVariable(name="patiNo") Integer patiNo) {
	    TuserPatiBas patient = patiRepository.findById(patiNo)
	        .orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

	    patient.setDelYn(YN.Y);
	    patient.setModDts(LocalDate.now());
	    patiRepository.save(patient);

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
	    List<TuserPatiBas> patients;

	    if (patiNm != null && !patiNm.isEmpty()) {
	        patients = patiRepository.findByPatiNmContainingAndDelYn(patiNm, YN.N);
	    } else {
	        patients = patiRepository.findByDelYn(YN.N);
	    }

	    model.addAttribute("patients", patients);
	    model.addAttribute("keyword", patiNm);
	    return "patient-list";
	}
	
	@GetMapping("/patients/view/{patiNo}")
	public String viewPatient(@PathVariable(name="patiNo") Integer patiNo, Model model) {
	    TuserPatiBas patient = patiRepository.findById(patiNo)
	        .orElseThrow(() -> new RuntimeException("조회된 환자가 없음."));

	    List<TuserPatiImgInf> images = imgRepository.findByPati_PatiNo(patiNo)
	    											.stream().filter(img -> !"Y".equals(img.getDelYn())).toList();

	    model.addAttribute("patient", patient);
	    model.addAttribute("images", images);
	    return "patient-view";
	}
}
