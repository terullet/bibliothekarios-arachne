package net.terullet.bibliothekarios.arachne.service;

import net.terullet.bibliothekarios.arachne.repository.mapper.WorkMapper;
import net.terullet.bibliothekarios.arachne.repository.entity.WorkTitleUpdateRequestEntity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class WorkService {
	private static final Logger logger = LogManager.getLogger(WorkService.class);

	private final SqlSessionFactory sqlSessionFactory;

	public WorkService(SqlSessionFactory sqlSessionFactory) {
		Objects.requireNonNull(sqlSessionFactory, "sqlSessionFactory MUST NOT be null.");
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 全作品の概要を取得
	 * TODO 非同期処理に置き換え
	 */
	public List<WorkOverview> getAllWorkOverviews() {
		logger.debug("Getting all work overviews");
		return executeReadOnly(mapper ->
				mapper.selectAllWorks()
						.stream()
						.map(WorkOverview::fromEntity)
						.toList()
		);
	}

	/**
	 * 指定した作品世界の作品概要を取得
	 */
	public List<WorkOverview> getWorkOverviewsByUniverse(long universeId) {
		logger.debug("Getting work overviews for universe: {}", universeId);
		return executeReadOnly(mapper ->
				mapper.selectWorksByUniverseId(universeId)
						.stream()
						.map(WorkOverview::fromEntity)
						.toList()
		);
	}

	/**
	 * 作品IDで詳細を取得
	 */
	public Work getWorkById(long workId) {
		logger.debug("Getting work by ID: {}", workId);
		return executeReadOnly(mapper -> {
			var factory = mapper.selectWorkById(workId);
			if (factory == null) {
				throw new WorkNotFoundException("Work not found: " + workId);
			}
			return Work.fromEntity(factory.build());
		});
	}

	/**
	 * 作品タイトルを更新
	 */
	public void updateWorkTitle(WorkTitleUpdateRequest request) {
		logger.debug("Updating work title: workId={}, newTitle={}",
				request.id(), request.newTitle());

		executeWithTransaction(mapper -> {
			int updated = mapper.updateWorkTitle(request.toEntity());
			if (updated == 0) {
				throw new WorkUpdateException("Failed to update work title: " + request.id());
			}
			return null;
		});
	}

	/**
	 * 作品を削除
	 */
	public void deleteWork(long workId) {
		logger.debug("Deleting work: workId={}", workId);

		this.executeWithTransaction(mapper -> {
			int deleted = mapper.deleteWork(workId);
			if (deleted != 1) {
				throw new WorkDeleteException("Failed to delete work: " + workId);
			}
			return null;
		});
	}

	/**
	 * 複数操作を一つのトランザクションで実行
	 */
	public void performBulkOperation(List<WorkTitleUpdateRequestEntity> updates) {
		logger.debug("Performing bulk update for {} works", updates.size());

		executeWithTransaction(mapper -> {
			for (var update : updates) {
				int updated = mapper.updateWorkTitle(update);
				if (updated == 0) {
					throw new WorkUpdateException("Failed to update work: " + update.getId());
				}
			}
			return null;
		});
	}

	// === ヘルパーメソッド ===

	/**
	 * 読み取り専用操作の実行
	 */
	private <T> T executeReadOnly(Function<WorkMapper, T> operation) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) { // autoCommit=true for read-only
			WorkMapper mapper = session.getMapper(WorkMapper.class);
			return operation.apply(mapper);
		} catch (Exception e) {
			logger.error("Database operation failed", e);
			throw new ServiceException("Database operation failed", e);
		}
	}

	/**
	 * トランザクション付き操作の実行
	 */
	private <T> T executeWithTransaction(Function<WorkMapper, T> operation) {
		try (SqlSession session = sqlSessionFactory.openSession(false)) { // autoCommit=false
			WorkMapper mapper = session.getMapper(WorkMapper.class);
			try {
				T result = operation.apply(mapper);
				session.commit();
				return result;
			} catch (Exception e) {
				session.rollback();
				throw e;
			}
		} catch (Exception e) {
			logger.error("Transaction failed", e);
			throw new ServiceException("Transaction failed", e);
		}
	}

	// === 例外クラス ===

	public static class ServiceException extends RuntimeException {
		public ServiceException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class WorkNotFoundException extends ServiceException {
		public WorkNotFoundException(String message) {
			super(message, null);
		}
	}

	public static class WorkUpdateException extends ServiceException {
		public WorkUpdateException(String message) {
			super(message, null);
		}
	}

	public static class WorkDeleteException extends ServiceException {
		public WorkDeleteException(String message) {
			super(message, null);
		}
	}
}
