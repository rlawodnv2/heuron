package heuron.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import heuron.com.entity.pati.TuserPatiImgInf;

public interface TuserPatiImgInfRepository extends JpaRepository<TuserPatiImgInf, Integer> {

	List<TuserPatiImgInf> findByPati_PatiNo(long patiNo);
	
}