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

처음으로 설명드릴 뷰 페이지 URL
설명({PathVariable}    @{requestParamter})

@Controller
ImageController
/images/upload/{patiNo} -> 이미지 업로드 화면
/image/delete/{imgNo}/{patiNo} -> 이미지 삭제

@RestController
ImageRestController
/api/images/{patiNo} | @{delYn} type enum YN -> 이미지 삭제 delYn 값이 null 일 경우 전체 조회 Y 및 N일 경우 조회처리
/api/images/upload/{patiNo} | @{file} type MultipartFile -> 이미지 업로드

@Controller
PatientController
/patient -> 환자 등록 html
/patients/save | @{patiNm} type String 
                 @{age} type int
                 @{genCd} type enum Gender
                 @{diseaseYn} type enum YN
                 @{files} type MultipartFile -> 환자 정보 및 환자 이미지 저장
/patient/{patiNo} | @{patiNo} type int -> 환자 정보 화면(조회)
/patients/update/{patiNo} | @{patiNm} type String 
                            @{age} type int
                            @{genCd} type enum Gender
                            @{diseaseYn} type enum YN
                            @{files} type MultipartFile -> 환자 이미지 및 정보 수정
/patients/delete/{patiNo} -> 환자 정보 삭제
/patients/list  | @{patiNm} type String -> 환자 검색
/patients/view/{patiNo} -> 환자 상세

@RestController
/api/patients/save @{TuserPatiBas} Type Entity -> 저장
/api/patients/findAll -> 전체검색
/api/patient/{patiNo} | @{delYn} -> 회원 조회 delYn 이 null일경우 전체조회
/api/patient/{patiNo} -> 회원삭제
/api/patient/update/{patiNo} -> 회원정보 수정


문제 사항:
enum을 적용할 시 index 오류가 발생하고 있음.

