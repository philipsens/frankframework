CREATE TABLE testiaf.DATABASECHANGELOGLOCK (ID INT NOT NULL, `LOCKED` BIT(1) NOT NULL, LOCKGRANTED datetime NULL, LOCKEDBY VARCHAR(255) NULL, CONSTRAINT PK_DATABASECHANGELOGLOCK PRIMARY KEY (ID));

DELETE FROM testiaf.DATABASECHANGELOGLOCK;

INSERT INTO testiaf.DATABASECHANGELOGLOCK (ID, `LOCKED`) VALUES (1, 0);

UPDATE testiaf.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'IGNORE', LOCKGRANTED = 'IGNORE' WHERE ID = 1 AND `LOCKED` = 0;

CREATE TABLE testiaf.DATABASECHANGELOG (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED datetime NOT NULL, ORDEREXECUTED INT NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35) NULL, `DESCRIPTION` VARCHAR(255) NULL, COMMENTS VARCHAR(255) NULL, TAG VARCHAR(255) NULL, LIQUIBASE VARCHAR(20) NULL, CONTEXTS VARCHAR(255) NULL, LABELS VARCHAR(255) NULL, DEPLOYMENT_ID VARCHAR(10) NULL);

INSERT INTO DUMMYTABLE(TCHAR, TMESSAGE) VALUES ('C', 'MESSAGE');

INSERT INTO testiaf.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('insert row', 'Ali Sihab Akcan', 'Migrator/DatabaseChangelog_inserts.xml', NOW(), 1, '8:3b5772bbfa5a3092a00bc020db46d688', 'sql', '', 'EXECUTED', NULL, NULL, 'VERSION', 'IGNORE');

CREATE TABLE testiaf.DUMMYTABLE (TKEY BIGINT AUTO_INCREMENT NOT NULL, TCHAR CHAR(1) NULL, TMESSAGE VARCHAR(100) NULL, CONSTRAINT PK_DUMMYTABLE PRIMARY KEY (TKEY));

INSERT INTO testiaf.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('one', 'Niels Meijer', 'Migrator/DatabaseChangelog_plus_changes.xml', NOW(), 2, '8:6ea8506c569cc02162836312f364217f', 'createTable tableName=DUMMYTABLE', 'Create DUMMYTABLE', 'EXECUTED', NULL, NULL, 'VERSION', 'IGNORE');

DROP TABLE testiaf.DUMMYTABLE;

INSERT INTO testiaf.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('two', 'Niels Meijer', 'Migrator/DatabaseChangelog_plus_changes.xml', NOW(), 3, '8:caa85ccb1dfe9e4b59561107126f4740', 'dropTable tableName=DUMMYTABLE', 'Drop DUMMYTABLE', 'EXECUTED', NULL, NULL, 'VERSION', 'IGNORE');

INSERT INTO testiaf.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID, TAG) VALUES ('three', 'Niels Meijer', 'Migrator/DatabaseChangelog_plus_changes.xml', NOW(), 4, '8:64ea2cfabf5f08738ffa2cfc5ee2f56a', 'tagDatabase', '', 'EXECUTED', NULL, NULL, 'VERSION', 'IGNORE', 'tralala');

UPDATE testiaf.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;