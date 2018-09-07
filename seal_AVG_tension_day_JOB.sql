CREATE
EVENT `seal_AVG_tension_day_JOB`
ON SCHEDULE EVERY 1 DAY
	STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY + INTERVAL 30 MINUTE)
DO BEGIN
	DECLARE TotalStr INT;
	DECLARE tension_avg INT;
	DECLARE counter INT DEFAULT 1;

	SELECT MAX(Str_number) INTO TotalStr from Seal_AVG_Tension_minute; 
	
	IF TotalStr IS NOT null THEN
		from_first_to_last: LOOP
			SELECT AVG(AVG_Tension) INTO tension_avg from Seal_AVG_Tension_minute 
				WHERE Str_number = counter and Date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 day), "%Y-%m-%d"), '%');
			INSERT into Seal_AVG_Tension_day(Date_, Str_number, AVG_Tension) VALUE (adddate(sysdate(), INTERVAL -1 day), counter, tension_avg);
			IF counter = TotalStr THEN 
				LEAVE from_first_to_last;
			END IF;
		SET counter = counter + 1;
		END LOOP from_first_to_last;
	END IF;
END;