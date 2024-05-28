package org.frankframework.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;

import org.apache.commons.lang3.RandomUtils;
import org.frankframework.testutil.JdbcTestUtil;
import org.frankframework.testutil.junit.DatabaseTestEnvironment;
import org.frankframework.testutil.junit.TxManagerTest;
import org.frankframework.testutil.junit.WithLiquibase;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class TransactionManagerTest {
	private static final String TEST_TABLE = "tralala";

	protected void checkNumberOfLines(DatabaseTestEnvironment env, int expected, int key) throws Exception {
		checkNumberOfLines(env, expected, "select count(*) from " + TEST_TABLE + " where TKEY = ?", key);
	}

	private void checkNumberOfLines(DatabaseTestEnvironment env, int expected, String query, Object... params) throws Exception {
		String preparedQuery = env.getDbmsSupport().prepareQueryTextForNonLockingRead(query);
		try(Connection connection = env.getConnection()) {
			int count = JdbcTestUtil.executeIntQuery(connection, preparedQuery, params);
			assertEquals(expected, count, "number of lines in table");
		}
	}

	@TxManagerTest
	@WithLiquibase(file = "Migrator/ChangelogBlobTests.xml", tableName = TEST_TABLE)
	public void testCommit(DatabaseTestEnvironment env) throws Exception {
		final int key = RandomUtils.nextInt();
		try(Connection connection = env.getConnection()) {
			JdbcTestUtil.executeStatement(connection, "DELETE FROM " + TEST_TABLE + " where TKEY=?", key);
		}

		TransactionStatus txStatus = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);

		try (Connection txManagedConnection = env.getConnection()) {
			checkNumberOfLines(env, 0, key);
			JdbcTestUtil.executeStatement(txManagedConnection, "INSERT INTO " + TEST_TABLE + " (tkey) VALUES (?)", key);
		}

		env.getTxManager().commit(txStatus);

		checkNumberOfLines(env, 1, key);
	}

	@TxManagerTest
	@WithLiquibase(file = "Migrator/ChangelogBlobTests.xml", tableName = TEST_TABLE)
	public void testRollback(DatabaseTestEnvironment env) throws Exception {
		final int key = RandomUtils.nextInt();
		TransactionStatus txPrepareStatus = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
		try(Connection connection = env.getConnection()) {
			JdbcTestUtil.executeStatement(connection, "DELETE FROM " + TEST_TABLE + " where TKEY=?", key);
		}
		checkNumberOfLines(env, 0, key);
		env.getTxManager().commit(txPrepareStatus);

		TransactionStatus txStatus = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);

		try (Connection txManagedConnection = env.getConnection()) {
			checkNumberOfLines(env, 0, key);
			JdbcTestUtil.executeStatement(txManagedConnection, "INSERT INTO " + TEST_TABLE + " (tkey) VALUES (?)", key);
			checkNumberOfLines(env, 1, key);
		}
		checkNumberOfLines(env, 1, key);

		env.getTxManager().rollback(txStatus);

		checkNumberOfLines(env, 0, key);
	}

	@TxManagerTest
	@WithLiquibase(file = "Migrator/ChangelogBlobTests.xml", tableName = TEST_TABLE)
	public void testRequiresNew(DatabaseTestEnvironment env) throws Exception {
		final int key1 = RandomUtils.nextInt();

		try(Connection connection = env.getConnection()) {
			JdbcTestUtil.executeStatement(connection, "DELETE FROM " + TEST_TABLE + " where TKEY=?", key1);
		}

		try (Connection txManagedConnection = env.getConnection()) {
			checkNumberOfLines(env, 0, key1);
			JdbcTestUtil.executeStatement(txManagedConnection, "INSERT INTO " + TEST_TABLE + " (tkey) VALUES (?)", key1);
		}

		TransactionStatus txStatus1 = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);

		try (Connection txManagedConnection = env.getConnection()) {
			checkNumberOfLines(env, 1, key1);
			JdbcTestUtil.executeStatement(txManagedConnection, "UPDATE " + TEST_TABLE + " SET TVARCHAR='tralala' WHERE tkey=?", key1);
		}

		try (Connection txManagedConnection = env.getConnection()) {
			JdbcTestUtil.executeStatement(txManagedConnection, "SELECT TVARCHAR FROM " + TEST_TABLE + " WHERE tkey=?", key1);
		}
		checkNumberOfLines(env, 1, key1);

		final int key2 = RandomUtils.nextInt();
		TransactionStatus txStatus2 = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		try (Connection txManagedConnection = env.getConnection()) {
			JdbcTestUtil.executeStatement(txManagedConnection, "INSERT INTO " + TEST_TABLE + " (tkey) VALUES (?)", key2);
		}

		env.getTxManager().commit(txStatus2);
		env.getTxManager().commit(txStatus1);

		checkNumberOfLines(env, 1, key1);
		checkNumberOfLines(env, 1, key2);
	}

	@TxManagerTest
	@WithLiquibase(file = "Migrator/ChangelogBlobTests.xml", tableName = TEST_TABLE)
	public void testRequiresNewAfterSelect(DatabaseTestEnvironment env) throws Exception {

		// This tests fails for Narayana, if no Modifiers are present for the database driver.
		// @see NarayanaDataSourceFactory.checkModifiers()

		final int key1 = RandomUtils.nextInt();
		TransactionStatus txStatusOuter = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
		try (Connection txManagedConnection = env.getConnection()) {
			JdbcTestUtil.executeStatement(txManagedConnection, "SELECT TVARCHAR FROM " + TEST_TABLE + " WHERE tkey=?", key1);
		}

		final int key2 = RandomUtils.nextInt();
		TransactionStatus txStatusInner = env.startTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		try (Connection txManagedConnection = env.getConnection()) {
			JdbcTestUtil.executeStatement(txManagedConnection, "INSERT INTO " + TEST_TABLE + " (tkey) VALUES (?)", key2);
		}

		env.getTxManager().commit(txStatusInner);
		env.getTxManager().commit(txStatusOuter);
	}

}
