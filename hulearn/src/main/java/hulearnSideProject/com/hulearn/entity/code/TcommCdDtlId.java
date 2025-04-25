package hulearnSideProject.com.hulearn.entity.code;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 복합 키 class
 */

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TcommCdDtlId implements Serializable {

	@Column(name = "GRP_CD")
	private String grpCd;

	@Column(name = "CD")
	private String cd;
}
