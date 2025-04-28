package hulearnSideProject.com.hulearn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;

public interface TuserPatiBasRepository extends JpaRepository<TuserPatiBas, Integer> {

	List<TuserPatiBas> findByPatiNmContainingAndDelYn(String patiNm, Character delYn);

	List<TuserPatiBas> findByDelYn(Character delYn);

	Optional<TuserPatiBas> findByPatiNoAndDelYn(long patiNo, Character delYn);

	void deleteById(long patiNo);

	TuserPatiBas findById(long patiNo);
	
}