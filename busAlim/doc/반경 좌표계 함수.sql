-- 시스템 계정에 접속해서 알리미 계정에 프로시저/함수 만드는 권한을 준다.
GRANT CREATE PROCEDURE TO alimi;

-- 시스템 계정 접속 해제하고 알리미 계정에 접속한다.
-- radians 함수를 만든다.
CREATE OR REPLACE FUNCTION RADIANS(nDegrees IN NUMBER) 
RETURN NUMBER DETERMINISTIC 
IS
BEGIN
  /*
  -- radians = degrees / (180 / pi)
  -- RETURN nDegrees / (180 / ACOS(-1)); but 180/pi is a constant, so...
  */
  RETURN nDegrees / 57.29577951308232087679815481410517033235;
END RADIANS;
/

-- 인크레파스 주변 1KM 반경 내 정류소를 조회하는 질의명령(테스트용)
SELECT 
    station_nm, station_id, x, y
FROM (  

            SELECT 
                ( 6371 * acos( cos( radians( 37.48217336674078) ) * cos( radians(y) ) * cos( radians(x) - radians(126.9013480410284) ) + sin( radians(37.48217336674078) ) * sin( radians(y) ) ) ) AS distance,
                station_nm, station_id, mobile_no, x, y
            FROM 
                station
            WHERE
                district_cd = 2
            UNION
            SELECT 
                ( 6371 * acos( cos( radians( 37.48217336674078) ) * cos( radians(y) ) * cos( radians(x) - radians(126.9013480410284) ) + sin( radians(37.48217336674078) ) * sin( radians(y) ) ) ) AS distance,
                station_nm, station_id, mobile_no, x, y
            FROM 
                seoulstation
            WHERE
                district_cd = 1
    
    ) DATA
WHERE 
    DATA.distance < 1;
