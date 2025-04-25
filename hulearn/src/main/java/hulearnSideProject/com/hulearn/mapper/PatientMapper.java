package hulearnSideProject.com.hulearn.mapper;

import hulearnSideProject.com.hulearn.dto.patient.ImageDto;
import hulearnSideProject.com.hulearn.dto.patient.PatientDto;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiBas;
import hulearnSideProject.com.hulearn.entity.pati.TuserPatiImgInf;

public class PatientMapper {

	public static PatientDto toDto(TuserPatiBas entity) {
		return PatientDto.builder()
			.patiNo(entity.getPatiNo())
			.patiNm(entity.getPatiNm())
			.age(entity.getAge())
			.genCd(entity.getGenCd())
			.diseaseYn(entity.getDiseaseYn())
			.delYn(entity.getDelYn())
			.hpNo(entity.getHpNo())
			.regDts(entity.getRegDts())
			.images(entity.getImageList() != null
				? entity.getImageList().stream().map(PatientMapper::toDto).toList()
				: null)
			.build();
	}

	public static ImageDto toDto(TuserPatiImgInf img) {
		return ImageDto.builder()
			.imgNo(img.getImgNo())
			.imgUrl(img.getImgUrl())
			.imgNm(img.getImgNm())
			.regDts(img.getRegDts())
			.build();
	}
}
