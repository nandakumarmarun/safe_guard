package com.security.analyzer.v1.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    default Optional<Company> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Company> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Company> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select company from Company company left join fetch company.country left join fetch company.state left join fetch company.district left join fetch company.city left join fetch company.location",
        countQuery = "select count(company) from Company company"
    )
    Page<Company> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select company from Company company left join fetch company.country left join fetch company.state left join fetch company.district left join fetch company.city left join fetch company.location"
    )
    List<Company> findAllWithToOneRelationships();

    @Query(
        "select company from Company company left join fetch company.country left join fetch company.state left join fetch company.district left join fetch company.city left join fetch company.location where company.id =:id"
    )
    Optional<Company> findOneWithToOneRelationships(@Param("id") Long id);
}
