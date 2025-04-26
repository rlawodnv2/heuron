package hulearnSideProject.com.hulearn.entity.pati;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TUSER_PATI_IMG_INF", schema = "hulearn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TuserPatiImgInf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMG_NO")
	private long imgNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATI_NO", nullable = false)
	@JsonBackReference
	private TuserPatiBas pati;

	@Column(name = "IMG_URL", nullable = false)
	private String imgUrl;

	@Column(name = "IMG_NM", nullable = false)
	private String imgNm;

	@Column(name = "DEL_YN", nullable = false)
	private Character delYn;

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