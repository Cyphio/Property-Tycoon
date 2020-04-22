package misc;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class RotatableLabel extends Group {

    public RotatableLabel(Label label, int x, int y , int rotationAngle, int scale){
        this.setScale(scale);
        this.setWidth(label.getWidth());
        this.setHeight(label.getHeight());
        this.setOrigin(label.getWidth()/2,label.getHeight()/2);
        this.addActor(label);
        this.setPosition(x,y);


        RotateByAction rotate = new RotateByAction();
        rotate.setAmount(rotationAngle);
        this.addAction(rotate);
    }
}
