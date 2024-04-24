package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.response.BoardResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "게시판 API")
public interface BoardApiDocs {

    @Operation(summary = "게시판 즐겨찾기 및 해제")
    Response<Void> flipFavoriteBoard(Long certificateId, UserDTO currentUser);

    @Operation(summary = "게시판 목록 조회")
    Response<List<BoardResponse>> readBoards(UserDTO currentUser);
}
