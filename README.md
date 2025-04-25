화면에서 저장할 수 있는 간단한 게시판과
API 호출할 수 있는 간단한 코드를 작성해보았습니다. 

아래는 해당 소스코드의 설명입니다.

패키지명|파일명|설명
---|---|---|
controller|ImageController.java|이미지 컨트롤러 @Controller|
controller|ImageRestController.java|이미지 API 컨트롤러 @RestController|
controller|PatientController.java|환자 관련 컨트롤러 @Controller|
controller|PatientRestController.java|환자 관련 API 컨트롤러 @RestController|
---|---|---|
dto|dto|JPA 조회시 |
dto.image|ImageDto.java|이미지 JPA 데이터셋을 위한 DTO package|
dto.image|PatientDto.java|환자 JPA DTO List<ImageDto> 포함|
---|---|---|
entity|entity|아래내용은 genCd값 하나만 사용하기 때문에 구현하지 않음 확장 가능|
entity.code|CommonEntity.java|regrNo,regPgmUrl,regDts,modrNo,modPgmUrl,modDts 처럼 entity마다 동일시 되는 값 지정 미구현|
entity.code|TcommCdDtl.java|공통코드상세 미구현|
entity.code|TcommCdDtlId.java|공통코드상세 pk값이 2개여서 따로 분리|
entity.code|TcommGrpCdBas.java|공통코드 그룹 entity|
entity.pati|TuserPatiBas.java|환자정보 entity|
entity.pati|TuserPatiImgInf.java|환자 이미지 entity|
---|---|---|
