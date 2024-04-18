package com.cos.cercat.domain.embededId;

import com.cos.cercat.domain.board.TargetBoard;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class FavoriteBoardPK implements Serializable {

    private Long userId;

    private Long certificateId;

    public static FavoriteBoardPK from(TargetBoard targetBoard) {
        return new FavoriteBoardPK(
                targetBoard.userId(),
                targetBoard.certificateId()
        );
    }
}
