화면에서 저장할 수 있는 간단한 게시판과
API 호출할 수 있는 간단한 코드를 작성해보았습니다. 

아래는 주요 패키지 및 파일 설명입니다.

패키지명|파일명|설명
---|---|---|
controller|ImageController.java|이미지 컨트롤러 @Controller|
controller|ImageRestController.java|이미지 API 컨트롤러 @RestController|
controller|PatientController.java|환자 관련 컨트롤러 @Controller|
controller|PatientRestController.java|환자 관련 API 컨트롤러 @RestController|
---|---|---|
dto|---|JPA 조회시 |
dto.image|ImageDto.java|이미지 JPA 데이터셋을 위한 DTO package|
dto.image|PatientDto.java|환자 JPA DTO List<ImageDto> 포함|
---|---|---|
entity|---|아래내용은 genCd값 하나만 사용하기 때문에 구현하지 않음 확장 가능|
entity.code|CommonEntity.java|regrNo,regPgmUrl,regDts,modrNo,modPgmUrl,modDts 처럼 entity마다 동일시 되는 값 지정 미구현|
entity.code|TcommCdDtl.java|공통코드상세 미구현|
entity.code|TcommCdDtlId.java|공통코드상세 pk값이 2개여서 따로 분리|
entity.code|TcommGrpCdBas.java|공통코드 그룹 entity|
entity.pati|TuserPatiBas.java|환자정보 entity|
entity.pati|TuserPatiImgInf.java|환자 이미지 entity|
---|---|---|
mapper|---|Entity 객체(데이터베이스 테이블과 매핑된 객체)를 DTO 객체(전달 용도로 단순화된 객체)로 변환하는 역할|
mapper|PatientMapper|환자정보 전달|
---|---|---|
repository|---|JpaRepository를 사용하여 JPA 에서 제공되는 간단한 CRUD 처리|
repository|TuserPatiBasRepository.java|환자 JPA CRUD Repository 환자명으로 삭제처리(DelYn업데이트) 환자번호로 삭제처리 DelYn업데이트|
repository|TuserPatiImgInfRepository.java|환자 이미지 JPA CURD Repository 환자번호로 이미지 검색|
---|---|---|
service|---|비즈니스 로직 및 Repository와 Controller 연결|
service|PatientService.java|환자 저장 조회 삭제 조건, 삭제 처리|
---|---|---|
util|---|handler와 config 포함
util.config|WebConfig.java|파일 경로 맵핑|
util.handler|GlobalExceptionHandler.java|Exception 처리 Handler|

