package hulearnSideProject.com.hulearn.entity.code;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TCOMM_CD_DTL", schema = "hulearn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TcommCdDtl {

	@EmbeddedId
	private TcommCdDtlId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRP_CD", insertable = false, updatable = false)
	private TcommGrpCdBas grpCd;

	@Column(name = "CD_NM")
	private String cdNm;

	@Column(name = "DISP_PRIR")
	private Integer dispPrir;

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
}