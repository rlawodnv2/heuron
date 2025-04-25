package hulearnSideProject.com.hulearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;

public interface TuserPatiImgInfRepository extends JpaRepository<TuserPatiImgInf, Integer> {

	List<TuserPatiImgInf> findByPati_PatiNo(Integer patiNo);
	
}