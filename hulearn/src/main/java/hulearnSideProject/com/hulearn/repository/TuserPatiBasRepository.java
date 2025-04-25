package hulearnSideProject.com.hulearn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas.YN;

public interface TuserPatiBasRepository extends JpaRepository<TuserPatiBas, Integer> {

	List<TuserPatiBas> findByPatiNmContainingAndDelYn(String patiNm, YN delYn);

	List<TuserPatiBas> findByDelYn(YN delYn);

	Optional<TuserPatiBas> findByPatiNoAndDelYn(Integer patiNo, YN delYn);
	
}