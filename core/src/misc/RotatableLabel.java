package misc;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class RotatableLabel extends Group {


    /**
     *  Makes a label that is more versatile ( it can be rotated and manipulated).
     */

    public RotatableLabel(Label label, int x, int y , int rotationAngle, float scale){
        label.setFontScale((float)1.2);
        label.setSize(1,1);
        label.setOrigin(label.getWidth()/2,label.getHeight()/2);
        label.setAlignment(Align.center);
        RotateByAction rotate = new RotateByAction();
        rotate.setAmount(rotationAngle);
        this.addAction(rotate);
        this.setOrigin(label.getWidth()/2,label.getHeight()/2);
        this.addActor(label);
        this.setPosition(x,y);


    }
}
