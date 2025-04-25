package hulearnSideProject.com.hulearn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;
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
	public TuserPatiBas getPatientById(Integer patiNo, YN delYn) {
		return patiRepository.findByPatiNoAndDelYn(patiNo, delYn).orElse(null);
	}
	// 단일 조회 삭제 조건 없음.
	public TuserPatiBas getPatientById(Integer id) {
		return patiRepository.findById(id).orElse(null);
	}

	// 삭제
	public void deletePatient(Integer patiNo) {
		patiRepository.deleteById(patiNo);
	}

	
}