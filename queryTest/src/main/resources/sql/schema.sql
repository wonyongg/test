CREATE TABLE blood_pressure (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      systolic VARCHAR(100) NOT NULL,
                      diastolic VARCHAR(100) NOT NULL,
                      updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
