package hulearnSideProject.com.hulearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;

public interface TuserPatiBasRepository extends JpaRepository<TuserPatiBas, Integer> {

	List<TuserPatiBas> findByPatiNmContainingAndDelYn(String patiNm, String string);

	List<TuserPatiBas> findByDelYn(String string);
	
}