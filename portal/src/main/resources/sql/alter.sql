-- Idempotent Index Creation

-- 1. idx_filter_placement
SET @dbname = DATABASE();
SET @tablename = "placement_filter";
SET @indexname = "idx_filter_placement";
SET @exist := (SELECT COUNT(*) FROM information_schema.statistics WHERE table_name = @tablename AND index_name = @indexname AND table_schema = @dbname);
SET @sqlstmt := IF(@exist > 0, 'SELECT ''Index idx_filter_placement already exists''', 'ALTER TABLE placement_filter ADD INDEX idx_filter_placement (placement_id)');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. idx_filter_domain
SET @indexname = "idx_filter_domain";
SET @exist := (SELECT COUNT(*) FROM information_schema.statistics WHERE table_name = @tablename AND index_name = @indexname AND table_schema = @dbname);
SET @sqlstmt := IF(@exist > 0, 'SELECT ''Index idx_filter_domain already exists''', 'ALTER TABLE placement_filter ADD INDEX idx_filter_domain (domain_id)');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3. idx_filter_specialisation
SET @indexname = "idx_filter_specialisation";
SET @exist := (SELECT COUNT(*) FROM information_schema.statistics WHERE table_name = @tablename AND index_name = @indexname AND table_schema = @dbname);
SET @sqlstmt := IF(@exist > 0, 'SELECT ''Index idx_filter_specialisation already exists''', 'ALTER TABLE placement_filter ADD INDEX idx_filter_specialisation (specialisation_id)');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 4. uq_application
SET @tablename = "placement_student";
SET @indexname = "uq_application";
SET @exist := (SELECT COUNT(*) FROM information_schema.statistics WHERE table_name = @tablename AND index_name = @indexname AND table_schema = @dbname);
SET @sqlstmt := IF(@exist > 0, 'SELECT ''Unique Key uq_application already exists''', 'ALTER TABLE placement_student ADD UNIQUE KEY uq_application (placement_id, student_id)');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

