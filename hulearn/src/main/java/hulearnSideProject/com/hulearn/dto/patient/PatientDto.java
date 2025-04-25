package hulearnSideProject.com.hulearn.dto.patient;

import java.time.LocalDate;
import java.util.List;

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
	private Integer patiNo;
	private String patiNm;
	private Integer age;
	private String genCd;
	private String diseaseYn;
	private String delYn;
	private String hpNo;
	private LocalDate regDts;
	private List<ImageDto> images;
}
