package jerem.local.queasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jerem.local.queasy.model.Question;

/**
 * Repository interface for managing {@link Question} persistence.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and
 * database interactions for
 * {@link Question} entities.
 * </p>
 * 
 * @see JpaRepository
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
