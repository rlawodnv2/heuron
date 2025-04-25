package hulearnSideProject.com.hulearn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.repository.TuserPatiBasRepository;
import hulearnSideProject.com.hulearn.repository.TuserPatiImgInfRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final TuserPatiBasRepository patiRepository;
	private final TuserPatiImgInfRepository imgRepository;

	// 저장
	public TuserPatiBas savePatient(TuserPatiBas pati) {
		return patiRepository.save(pati);
	}

	// 전체 조회
	public List<TuserPatiBas> getAllPatients() {
		return patiRepository.findAll();
	}

	// 단일 조회
	public TuserPatiBas getPatientById(Integer patiNo) {
		return patiRepository.findById(patiNo).orElse(null);
	}

	// 삭제
	public void deletePatient(Integer patiNo) {
		patiRepository.deleteById(patiNo);
	}
}