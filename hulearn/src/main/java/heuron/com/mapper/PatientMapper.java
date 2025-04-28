package heuron.com.mapper;

import heuron.com.dto.image.ImageDto;
import heuron.com.dto.patient.PatientDto;
import heuron.com.entity.pati.TuserPatiBas;
import heuron.com.entity.pati.TuserPatiImgInf;

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
						.delYn(img.getDelYn())
						.imgUrl(img.getImgUrl())
						.imgNm(img.getImgNm())
						.regDts(img.getRegDts())
						.build();
	}
}
