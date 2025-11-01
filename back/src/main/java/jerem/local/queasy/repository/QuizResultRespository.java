package jerem.local.queasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jerem.local.queasy.model.Quiz;
import jerem.local.queasy.model.QuizResult;

/**
 * Repository interface for managing {@link QuizResult} persistence.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and
 * database interactions for
 * {@link Quiz} entities.
 * </p>
 * 
 * @see JpaRepository
 */
@Repository
public interface QuizResultRespository extends JpaRepository<QuizResult, Long> {

}
