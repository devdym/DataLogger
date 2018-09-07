CREATE
EVENT `odim_AVG_tension_minute_JOB`
ON SCHEDULE EVERY 1 MINUTE
DO BEGIN
	DECLARE TotalSW INT;
	DECLARE TotalWT INT;
	DECLARE SR_avg_tension INT;
	DECLARE WT_avg_tension INT;
	DECLARE counter INT DEFAULT 1;
	DECLARE counterWT INT DEFAULT 1;

	SELECT MAX(Winch_number) INTO TotalSW from MIR.ODIM_Str_winch; 
	SELECT MAX(Winch_number) INTO TotalWT from MIR.ODIM_WideTow_winch; 

	IF TotalSW IS NOT null THEN 
		from_first_to_last: LOOP		
			SELECT AVG(TensionValue) INTO SR_avg_tension from ODIM_Str_winch_cycle WHERE Date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 minute), "%Y-%m-%d %H:%i"), '%') and Winch_number = counter;
			INSERT into ODIM_AVG_Tension_minute(Date_, SW_Number, AVG_Tension) VALUE (adddate(sysdate(), INTERVAL -1 minute), counter, SR_avg_tension);
			IF counter = TotalSW THEN 
				LEAVE from_first_to_last;
			END IF;
		SET counter = counter + 1;
		END LOOP from_first_to_last;
	END IF;

	IF TotalWT IS NOT null THEN
		from_first_to_lastWT: LOOP		
			SELECT AVG(TensionValue) INTO WT_avg_tension from ODIM_WideTow_winch_cycle WHERE Date_ like concat(date_format(adddate(sysdate(), INTERVAL -1 minute), "%Y-%m-%d %H:%i"), '%') and Winch_number = counterWT;
			INSERT into ODIM_AVG_Tension_minute(Date_, SW_Number, AVG_Tension) VALUE (adddate(sysdate(), INTERVAL -1 minute), (counterWT+100), WT_avg_tension);
			IF counterWT = TotalWT THEN 
				LEAVE from_first_to_lastWT;
			END IF;
		SET counterWT = counterWT + 1;
		END LOOP from_first_to_lastWT;
	END IF;
END;