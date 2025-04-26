package hulearnSideProject.com.hulearn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final TuserPatiBasRepository patiRepository;

	// 저장
	public TuserPatiBas savePatient(TuserPatiBas pati) {
		return patiRepository.save(pati);
	}

	// 전체 조회
	public List<TuserPatiBas> getAllPatients() {
		return patiRepository.findAll();
	}

	// 단일 조회 삭제 조건 포함.
	public TuserPatiBas getPatientById(long patiNo, Character delYn) {
		
		TuserPatiBas patient = patiRepository.findByPatiNoAndDelYn(patiNo, delYn).orElse(null);
		
		if (patient == null) {
		    return ResponseEntity.notFound().build();
		}
		
		return patient;
	}
	// 단일 조회 삭제 조건 없음.
	public TuserPatiBas getPatientById(long id) {
		
		TuserPatiBas patient = patiRepository.findById(id).orElse(null);
		
		if (patient == null) {
		    return ResponseEntity.notFound().build();
		}
		
		return patient;
	}

	// 삭제
	public void deletePatient(long patiNo) {
		patiRepository.deleteById(patiNo);
	}

	public void save(TuserPatiBas patient) {
		patiRepository.save(patient);
	}

	public List<TuserPatiBas> findByDelYn(String patiNm) {
		List<TuserPatiBas> patients = new ArrayList<>();
		
		if (patiNm != null && !patiNm.isEmpty()) {
			patients = patiRepository.findByPatiNmContainingAndDelYn(patiNm, 'N');
		} else {
			patients = patiRepository.findByDelYn('N');
		}
		return patients;
	}

	public TuserPatiBas findById(long patiNo) {
		TuserPatiBas patient = patiRepository.findById(patiNo)
												.orElseThrow(() -> new PathNotFoundException("조회된 환자가 없음."));
		
		return patient;
	}

	
}