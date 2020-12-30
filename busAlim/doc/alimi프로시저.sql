CREATE OR REPLACE PROCEDURE friendProc(
    id1 IN friend.id%TYPE,
    frid1 IN friend.frid%TYPE,
    cnt OUT NUMBER
)
IS
    ccnt NUMBER;
BEGIN
    DBMS_OUTPUT.ENABLE;
    
    select COUNT(*)
    into    ccnt
    from    friend
    where   id = id1 AND frid = frid1;
    
    if ccnt = 0 then
        INSERT INTO
            friend(frno, id, frid)
        VALUES(
            (SELECT NVL(MAX(frno), 10000) + 1 FROM friend),
            id1,
            frid1
        );
    else
        DELETE FROM friend
        WHERE   id = id1 AND frid = frid1;
        COMMIT;
    END IF;
    
    cnt := ccnt;
    commit;
END;
/
exec friendProc('jjang', 'jjang2', :cnt);