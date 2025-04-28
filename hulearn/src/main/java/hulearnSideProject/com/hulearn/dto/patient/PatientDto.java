package hulearnSideProject.com.hulearn.dto.patient;

import java.time.LocalDate;
import java.util.List;

import hulearnSideProject.com.hulearn.dto.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {
	private long patiNo;
	private String patiNm;
	private Integer age;
	private String genCd;
	private Character diseaseYn;
	private Character delYn;
	private String hpNo;
	private LocalDate regDts;
	private LocalDate modDts;
	private List<ImageDto> images;
}
