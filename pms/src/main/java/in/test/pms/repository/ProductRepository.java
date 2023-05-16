package in.test.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.test.pms.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

}
