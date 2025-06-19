package net.terullet.bibliothekarios.arachne.javafx.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Flywayを使用したデータベースマイグレーション管理クラス
 */
public class DatabaseMigrator {
	private static final Logger logger = LogManager.getLogger(DatabaseMigrator.class);

	private final Flyway flyway;

	public DatabaseMigrator() {
		Properties dbProperties = loadDatabaseProperties();

		this.flyway = Flyway.configure()
				.dataSource(
						dbProperties.getProperty("db.url"),
						dbProperties.getProperty("db.username"),
						dbProperties.getProperty("db.password")
				)
				.locations("classpath:db/migration")
				.baselineOnMigrate(true)
				.baselineVersion("0")
				.validateOnMigrate(true)
				.cleanDisabled(false) // 本番環境では true に変更
				.load();
	}

	/**
	 * データベースプロパティを読み込み
	 */
	private Properties loadDatabaseProperties() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader()
				.getResourceAsStream("net/terullet/bibliothekarios/arachne/javafx/db.properties")) {
			if (input == null) {
				throw new RuntimeException("db.properties file not found");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load database properties", e);
		}
		return properties;
	}

	/**
	 * データベースマイグレーションを実行
	 */
	public void migrate() {
		try {
			logger.info("Starting database migration...");

			// マイグレーション情報の確認
			MigrationInfoService infoService = flyway.info();
			MigrationInfo[] migrations = infoService.all();

			logger.info("Found {} migrations", migrations.length);
			for (MigrationInfo migration : migrations) {
				logger.debug("Migration: {} - {} ({})",
						migration.getVersion(),
						migration.getDescription(),
						migration.getState());
			}

			// マイグレーション実行
			int migrationCount = flyway.migrate().migrationsExecuted;

			if (migrationCount > 0) {
				logger.info("Successfully executed {} migrations", migrationCount);
			} else {
				logger.info("Database is up to date");
			}

		} catch (Exception e) {
			logger.error("Database migration failed", e);
			throw new RuntimeException("Database migration failed", e);
		}
	}

	/**
	 * データベース情報の確認
	 */
	public void info() {
		MigrationInfoService infoService = flyway.info();
		MigrationInfo[] migrations = infoService.all();

		logger.info("=== Database Migration Status ===");
		for (MigrationInfo migration : migrations) {
			logger.info("{} - {} ({})",
					migration.getVersion(),
					migration.getDescription(),
					migration.getState());
		}
	}

	/**
	 * 開発時用：データベースをクリーン（本番環境では使用禁止）
	 */
	public void clean() {
		logger.warn("Cleaning database - This should only be used in development!");
		flyway.clean();
	}

	/**
	 * データベース接続の検証
	 */
	public boolean validateConnection() {
		try {
			flyway.validate();
			return true;
		} catch (Exception e) {
			logger.error("Database validation failed", e);
			return false;
		}
	}
}
