package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate beforeDate, LocalDate afterDate);

    List<Book> findByTitleContainsIgnoreCase(String line);

    List<Book> findByAuthorLastNameContainsIgnoreCase(String line);

    @Query("select b from Book as b where length(b.title) > :minLength")
    List<Book> findByTitleLengthLongerThan(@Param(value = "minLength") int length);

    @Query("select concat(a.firstName, ' ', a.lastName, ' - ', sum(b.copies)) " +
            "from Book as b join b.author as a " +
            "group by a.id order by sum(b.copies) desc")
    List<Object> getAuthorsByBooksCopies();

    @Query("select new bookshopsystemapp.domain.entities.ReducedBook(b.title, b.editionType, b.ageRestriction, b.price) " +
            "from Book as b " +
            "where b.title = :title")
    ReducedBook getBookInformationByTitle(@Param(value = "title") String Title);

    @Modifying
    @Query("update Book as b set b.copies = b.copies + :moreCopies " +
            "where b.releaseDate > :minReleaseDate")
    int increaseBookCopiesOfBooksReleasedAfter(@Param(value = "minReleaseDate") LocalDate date, @Param(value = "moreCopies") int moreCopies);

    @Modifying
    @Query("delete from Book as b where b.copies < :neededCopies")
    int removeBooksWithCopiesLessThan(@Param(value = "neededCopies") int minCopies);

    @Procedure(procedureName = "sp_book_count")
    Integer getAuthorsBooksCount(@Param("@author_first_name") String firstName, @Param("@author_last_name") String lastName);

}
