package groom.goorm_board_back.service.board;

import groom.goorm_board_back.domain.Board;
import groom.goorm_board_back.dto.board.BoardInfoDto;
import groom.goorm_board_back.dto.board.BoardSaveDto;
import groom.goorm_board_back.dto.board.BoardUpdateDto;
import groom.goorm_board_back.repository.board.BoardRepository;
import groom.goorm_board_back.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void save(BoardSaveDto boardSaveDto) {

        Board board = Board.builder()
                .title(boardSaveDto.title())
                .content(boardSaveDto.content())
                .writer(memberRepository.findByMemberWithId())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void update(Long id, BoardUpdateDto boardUpdateDto) {

        Board board = boardRepository.findByBoardWithId(id);
        checkAuthority(board);
        board.updateTitle(boardUpdateDto.title());
        board.updateContent(boardUpdateDto.content());
    }

    public void delete(Long id) {

        Board board = boardRepository.findByBoardWithId(id);
        checkAuthority(board);
        boardRepository.delete(board);
    }

    private void checkAuthority(Board board) {
        if(!board.getWriter().equals(memberRepository.findByMemberWithId())){
            throw new IllegalArgumentException("수정/삭제 할 권한이 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public BoardInfoDto getBoardInfo(Long id) {

        Board board = boardRepository.findByBoardWithId(id);

        return BoardInfoDto.builder()
                .boardId(board.getId())
                .username(board.getWriter().getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}