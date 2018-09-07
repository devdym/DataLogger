CREATE DEFINER=`usersql`@`%` PROCEDURE `new_Str_config`()
BEGIN
DECLARE TotalSR INT;
DECLARE minS INT;
DECLARE minM INT;
DECLARE minW INT;
DECLARE counter INT DEFAULT 1;
DECLARE counterC INT DEFAULT 1;
SELECT MAX(Str_number) INTO TotalSR from Seal_AVG_Tension_minute; 
from_first_to_last: LOOP
	INSERT into Winch_Map(Winch_ODIM, Winch_Deck) VALUE (counter, 'X');
	INSERT into MODULE(Module_sn, Calibration, Slope) VALUE (counter, 1, 1.0);
	INSERT into SECTION (Section_sn, RVIM_Usage) VALUE (counter, 1);
	IF counter = TotalSR THEN 
		LEAVE from_first_to_last;
	END IF;
	SET counter = counter + 1;
END LOOP from_first_to_last;

SELECT MIN(ID) INTO minM FROM MODULE;
SELECT MIN(ID) INTO minS FROM SECTION;
SELECT MIN(ID) INTO minW FROM Winch_Map;

from_first_to_last_conf: LOOP
	INSERT into Streamer_conf(Date_, Str_number, MODULE_ID, SECTION_ID, Winch_Map_ID) VALUE (sysdate(), concat("Streamer ", counterC), minM, minS, minW);
	IF counterC = TotalSR THEN 
		LEAVE from_first_to_last_conf;
	END IF;
	SET counterC = counterC + 1;
	SET minS = minS + 1;
	SET minM = minM + 1;
	SET minW = minW + 1;
END LOOP from_first_to_last_conf;

END