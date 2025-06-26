package net.terullet.bibliothekarios.arachne.service.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.repository.entity.WorkCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.mapper.WorkMapper;
import net.terullet.bibliothekarios.arachne.repository.mapper.web.com.syosetu.NarouWorkMapper;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkCreationRequestEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkEntity;
import net.terullet.bibliothekarios.arachne.repository.entity.web.com.syosetu.NarouWorkUpdateRequestEntity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class NarouWorkService {
	private static final Logger logger = LogManager.getLogger(NarouWorkService.class);

	private final SqlSessionFactory sqlSessionFactory;

	public NarouWorkService(SqlSessionFactory sqlSessionFactory) {
		Objects.requireNonNull(sqlSessionFactory, "sqlSessionFactory MUST NOT be null.");
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 全なろう作品を取得
	 */
	public List<NarouWorkEntity> getAllNarouWorks() {
		logger.debug("Getting all Narou works");
		return executeReadOnly(NarouWorkMapper::selectAllNarouWorks);
	}

	/**
	 * なろう作品を新規作成
	 */
	public void createNarouWork(NarouWorkCreationRequest request) {
		logger.debug("Creating Narou work: ncode={}, narouId={}", 
				request.ncode(), request.narouId());

		this.executeWithTransaction(session -> {
			int changed;
			WorkMapper wm = session.getMapper(WorkMapper.class);
			WorkCreationRequestEntity wcr = request.toWorkCreationRequestEntity();
			changed = wm.insertWork(wcr);
			if (changed == 0) {
				throw new NarouWorkCreationException("Failed to create Narou Work: " + request.ncode());
			}
			NarouWorkMapper nwm = session.getMapper(NarouWorkMapper.class);
			changed = nwm.insertNarouWork(request.toNarouWorkCreationRequestEntity(wcr.getId()));
			if (changed == 0) {
				throw new NarouWorkCreationException("Failed to create Narou Work: " + request.ncode());
			}
		});
	}

	/**
	 * なろう作品を更新
	 */
	public void updateNarouWork(NarouWorkUpdateRequestEntity request) {
		logger.debug("Updating Narou work: workId={}, genre={}", 
				request.getId(), request.getGenre());

		executeWithTransaction(session -> {
			NarouWorkMapper mapper = session.getMapper(NarouWorkMapper.class);
			int updated = mapper.updateNarouWork(request);
			if (updated == 0) {
				throw new NarouWorkUpdateException("Failed to update Narou work: " + request.getId());
			}
		});
	}

	/**
	 * 複数なろう作品の一括更新
	 */
	public void performBulkUpdate(List<NarouWorkUpdateRequestEntity> updates) {
		logger.debug("Performing bulk update for {} Narou works", updates.size());

		executeWithTransaction(session -> {
			NarouWorkMapper mapper = session.getMapper(NarouWorkMapper.class);
			for (var update : updates) {
				int updated = mapper.updateNarouWork(update);
				if (updated == 0) {
					throw new NarouWorkUpdateException("Failed to update Narou work: " + update.getId());
				}
			}
		});
	}

	/**
	 * 複数なろう作品の一括作成
	 */
	public void performBulkCreation(List<NarouWorkCreationRequestEntity> creations) {
		logger.debug("Performing bulk creation for {} Narou works", creations.size());

		executeWithTransaction(session -> {
			NarouWorkMapper mapper = session.getMapper(NarouWorkMapper.class);
			for (var creation : creations) {
				int inserted = mapper.insertNarouWork(creation);
				if (inserted == 0) {
					throw new NarouWorkCreationException("Failed to create Narou work: " + creation.getNcode());
				}
			}
		});
	}

	// === ヘルパーメソッド ===

	/**
	 * 読み取り専用操作の実行
	 */
	private <T> T executeReadOnly(Function<NarouWorkMapper, T> operation) {
		try (SqlSession session = sqlSessionFactory.openSession(true)) { // autoCommit=true for read-only
			NarouWorkMapper mapper = session.getMapper(NarouWorkMapper.class);
			return operation.apply(mapper);
		} catch (Exception e) {
			logger.error("Database operation failed", e);
			throw new ServiceException("Database operation failed", e);
		}
	}

	/**
	 * トランザクション付き操作の実行
	 */
	private void executeWithTransaction(Consumer<SqlSession> operation) {
		try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
			try {
				operation.accept(session);
				session.commit();
			} catch (Exception e) {
				session.rollback();
				throw e;
			}
		} catch (Exception e) {
			logger.error("Transaction failed", e);
			throw e;
		}
	}

	// === 例外クラス ===

	public static class ServiceException extends RuntimeException {
		public ServiceException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class NarouWorkCreationException extends ServiceException {
		public NarouWorkCreationException(String message) {
			super(message, null);
		}
	}

	public static class NarouWorkUpdateException extends ServiceException {
		public NarouWorkUpdateException(String message) {
			super(message, null);
		}
	}
}
