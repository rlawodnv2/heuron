package hulearnSideProject.com.hulearn.entity.pati;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TUSER_PATI_BAS", schema = "hulearn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TuserPatiBas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATI_NO")
	private Integer patiNo;

	@Column(name = "PATI_NM", nullable = false)
	@NotNull(message = "환자 이름은 필수 값입니다.")
	@Size(min = 1, message = "환자 이름은 비어있을 수 없습니다.")
	private String patiNm;

	@Column(name = "AGE", nullable = false)
	@NotNull(message = "환자 나이는 필수 값입니다.")
	@Size(max = 3, message = "환자 나이를 확인해 주세요.")
	@Min(value = 0, message = "환자 나이는 0세 이상이어야 합니다.")
	@Max(value = 999, message = "환자 나이는 999세 이하이어야 합니다.")
	private Integer age;

	@Enumerated(EnumType.STRING)
	@Column(name = "GEN_CD", nullable = false)
	@NotNull(message = "성별을 입력해주세요. M/F")
	private Gender genCd;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISEASE_YN", nullable = false)
	@NotNull(message = "질병 여부를 입력해주세요. Y/N")
	private YN diseaseYn;

	@Column(name = "DEL_YN", nullable = false)
	private YN delYn;

	@Column(name = "HP_NO")
	private String hpNo;

	@Column(name = "REGR_NO", nullable = false)
	private String regrNo;

	@Column(name = "REG_PGM_URL", nullable = false)
	private String regPgmUrl;

	@Column(name = "REG_DTS", nullable = false, columnDefinition = "DATE")
	private LocalDate regDts;

	@Column(name = "MODR_NO", nullable = false)
	private String modrNo;

	@Column(name = "MOD_PGM_URL", nullable = false)
	private String modPgmUrl;

	@Column(name = "MOD_DTS", nullable = false, columnDefinition = "DATE")
	private LocalDate modDts;

	@OneToMany(mappedBy = "pati", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<TuserPatiImgInf> imageList;
	
	public enum Gender {
		M("남성"),
		F("여성");

		private final String label;

		Gender(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
	
	public enum YN {
		Y,N;
	}
}