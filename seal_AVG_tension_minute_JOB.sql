CREATE
EVENT `seal_AVG_tension_minute_JOB`
ON SCHEDULE EVERY 1 MINUTE
DO BEGIN
	DECLARE TotalStr INT;
	DECLARE tension_avg INT;
	DECLARE counter INT DEFAULT 1;

	SELECT MAX(Streamer) INTO TotalStr from MIR.Seal_MetricLog; 

	IF TotalStr IS NOT null THEN
		from_first_to_last: LOOP
			SELECT AVG(Tension) INTO tension_avg from MIR.Seal_MetricLog 
				WHERE Streamer = counter and Date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 minute), "%Y-%m-%d %H:%i"), '%');
			INSERT into Seal_AVG_Tension_minute(Date_, Str_number, AVG_Tension) VALUE (adddate(sysdate(), INTERVAL -1 minute), counter, tension_avg);
			IF counter = TotalStr THEN 
				LEAVE from_first_to_last;
			END IF;
			SET counter = counter + 1;
		END LOOP from_first_to_last;
	END IF;
END;