package hulearnSideProject.com.hulearn.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PatientRestController {
	
	private final PatientService patientService;

	@PostMapping("/api/patients/save")
	public ResponseEntity<TuserPatiBas> save(@Valid @RequestBody TuserPatiBas patient) {
		return ResponseEntity.ok(patientService.savePatient(patient));
	}

	@GetMapping("/api/patients/findAll")
	public ResponseEntity<List<TuserPatiBas>> findAll() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	/**
	 * @content 조회 api
	 * @param id
	 * @return
	 */
	@GetMapping("/api/patient/{patiNo}")
	public ResponseEntity<TuserPatiBas> findById(@PathVariable(name="patiNo") long patiNo
													,@RequestParam(name="delYn") Character delYn) {
		TuserPatiBas pati = patientService.getPatientById(patiNo, delYn);
		
		if (delYn == null) {
			pati = patientService.getPatientById(patiNo);
		} else {
			pati = patientService.getPatientById(patiNo, delYn);
		}
		
		return pati != null ? ResponseEntity.ok(pati) : ResponseEntity.notFound().build();
	}

	/**
	 * @content delete api
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/patient/{patiNo}")
	public ResponseEntity<Void> delete(@PathVariable(name="patiNo") long patiNo) {
		
		patientService.deletePatient(patiNo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/api/patient/update/{patiNo}")
	public ResponseEntity<TuserPatiBas> update(@PathVariable("patiNo") long patiNo, @Valid @RequestBody TuserPatiBas updatedPatient){
		LocalDate nowDate = LocalDate.now();

		TuserPatiBas savedPatient = new TuserPatiBas();
		
		TuserPatiBas patient = patientService.getPatientById(patiNo);
		
		if (patient == null) {
			return ResponseEntity.notFound().build();
		}
		
		patient.setModDts(nowDate);
		patient.setModrNo("system");
		patient.setModPgmUrl("/api/patient/update/"+patiNo);

		if(updatedPatient.getPatiNm() != null) {
			patient.setPatiNm(updatedPatient.getPatiNm());
		}
		
		if(updatedPatient.getAge() != null) {
			patient.setAge(updatedPatient.getAge());
		}
		
		if(updatedPatient.getGenCd() != null) {
			patient.setGenCd(updatedPatient.getGenCd());
		}
		
		if(updatedPatient.getDiseaseYn() != null) {
			patient.setDiseaseYn(updatedPatient.getDiseaseYn());
		}
		
		if(updatedPatient.getDelYn() != null) {
			patient.setDelYn(updatedPatient.getDelYn());
		}

		try {
			savedPatient = patientService.savePatient(patient);
		} catch(Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}

		return ResponseEntity.ok(savedPatient);
	}
}
