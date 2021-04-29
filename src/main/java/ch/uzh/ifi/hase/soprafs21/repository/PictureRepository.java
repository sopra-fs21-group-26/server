package ch.uzh.ifi.hase.soprafs21.repository;
import ch.uzh.ifi.hase.soprafs21.entity.Picture;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pictureRepository")
public interface PictureRepository extends JpaRepository<Picture, Long> {
    User findById(long id);
}