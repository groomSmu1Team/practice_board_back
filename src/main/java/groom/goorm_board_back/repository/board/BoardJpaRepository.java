package groom.goorm_board_back.repository.board;

import groom.goorm_board_back.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
