CREATE
EVENT `ODIM_AVG_tension_day_JOB`
ON SCHEDULE EVERY 1 DAY
	STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY + INTERVAL 20 MINUTE)
DO BEGIN
	DECLARE TotalSR INT;
	DECLARE TotalWT INT;
	
	DECLARE tension_avg INT;
	DECLARE counter INT DEFAULT 1;

	SET TotalSR = 20;
	SET TotalWT = 2;

	SELECT MAX(SW_number) INTO TotalSR from ODIM_AVG_Tension_minute; 

	IF TotalSR IS NOT null THEN
		from_first_to_last: LOOP
			SELECT AVG(AVG_Tension) INTO tension_avg from ODIM_AVG_Tension_minute 
				WHERE SW_number = counter and date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 day), "%Y-%m-%d"), '%');
			INSERT into ODIM_AVG_Tension_day(Date_, SW_number, AVG_Tension) VALUE (adddate(sysdate(), INTERVAL -1 day), counter, tension_avg);
			IF counter = TotalSR THEN 
				LEAVE from_first_to_last;
			END IF;
		SET counter = counter + 1;
		END LOOP from_first_to_last;
	END IF;
END;