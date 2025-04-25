package hulearnSideProject.com.hulearn.entity.pati;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private String patiNm;

	@Column(name = "AGE", nullable = false)
	private Integer age;

	@Column(name = "GEN_CD", nullable = false)
	private String genCd;

	@Column(name = "DISEASE_YN", nullable = false)
	private String diseaseYn;

	@Column(name = "DEL_YN", nullable = false)
	private String delYn;

	@Column(name = "HP_NO")
	private String hpNo;

	@Column(name = "REGR_NO", nullable = false)
	private String regrNo;

	@Column(name = "REG_PGM_URL", nullable = false)
	private String regPgmUrl;

	@Column(name = "REG_DTS", nullable = false)
	private LocalDate regDts;

	@Column(name = "MODR_NO", nullable = false)
	private String modrNo;

	@Column(name = "MOD_PGM_URL", nullable = false)
	private String modPgmUrl;

	@Column(name = "MOD_DTS", nullable = false)
	private LocalDate modDts;

	@OneToMany(mappedBy = "pati", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<TuserPatiImgInf> imageList;
}