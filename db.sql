use esmat;

DROP TRIGGER IF EXISTS mopf;
DELIMITER //
CREATE TRIGGER mopf
BEFORE INSERT ON esmat.manf_of_pro_fit FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;



DROP TRIGGER IF EXISTS purchases_trg;
DELIMITER //
CREATE TRIGGER purchases_trg
BEFORE INSERT ON esmat.Purchases FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;




DROP TRIGGER IF EXISTS sales_trg;
DELIMITER //
CREATE TRIGGER sales_trg
BEFORE INSERT ON esmat.sales FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS deceased_trg;
DELIMITER //
CREATE TRIGGER deceased_trg
BEFORE INSERT ON esmat.deceased FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;


DROP TRIGGER IF EXISTS employee_ancestor_trg;
DELIMITER //
CREATE TRIGGER employee_ancestor_trg
BEFORE INSERT ON esmat.employee_ancestor FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;




DROP TRIGGER IF EXISTS employee_time_tracker_trg;
DELIMITER //
CREATE TRIGGER employee_time_tracker_trg
BEFORE INSERT ON esmat.employee_time_tracker FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;


DROP TRIGGER IF EXISTS mopf;
DELIMITER //
CREATE TRIGGER mopf
BEFORE UPDATE ON esmat.manf_of_pro_fit FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;



DROP TRIGGER IF EXISTS purchases_trg_u;
DELIMITER //
CREATE TRIGGER purchases_trg_u
BEFORE UPDATE ON esmat.Purchases FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;




DROP TRIGGER IF EXISTS sales_trg_u;
DELIMITER //
CREATE TRIGGER sales_trg_u
BEFORE UPDATE ON esmat.sales FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS deceased_trg_u;
DELIMITER //
CREATE TRIGGER deceased_trg_u
BEFORE UPDATE ON esmat.deceased FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;


DROP TRIGGER IF EXISTS employee_ancestor_trg_u;
DELIMITER //
CREATE TRIGGER employee_ancestor_trg_u
BEFORE UPDATE ON esmat.employee_ancestor FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;




DROP TRIGGER IF EXISTS employee_time_tracker_trg_u;
DELIMITER //
CREATE TRIGGER employee_time_tracker_trg_u
BEFORE UPDATE ON esmat.employee_time_tracker FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;

 
DROP TRIGGER IF EXISTS other_expenses_trg;
DELIMITER //
CREATE TRIGGER other_expenses_trg
BEFORE INSERT ON esmat.other_expenses FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS other_expenses_trg_u;
DELIMITER //
CREATE TRIGGER other_expenses_trg_u
BEFORE UPDATE ON esmat.other_expenses FOR EACH ROW
BEGIN
    IF (NEW.day_date IS NULL) THEN -- change the isnull check for the default used
        SET NEW.day_date = now();
    END IF;
END//
DELIMITER ;