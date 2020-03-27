package main;

import Tiles.*;

public class ConfigValidator {

    public ConfigValidator(Tile[] board){
        validate(board);
    }

    public void validate(Tile[] board) {
        if (!(board[0] instanceof Go)){
            throw new IllegalStateException("The Go tile has been configured incorrectly");
        }
        if (!(board[10] instanceof Jail)){
            throw new IllegalStateException("The Jail tile has been configured incorrectly");
        }
        if (!(board[4] instanceof Tax)){
            throw new IllegalStateException("The Tax tile has been configured incorrectly");
        }
        if (!(board[2] instanceof OpportunityKnocks)){
            throw new IllegalStateException("The CommunityChest tile has been configured incorrectly");
        }
        if (!(board[5] instanceof Station)){
            throw new IllegalStateException("The Station 1 tile has been configured incorrectly");
        }
        if (!(board[15] instanceof Station)){
            throw new IllegalStateException("The Station 2 tile has been configured incorrectly");
        }
        if (!(board[25] instanceof Station)){
            throw new IllegalStateException("The Station 3 tile has been configured incorrectly");
        }
        if (!(board[35] instanceof Station)){
            throw new IllegalStateException("The Station 4 tile has been configured incorrectly");
        }
        if (!(board[20] instanceof FreeParking)){
            throw new IllegalStateException("The FreeParking tile has been configured incorrectly");
        }
        if (!(board[30] instanceof GoToJail)){
            throw new IllegalStateException("The GotoJail tile has been configured incorrectly");
        }
    }
}
