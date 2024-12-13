package in.vishalit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vishalit.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
