package jerem.local.queasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jerem.local.queasy.model.Quiz;

/**
 * Repository interface for managing {@link Quiz} persistence.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and
 * database interactions for
 * {@link Quiz} entities.
 * </p>
 * 
 * @see JpaRepository
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByTitle(String title);
}
