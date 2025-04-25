package hulearnSideProject.com.hulearn.entity.code;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TCOMM_GRP_CD_BAS", schema = "hulearn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TcommGrpCdBas {

	@Id
	@Column(name = "GRP_CD")
	private String grpCd;

	@Column(name = "GRP_CD_NM")
	private String grpCdNm;

	@Column(name = "USE_YN")
	private String useYn;

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

	@OneToMany(mappedBy = "grpCd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TcommCdDtl> cdDtlList;
}